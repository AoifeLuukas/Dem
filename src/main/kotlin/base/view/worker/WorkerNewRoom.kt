package base.view.worker

import base.data.Data
import base.data.Reserva
import base.data.ReservaModel
import base.data.room
import base.view.user.LoadingPayment
import base.view.user.WrongReserva
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.concurrent.thread


class WorkerNewRoom : View("Worker/ New Room") {
    val data: Data by inject()

    val PaymentTypes = FXCollections.observableArrayList("Cash",
            "Debit Card",
            "Credit Card")
    val CheckboxBooleanProperty = SimpleBooleanProperty()

    val rooms = data.rooms.asObservable()

    val model = ReservaModel(Reserva())

    var email = SimpleStringProperty()


    override val root = gridpane {
        onBeforeShow().apply {
            model.rebind {
                item.roomdId = rooms[0].id
                item.roomdPrice = rooms[0].price
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
                fieldset("User Data") {
                    field("Client's email") {
                        textfield(email){
                            promptText = "test@test.com"
                        }
                    }
                }
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
                }
                fieldset ("Payment Info") {
                    field ("Payment Type") {
                        combobox(SimpleStringProperty("Cash"), values = PaymentTypes) {
                        }
                    }
                    field () {
                        checkbox("Coupon", CheckboxBooleanProperty) {

                        }
                        textfield ("Coupon Id") {
                            visibleWhen(CheckboxBooleanProperty)
                        }
                    }
                }
                borderpane {
                    left {
                        button ("Cancel reservation") {
                            action {
                                replaceWith(WorkerMain::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
                            }
                        }
                    }
                    right {
                        button ("Make reservation") {
                            action {
                                var usern = data.users.find { it.email == email.value}
                                if (usern != null) {
                                    var userId = usern.id
                                    model.rebind {
                                        item.userId = userId
                                    }
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
                            //replaceWith(DidHePaid::class)    <- won't work since tornadofx it's jealous of this thread
                        }
                    }
                }
            }
        }
    }
}

class DidHePaid: Fragment("Did he!?") {
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
                            close()
                        }
                    }
                }
            }
        }
    }
}