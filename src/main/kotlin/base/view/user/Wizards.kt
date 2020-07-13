package base.view.user

import base.data.Data
import base.data.User
import javafx.geometry.Orientation
import tornadofx.*
import java.time.LocalDate


class ClientRegister : Fragment("Register Client") {
    val data: Data by inject()
    var newUser = User(data.users.maxBy { it.id }!!.id+1, 3, "", "", "", "", LocalDate.now(), "")

    override val root = form {
        data.makeNewUser = false
        fieldset(title) {
            field("First Name") {
                textfield(newUser.firstNameProperty) {
                    promptText = "First Name"
                }
            }
            field ("Second Name"){
                textfield(newUser.lastNameProperty) {
                    promptText = "Last Name"
                }
            }
            field ("Identification") {
                textfield(newUser.identificationProperty) {
                    promptText = "11.111.111-1"
                }
            }
            field ("Email") {
                textfield(newUser.emailProperty) {
                    promptText = "test@test.com"
                }
            }
            field ("Password") {
                textfield(newUser.passwordProperty) {
                    promptText = "Password"
                }
            }
            field ("Age") {
                datepicker(newUser.birthDayProperty) {
                }
            }
        }
        separator (Orientation.HORIZONTAL){}
        borderpane {
            right{
                button ("continue") {
                    setPrefSize(150.0, 60.0)
                    action {
                        data.makeNewUser = true
                        data.newUser = newUser
                        close ()
                    }
                }
            }
            left {
                button ("close") {
                    setPrefSize(150.0, 60.0)
                    action {
                        data.newUser = null
                        data.makeNewUser = false
                        close ()
                    }
                }
            }
        }
    }
}

