package base.view.worker

import base.data.Data
import base.data.User
import base.view.Login
import base.view.user.ClientRegister
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.random.Random


/*
type:
1 - Admin
2 - User
3 - Client
 */

class WorkerMain : Fragment() {
    val data : Data by inject()
    private val user = data.activeUser!!

    private var aMonthAgo: LocalDate = LocalDate.now().minusDays(30)
    private val dates: MutableList<Any> = IntStream.iterate(0) { i: Int -> i + 1 }
            .limit(30)
            .mapToObj<Any> { i: Int -> aMonthAgo.plusDays(i.toLong()) }
            .collect(Collectors.toList())
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM")

    override val root = gridpane {
        title = "Worker/ Main"
        row {
            gridpaneConstraints {
                fillHeight = true
            }
            gridpane {
                gridpaneColumnConstraints {
                    percentWidth = 30.0
                }
                gridpaneConstraints {
                    fillWidth = true
                    constraintsForRow(0).percentHeight = 15.0
                    constraintsForRow(1).percentHeight = 80.0
                    constraintsForRow(2).percentHeight = 5.0
                    marginTop = 10.0
                    marginLeft = 10.0
                }
                row {
                    borderpane {
                        top {
                            label("Welcome ${user.firstName}") {
                                style {
                                    fontSize = 22.px
                                    fontWeight = FontWeight.BOLD
                                }
                            }
                        }
                    }
                }
                row {
                    vbox {
                        if ((user.type == 1) or (user.type == 2)) {
                            hyperlink("Reserve a new room").action {
                                replaceWith(WorkerNewRoom::class)
                            }
                            hyperlink("See all the rooms").action {
                                replaceWith(WorkerRoomList::class)
                            }
                            hyperlink("Register a new client").action {
                                find<ClientRegister> {
                                    whenUndockedOnce {
                                        println("hi")
                                        if (data.makeNewUser) {
                                            println("valid input")
                                            if (data.register(data.newUser!!)) {
                                                println("new client created")
                                            }
                                        }
                                    }
                                    openModal()
                                }
                            }
                            if (user.type == 1) {
                                hyperlink("Edit users").action {
                                }
                            }
                        } else if (user.type == 3) {
                            println("Client.")
                            hyperlink("This has been a mistake, please log out!").action {
                                data.activeUser = null
                                replaceWith(Login::class)
                            }
                        } else {
                            println("ERROR: Wrong client type.")
                        }
                    }
                }
                row {
                    vbox {
                        hyperlink("Logout").action {
                            data.activeUser = null
                            replaceWith(Login::class)
                        }
                    }
                }
            }
            line{
                startX = 0.0
                startY = 0.0
                endX = 0.0
                endY = 600.0
            }
            gridpane {
                gridpaneConstraints {
                    constraintsForRow(0).percentHeight = 50.0
                    constraintsForRow(1).percentHeight = 50.0
                }
                if(user.type != 3) {
                    row {
                        barchart("Amount of rented rooms by category", CategoryAxis(), NumberAxis()) {
                            gridpaneColumnConstraints {
                                percentWidth = 50.0
                                paddingRight = 10.0
                            }
                            series("Single Bed Rooms") {
                                data("FEB", 186)
                                data("MAR", 102)
                                data("APR", 110)
                                data("MAY", 105)
                            }
                            series("Two Bed Rooms") {
                                data("FEB", 135)
                                data("MAR", 177)
                                data("APR", 137)
                                data("MAY", 111)
                            }
                            series("Familiar Rooms") {
                                data("FEB", 79)
                                data("MAR", 108)
                                data("APR", 124)
                                data("MAY", 169)
                            }
                        }
                        linechart("Amount of clients by type of client", CategoryAxis(), NumberAxis()) {
                            gridpaneColumnConstraints {
                                percentWidth = 50.0
                            }
                            series("New Clients") {
                                data("FEB", 226)
                                data("MAR", 242)
                                data("APR", 265)
                                data("MAY", 286)
                            }
                            series("Regular Clients") {
                                data("FEB", 79)
                                data("MAR", 98)
                                data("APR", 83)
                                data("MAY", 94)
                            }
                        }
                    }
                    row {
                        areachart("Amount of clients registered by day", CategoryAxis(), NumberAxis()) {
                            gridpaneColumnConstraints {
                                percentWidth = 50.0
                                paddingRight = 10.0
                            }
                            series("New Clients") {
                                for (i in dates) {
                                    val date = LocalDate.parse(i.toString()).format(formatter).toString()
                                    data(date, Random.nextInt(10, 30))
                                }
                            }

                        }

                        piechart("State of the rooms") {
                            gridpaneColumnConstraints {
                                percentWidth = 50.0
                            }

                            data("Free", Random.nextDouble(5.0, 15.0))
                            data("Rented", Random.nextDouble(40.0, 65.0))
                            data("maintenance", Random.nextDouble(1.0, 7.0))
                        }
                    }
                }
            }
        }
    }
}
