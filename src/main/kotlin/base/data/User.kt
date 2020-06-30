package base.data

data class User(val name: String = "user", val password: String = "password")

data class Worker(val name: String = "worker", val password: String = "password")

data class Reserva(val dir: String = "none", val user: String = "none", val worker: String = "none")

class App(){
    var Users:Collection<User> = emptyList()
    var Workers:Collection<Worker> = emptyList()
    var Reservas:Collection<Reserva> = emptyList()

    fun RunApp() {
        println("Welcome to XYZ")
        var value: String

        RUNAPP@ while (true) {
            println("Please type USER to continue as an user or WORKER to change to the WORKER interface. Type Q to close.")
            value = readLine().toString().toUpperCase().trim()
            when (value) {
                "USER" -> {
                    this.UserLogin()
                }
                "WORKER" -> {
                    println("WelcomeWorker")
                }
                "Q" -> {
                    println("Have a nice day.")
                    break@RUNAPP
                }
            }
        }
    }

    fun UserLogin() {
        var value: String
        println("Welcome user")

        USER@ while(true) {
            println("If you would like to login please type LOGIN.")
            println("If you would like to register please type REGISTER.")
            println("If you would go back to the main menu please type Q.")
            value = readLine().toString().toUpperCase().trim()

            when (value) {
                "LOGIN" -> {
                    this.login("USER")
                }
                "REGISTER" -> {
                    this.register("USER")
                }
                "Q" -> {
                    println("Have a nice day.")
                    break@USER
                }
            }
        }
    }

    fun login(type: String) {
        when (type) {
            "USER" -> {
                if (this.Users.isEmpty()) {
                    println("There are no user at the moment. Please register.")
                    return
                } else {
                    USERLOG@ while (true) {
                        println("please i")
                    }
                }
            }
        }
    }

    fun register(type: String) {


    }

}

fun main() {
    var app:App = App()
    app.RunApp()
}






