package base.view.user

import base.data.Data
import base.data.ReservaModel
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.concurrent.thread

class UserNewRoom : View("User/ New Room") {

    val data: Data by inject()
    val rooms = data.rooms.asObservable()

    val model:ReservaModel by inject()

    override val root = gridpane {
        onBeforeShow().apply {
            model.rebind {
                item.roomdId = rooms[0].id
                item.roomdPrice = rooms[0].price
                item.userId = data.activeUser!!.idProperty.value
            }
        }
        gridpaneConstraints {
            constraintsForRow(0).percentHeight = 100.0
        }
        row {
            listview(rooms) {
                gridpaneColumnConstraints {
                    percentWidth = 60.0
                }

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
                                                field("Number") {
                                                    label(it.id.toString()) {
                                                        isEditable = false
                                                    }
                                                }
                                                field("Description") {
                                                    label(it.name){
                                                        isEditable = false
                                                    }
                                                }
                                                label("${it.price} usd per night") {
                                                    alignment = Pos.CENTER_RIGHT
                                                    isEditable = false
                                                    style {
                                                        fontSize = 22.px
                                                        fontWeight = FontWeight.BOLD
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                }
                onUserSelect(1) {
                    model.rebind() {
                        item.roomdId = it.id
                        item.roomdPrice = it.price
                        item.userId = data.activeUser!!.id
                    }
                    model.markDirty(model.roomPrice)
                }
            }
            line{
                startX = 0.0
                startY = 0.0
                endX = 0.0
                endY = 600.0
            }
            form {
                fieldset("Dates"){
                    field("Starting Date"){
                        datepicker(model.startingDate) {
                            value = LocalDate.now()
                            setOnAction {
                                model.markDirty(model.roomId)
                                model.commit()
                            }
                        }
                    }
                    field("Final Date") {
                        datepicker(model.endingDate) {
                            value = LocalDate.now()
                            setOnAction {
                                model.markDirty(model.roomId)
                                model.commit()
                            }
                        }
                    }
                    field("Days") {
                        val amountOfDays = label("${ChronoUnit.DAYS.between(model.startingDate.value, model.endingDate.value)}")
                        model.dirty.addListener {_, _, _ ->
                            amountOfDays.text = "${ChronoUnit.DAYS.between(model.startingDate.value, model.endingDate.value)}"
                        }
                    }
                    field("Total") {
                        var value = ChronoUnit.DAYS.between(model.startingDate.value, model.endingDate.value) * model.roomPrice.value.toDouble()
                        value = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                        val priceOfDays = label("\$$value USD")
                        model.dirty.addListener {_, _, _ ->
                            value = ChronoUnit.DAYS.between(model.startingDate.value, model.endingDate.value) * model.roomPrice.value.toDouble()
                            value = BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                            priceOfDays.text = "\$$value USD"
                        }
                    }
                    borderpane {
                        left {
                            button ("Cancel reservation") {
                                tooltip {
                                    text = "Will go back to the main page."
                                }
                                action {
                                    replaceWith(UserMain::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
                                }
                            }
                        }
                        right {
                            button ("Make reservation") {
                                action {
                                    openInternalWindow<AreYouSureTho>()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class AreYouSureTho: Fragment("Are you sure tho?") {

    val data: Data by inject()
    val model:ReservaModel by inject()

    override val root = vbox {
        form {
            fieldset{
                label ("Are you sure that you want to make this reserve?")
            }
            borderpane {
                left {
                    button ("cancel") {
                        action {
                            close()
                        }
                        shortcut("Esc")
                    }
                }
                right {
                    button ("continue") {
                        action {
                            if (data.newReserva(model.itemProperty.value)) {
                                replaceWith(LoadingPayment::class)
                            } else {
                                replaceWith(WrongReserva::class)
                            }
                        }
                    }
                }
            }
        }
    }
}

class LoadingPayment: Fragment() {
    override val root = gridpane {
        row {
            shortcut("Esc"){
                close()
            }
            borderpane{
                top {
                    label ("Loading payment platform...")
                }
                center {
                    progressindicator {
                        thread {
                            for (i in 1..100) {
                                Platform.runLater { progress = i.toDouble() / 100.0 }
                                Thread.sleep(50)
                            }
                        }
                    }
                }
            }
        }
    }
}


class WrongReserva: Fragment() {
    override val root = gridpane {

        shortcut("Esc") {
            close()
        }
        gridpaneConstraints {
            prefWidth = 300.0
            prefHeight = 150.0
            constraintsForRow(0).percentHeight = 10.0
            constraintsForRow(1).percentHeight = 10.0
            constraintsForRow(2).percentHeight = 50.0
            constraintsForRow(3).percentHeight = 20.0
            constraintsForRow(4).percentHeight = 10.0
        }
        row {
            borderpane {
                center {
                    label ("Error! Wrong Input"){
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
            line {
                startY = 0.0
                startX = 5.0
                endY = 0.0
                endX = 295.0
            }
        }
        row {
            borderpane {
                center {
                    label ("Wrong starting or ending date.") {
                        style {
                            fontSize = 18.px
                        }
                    }
                }
            }
        }
        row {
            line {
                startY = 0.0
                startX = 5.0
                endY = 0.0
                endX = 295.0
            }
        }
        row {
            borderpane {
                right {
                    button ("OK") {
                        prefWidth = 60.0
                        prefHeight = 30.0
                        action {
                            close()
                        }
                        shortcut("ENTER")
                    }
                }
            }
        }
    }
}