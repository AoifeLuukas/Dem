package base.view.user

import base.data.Reserva
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class UserReserveList : View("User/ Reserve List") {
    private var reserveList = listOf(
            Reserva(1, 40.0, LocalDate.of(2020, 2, 23), LocalDate.of(2020, 2, 25)),
            Reserva(54, 300.0, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 5))).asObservable()

    override val root = gridpane {
        gridpaneConstraints {
            useMaxSize = true
            constraintsForRow(0).percentHeight = 5.0
            constraintsForRow(1).percentHeight = 90.0
            constraintsForRow(2).percentHeight = 5.0
        }
        row {
            borderpane {
                center {
                    label ("History of reserved rooms.") {
                        style {
                            fontSize = 22.px
                            fontWeight = FontWeight.BOLD
                            underline = true
                        }
                    }
                }
            }
        }
        row {
            listview (reserveList) {
                gridpaneColumnConstraints {
                    percentWidth = 100.0
                }
                cellFormat {
                    graphic =
                            cache {
                                form {
                                    hbox{
                                        imageview("coupon-placeholder.png"){
                                            isPreserveRatio = true
                                            fitHeight = 90.0
                                            paddingRight = 10.0
                                        }
                                        fieldset("Reservation Info") {
                                            vbox {
                                                paddingRight = 10.0
                                                paddingLeft = 10.0
                                                field ("Starting Date:") {
                                                    textfield (it.startingDate.toString()) {
                                                        isEditable = false
                                                        prefWidth = 100.0
                                                    }
                                                }
                                                field ("Ending Date:") {
                                                    textfield (it.startingDate.toString()) {
                                                        isEditable = false
                                                        prefWidth = 100.0
                                                    }
                                                }
                                            }
                                        }
                                        fieldset ("Room Info") {
                                            hbox{
                                                vbox {
                                                    paddingRight = 10.0
                                                    field ("Room Number:") {
                                                        textfield (it.roomdId.toString()) {
                                                            isEditable = false
                                                            prefWidth = 100.0
                                                        }
                                                    }
                                                    field ("Room Price:") {
                                                        textfield (it.roomdPrice.toString()) {
                                                            isEditable = false
                                                            prefWidth = 100.0
                                                        }
                                                    }
                                                }
                                                vbox {
                                                    field("1") {
                                                        isVisible = false
                                                        textfield ("1"){
                                                            isVisible = false
                                                            prefWidth = 120.0
                                                        }
                                                    }
                                                    field("Total Price:") {
                                                        val value = ChronoUnit.DAYS.between(it.startingDate, it.endingDate) * it.roomdPrice
                                                        textfield (value.toString()) {
                                                            isEditable = false
                                                            prefWidth = 120.0
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
        row {
            borderpane {
                right {
                    button ("Go Back") {
                        paddingRight = 30.0
                        prefHeight = 20.0
                        prefWidth = 125.0
                        style {
                            fontSize = 16.px
                        }
                        action {
                            replaceWith(UserMain::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
                        }
                    }
                }
            }
        }
    }
}
