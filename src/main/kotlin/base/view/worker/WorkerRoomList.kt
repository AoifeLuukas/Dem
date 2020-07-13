package base.view.worker

import base.data.Data
import base.data.room
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class WorkerRoomList : View("Worker/ Room List") {
    val data: Data by inject()
    private var roomsList = data.rooms.asObservable()

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
                    label ("List of all rooms") {
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
            listview (roomsList) {
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
                                        fieldset("Room Info") {
                                            paddingLeft = 20.0
                                            hbox {
                                                vbox {
                                                    paddingRight = 10.0
                                                    paddingLeft = 10.0
                                                    field ("Room Number:") {
                                                        label (it.id.toString()) {
                                                            isEditable = false
                                                        }
                                                    }
                                                    field ("Room Name:") {
                                                        label (it.name) {
                                                            isEditable = false
                                                        }
                                                    }
                                                }
                                                vbox {
                                                    paddingRight = 10.0
                                                    paddingLeft = 10.0
                                                    field ("Room Price:") {
                                                        label (it.price.toString()) {
                                                            isEditable = false
                                                        }
                                                    }
                                                    field ("Room Status:") {
                                                        hbox {
                                                            circle {
                                                                radius = 5.0
                                                                when (it.status) {
                                                                    1 -> {
                                                                        fill = Color.GREEN
                                                                    }
                                                                    2 -> {
                                                                        fill = Color.RED
                                                                    }
                                                                    3 -> {
                                                                        fill = Color.BLUE
                                                                    }
                                                                }
                                                            }

                                                            label {
                                                                paddingLeft = 20.0
                                                                when (it.status) {
                                                                    1 -> {
                                                                        text = "Free"
                                                                    }
                                                                    2 -> {
                                                                        text = "Rented"
                                                                    }
                                                                    3 -> {
                                                                        text = "Maintenance"
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
                            replaceWith(WorkerMain::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
                        }
                    }
                }
            }
        }
    }
}
