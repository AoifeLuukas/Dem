package base.data

data class UserWeas(val name: String = "user", val password: String = "password")

data class WorkerWea(val name: String = "worker", val password: String = "password")

data class ReservaWea(val dir: String = "none", val user: String = "none", val worker: String = "none")

class App(){
    var userWeas:Collection<UserWeas> = emptyList()
    var workerWeas:Collection<WorkerWea> = emptyList()
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
                if (this.userWeas.isEmpty()) {
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


}

fun main() {
    var app:App = App()
    app.RunApp()
}






