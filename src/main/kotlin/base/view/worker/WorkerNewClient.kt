package base.view.worker

import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation
import javafx.scene.text.FontWeight
import tornadofx.*

class WorkerNewClient : View("Worker/ New Client") {
    var passwordToMail = SimpleBooleanProperty(true)

    override val root = gridpane {
        gridpaneConstraints {
            marginTopBottom(20.0)
            marginLeftRight(20.0)
            constraintsForRow(0).percentHeight = 10.0
            constraintsForRow(1).percentHeight = 90.0
            useMaxSize = true
        }
        row {
            borderpane {
                gridpaneColumnConstraints {
                    percentWidth = 100.0
                }
                center{
                    label ("Register a new client"){
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
            form {
                gridpaneColumnConstraints {
                    percentWidth = 100.0
                }
                vbox {
                    gridpane {
                        gridpaneConstraints {
                            constraintsForRow(0).percentHeight = 100.0
                        }
                        row {
                            fieldset ("Client Basic Info") {
                                gridpaneColumnConstraints {
                                    percentWidth = 30.0
                                    useMaxHeight = true
                                }
                                vbox {
                                    field ("First Name:") {
                                        textfield {
                                            promptText = "First Name"
                                            maxWidth = 150.0
                                        }
                                    }
                                    field ("Last Name:") {
                                        textfield {
                                            promptText = "Last Name"
                                            maxWidth = 150.0
                                        }
                                    }
                                    separator {
                                    }
                                    field ("Email:") {
                                        textfield {
                                            promptText = "example@example.com"
                                            maxWidth = 150.0
                                        }
                                    }
                                    field ("Identification:") {
                                        textfield {
                                            promptText = "12.345.678-9"
                                            maxWidth = 150.0
                                        }
                                    }
                                    field{
                                        checkbox ("Send password to mail?", passwordToMail)
                                    }
                                    stackpane {
                                        field {
                                            visibleWhen(passwordToMail)
                                            label("A password will be sent to the client emails.") {
                                            }
                                        }
                                        field ("Password: ") {
                                            visibleWhen(!passwordToMail)
                                            passwordfield {
                                                promptText = "Password"
                                                maxWidth = 150.0
                                            }
                                        }
                                    }
                                }
                            }
                            separator(Orientation.VERTICAL)
                            fieldset () {
                                gridpaneColumnConstraints {
                                    percentWidth = 30.0
                                }
                            }
                            separator(Orientation.VERTICAL)
                            fieldset  {
                                gridpaneColumnConstraints {
                                    percentWidth = 30.0
                                }
                            }
                        }
                    }
                    borderpane{
                        left {
                            button("cancel")
                        }
                        right {
                            button("continue")
                        }
                    }
                }
            }
        }
    }
}
