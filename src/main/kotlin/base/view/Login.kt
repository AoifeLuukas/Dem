package base.view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import tornadofx.*

class Login : View() {
    override val root = gridpane()
    val user = SimpleStringProperty()
    val pass = SimpleStringProperty()
    init {
        with(root) {
            title = "Hotel"
            gridpaneConstraints {
                fillHeight = true
                constraintsForRow(0).percentHeight = 95.0
            }
            row {
                form {
                    fieldset("User Login", labelPosition = Orientation.VERTICAL) {
                        field("User") {
                            textfield(user)
                        }
                        field("Password") {
                            passwordfield(pass)
                        }
                        borderpane {
                            right {
                                button("Login") {
                                    action {
                                        openInternalWindow<MyFragment>()
                                        user.value = ""
                                        pass.value = ""
                                    }
                                }
                            }

                            left {
                                hyperlink("Sign up").action {
                                    openInternalWindow<MyFragment>()
                                }
                            }
                        }
                    }
                    gridpaneColumnConstraints {
                        percentWidth = 50.0

                    }
                }
                line{
                    startX = 0.0
                    startY = 0.0
                    endX = 0.0
                    endY = 600.0
                }
                imageview("windrose.png"){
                    isPreserveRatio = true
                    fitWidth = 450.0
                }
            }
            row{
                hyperlink("Are you a worker?").action {
                    id = "ChangeLogin"
                    replaceWith(WorkerLogin::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.UP))
                }
            }
        }
    }
}

class WorkerLogin : View() {
    override val root = gridpane()
    val user = SimpleStringProperty()
    val pass = SimpleStringProperty()
    init {
        with(root) {
            title = "Hotel"
            gridpaneConstraints {
                fillHeight = true
                constraintsForRow(0).percentHeight = 95.0
            }
            row {
                form {
                    fieldset("Worker Login", labelPosition = Orientation.VERTICAL) {
                        field("User") {
                            textfield(user)
                        }
                        field("Password") {
                            passwordfield(pass)
                        }

                        button("Login") {
                            action {
                                openInternalWindow<MyFragment>()
                                user.value = ""
                                pass.value = ""
                            }
                        }
                    }
                    gridpaneColumnConstraints {
                        percentWidth = 50.0
                    }
                }
                line{
                    startX = 0.0
                    startY = 0.0
                    endX = 0.0
                    endY = 600.0
                }
                imageview("cat-paw.png"){
                    isPreserveRatio = true
                    fitWidth = 450.0
                }

            }
            row{
                hyperlink("Go back to the User View").action {
                    id = "ChangeLogin"
                    replaceWith(Login::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.DOWN))
                }
            }
        }
    }
}


class MyFragment: Fragment() {
    override val root = label("This is a popup")

    fun changeSize(num: Int){
        
    }
}