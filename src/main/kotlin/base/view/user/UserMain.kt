package base.view.user

import base.data.*
import base.view.Login
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate

class UserMain : Fragment() {

    override val root = gridpane ()
    val data: Data by inject()

    var reservas: ObservableList<Reserva> = (data.reservas.filter { (
            (it.userId == data.activeUser?.idProperty!!.value)
                    and (LocalDate.now().isAfter(it.startingDate) or LocalDate.now().isEqual(it.startingDate))
                    and (LocalDate.now().isBefore(it.endingDate) or LocalDate.now().isEqual(it.endingDate))
            )
    }).asObservable()
    var rooms:ObservableList<room> = data.rooms.filter {it.id in (reservas.map{it.roomdId})}.asObservable()

    val coupons:ObservableList<coupon> = data.activeUser!!.coupons.asObservable()

    val sizeCoupons = coupons.size

    val model: roomModel by inject()

    init {
        with(root) {
            whenDocked {
                reservas = (data.reservas.filter { (
                        (it.userId == data.activeUser?.id)
                                and (LocalDate.now().isAfter(it.startingDate) or LocalDate.now().isEqual(it.startingDate))
                                and (LocalDate.now().isBefore(it.endingDate) or LocalDate.now().isEqual(it.endingDate))
                        )
                }).asObservable()
                rooms = data.rooms.filter {it.id in (reservas.map{it.roomdId})}.asObservable()
                println(rooms)
                for (i in rooms) {
                    println(i.name)
                }
            }
            whenUndocked {
                model.commit()
            }

            title = "User/ Main"
            row {
                gridpaneConstraints {
                    fillHeight = true
                }
                gridpane {
                    gridpaneColumnConstraints {
                        percentWidth = 30.0
                    }
                    gridpaneConstraints {
                        fillWidth = true
                        constraintsForRow(0).percentHeight = 15.0
                        constraintsForRow(1).percentHeight = 80.0
                        constraintsForRow(2).percentHeight = 5.0
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    row {
                        borderpane {
                            top {
                                label ("Welcome User") {
                                    style {
                                        fontSize = 22.px
                                        fontWeight = FontWeight.BOLD
                                    }
                                }
                            }
                            right {
                                label ("You have $sizeCoupons coupons") {
                                    alignment = Pos.CENTER_RIGHT
                                }
                            }
                        }
                    }
                    row {
                        vbox {
                            hyperlink("Reserve a new room").action {
                                replaceWith(UserNewRoom::class)
                            }
                            hyperlink("See all your...").action {
                                replaceWith(UserReserveList::class)
                            }
                        }
                    }
                    row {
                        vbox {
                            hyperlink("Logout").action {
                                data.activeUser = null
                                replaceWith(Login::class)
                            }
                        }
                    }
                }
                line{
                    startX = 0.0
                    startY = 0.0
                    endX = 0.0
                    endY = 600.0
                }
                drawer (multiselect = true, side = Side.RIGHT) {
                    useMaxWidth = true
                    gridpaneColumnConstraints {
                        percentWidth = 70.0
                    }
                    item ("Current Rooms", expanded = true) {
                        if (rooms.isEmpty()) {
                            borderpane {
                                center {
                                    text ("There is no rooms on ur name. :c")
                                }
                            }
                        } else {
                            tableview<room> {
                                readonlyColumn("ID", room::id)
                                readonlyColumn("name", room::name)
                                smartResize()
                                fitToParentWidth()
                                asyncItems { rooms }
                            }
                        }
                    }
                    item ("Coupons!", expanded = true) {
                        if (coupons.size == 0) {
                            borderpane {
                                center {
                                    text ("There is no coupons available. :c")
                                }
                            }
                        } else {
                            listview(coupons){
                                cellFormat {
                                    graphic =
                                            cache {
                                                borderpane {
                                                    left {
                                                        imageview("coupon-placeholder.png") {
                                                            isPreserveRatio = true
                                                            fitHeight = 100.0
                                                        }
                                                    }
                                                    center {
                                                        form {
                                                            fieldset {
                                                                label(it.name) {
                                                                    alignment = Pos.CENTER_RIGHT
                                                                    style {
                                                                        fontSize = 22.px
                                                                        fontWeight = FontWeight.BOLD
                                                                    }
                                                                }
                                                                field("Id") {
                                                                    label(it.id.toString())
                                                                }
                                                                field("Description") {
                                                                    label(it.description)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
