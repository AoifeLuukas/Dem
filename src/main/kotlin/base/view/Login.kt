package base.view

import base.data.Data
import base.data.User
import base.data.UserModel
import base.view.user.ClientRegister
import base.view.user.UserMain
import base.view.worker.WorkerMain
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate

class Login : View() {
    override val root = gridpane()
    val user = SimpleStringProperty()
    val pass = SimpleStringProperty()

    val data: Data by inject()
    val customer: UserModel by inject()

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
                        field("Email") {
                            textfield(user)
                        }
                        field("Password") {
                            passwordfield(pass)
                        }
                        borderpane {
                            right {
                                button("Login") {
                                    action {
                                        if (data.login(user.value, pass.value) == true) {
                                            replaceWith(UserMain::class)
                                        } else {
                                            openInternalWindow<WrongLogin>()
                                            user.value = ""
                                            pass.value = ""
                                        }
                                    }
                                    shortcut ("ENTER")
                                }
                            }

                            left {
                                hyperlink("Sign up").action {
                                    find<ClientRegister> {
                                        whenUndockedOnce {
                                            println("hi")
                                            if (data.makeNewUser) {
                                                if (data.register(data.newUser!!)) {
                                                    println("ctm lo creo")
                                                } else {
                                                    println("no lo creo")
                                                }

                                                println("lawea funca")
                                            } else {
                                                println("aweonao")
                                            }
                                        }
                                        openModal()
                                    }

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
                    fitWidth = 400.0
                    paddingAll = 20.0
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
    val data: Data by inject()
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
                        borderpane {
                            right {
                                button("Login") {
                                    action {
                                        if (data.login(user.value, pass.value)) {
                                            replaceWith(WorkerMain::class)
                                        } else {
                                            openInternalWindow<WrongLogin>()
                                            user.value = ""
                                            pass.value = ""
                                        }
                                    }
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
                imageview("cat-paw.png"){
                    isPreserveRatio = true
                    fitWidth = 400.0
                    paddingAll = 20.0
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


class WrongLogin: Fragment() {
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
                    label ("Wrong email or password.") {
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

class Register: Fragment() {
    val user = SimpleStringProperty()
    val pass = SimpleStringProperty()
    override val root = borderpane{

        style {
            prefWidth = 450.px
        }
        center{
            form {
                fieldset("Register", labelPosition = Orientation.VERTICAL) {
                    field("Username") {
                        textfield(user)
                    }
                    field("Password") {
                        passwordfield(pass)
                    }
                    borderpane {
                        left {
                            button("Cancel") {
                                action {
                                    close()
                                }
                            }
                        }

                        right {
                            button("Register") {
                                action {
                                    close()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}