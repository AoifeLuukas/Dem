package base.data

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.time.LocalDate


class Data:Controller() {
    var users = mutableListOf<User>(
            User(1,
                    1,
                    "Raul",
                    "Lopez",
                    "Raul@random.com",
                    "password",
                    LocalDate.of(1111, 1, 1),
                    "11.111.111-1"
            ),
            User(2,
                    2,
                    "Pepe",
                    "Veraz",
                    "Pepe@random.com",
                    "password",
                    LocalDate.of(1111, 1, 1),
                    "11.111.111-1"
            ),
            User(3,
                    3,
                    "Julio",
                    "Cesar",
                    "Julio@random.com",
                    "password",
                    LocalDate.of(1111, 1, 1),
                    "11.111.111-1",
                    listOf<coupon>(
                            coupon(1,
                                    "First Coupon",
                                    "This is your first coupon, for a 2x1 promotion"
                            ),
                            coupon(2,
                                    "Second Coupon",
                                    "30% OFF for Valentines Day"
                            ),
                            coupon(3,
                                    "Third Coupon",
                                    "Free ride to hell"
                            )
                    ).asObservable()
            )
    ).asObservable()

    var activeUser:User? = null

    var newUser:User? = null

    var makeNewUser: Boolean = false

    var reservas = mutableListOf<Reserva>(
            Reserva(1,
                    10.0,
                    LocalDate.of(2020, 7, 10),
                    LocalDate.of(2020, 7, 16),
                    1
            ),
            Reserva(2,
                    15.0,
                    LocalDate.of(2020, 7, 8),
                    LocalDate.of(2020, 7, 15),
                    1
            )
    ).asObservable()

    var rooms = mutableListOf<room>(
            room(
                    1, "Palace", 10.0, 1
            ),
            room (
                    2, "Basement", 15.0, 2
            ),
            room (
                    3, "Basement 2", 13.0, 3
            )
    )


    fun login (email: String?, password: String?): Boolean {
        val user: User? = users.find { it.email == email }
        return if (user != null) {
            if (user.password == password) {
                activeUser = user
                true
            } else {
                activeUser = null
                false
            }
        } else {
            activeUser = null
            false
        }
    }

    fun register (user :User): Boolean {
        user.id = users.maxBy { it.id }!!.id+1
        user.type = 3
        println(user.id)
        println(user.type)
        println(user.firstName)
        println(user.lastName)
        println(user.email)
        println(user.birthday)
        println(user.identification)
        println(user.password)
        return if ((users.find {it.identification == user.identification} == null)
                and (user.firstName != "") and (user.lastName != "") and (user.email != "") and (user.password != "")
                and (user.identification != "") and ((users.find {it.email == user.email} == null))
        ) {
            this.users.add(user)
            true
        } else {
            false
        }
    }

    fun newReserva(reserva: Reserva): Boolean {
        return if (reserva.endingDate.isAfter(reserva.startingDate)
                and (reserva.startingDate.isAfter(LocalDate.now()) or reserva.startingDate.isEqual(LocalDate.now()))) {
            println(reservas.size)
            this.reservas.add(reserva)
            println(reservas.size)
            println(reservas.last().userId)
            true
        } else {
            false
        }
    }
}

//  Room

class room {
    val id: Int
    val name: String
    val price: Double
    var status: Int = 1

    constructor(id: Int = 0, name: String, price: Double = 0.0) {
        this.id = id
        this.name = name
        this.price = price
    }


    constructor(id: Int = 0, name: String, price: Double = 0.0, status: Int) {
        this.id = id
        this.name = name
        this.price = price
        this.status = status
    }
}

class roomModel : ItemViewModel<room>() {
    val id = bind(room::id)
    val name = bind(room::name)
    val price = bind(room::price)
    val status = bind(room::status)
}


//  Coupon

class coupon {
    val id: Int
    val name: String
    val description: String

    constructor(id: Int, name: String, description: String) {
        this.id = id
        this.name = name
        this.description = description
    }
}

//  Reserva

class Reserva(roomId:Int = 0, roomPrice:Double = 0.0, startingDate: LocalDate = LocalDate.now(), endingDate: LocalDate = LocalDate.now(), userId: Int = 0) {
    val roomIdProperty = SimpleIntegerProperty(this, "roomId", roomId)
    var roomdId by roomIdProperty

    val roomPriceProperty = SimpleDoubleProperty(this, "roomPrice", roomPrice)
    var roomdPrice by roomPriceProperty

    val startingDateProperty = SimpleObjectProperty<LocalDate>(this, "startingDate", startingDate)
    var startingDate by startingDateProperty

    val endingDateProperty = SimpleObjectProperty<LocalDate>(this, "endingDate", endingDate)
    var endingDate by endingDateProperty

    val userIdProperty = SimpleIntegerProperty(this, "userId", userId)
    var userId by userIdProperty

}

class ReservaModel(reserva: Reserva = Reserva()) : ItemViewModel<Reserva>(reserva) {
    val roomId = bind(Reserva::roomIdProperty)
    val roomPrice = bind(Reserva::roomPriceProperty)
    val startingDate = bind(Reserva::startingDateProperty)
    val endingDate = bind(Reserva::endingDateProperty)
    val userId = bind(Reserva::userIdProperty)
}

//  User

class User(id: Int, type: Int, firstName: String, lastName: String, email: String, password: String, birthday: LocalDate, identification: String, var coupons: List<coupon> = listOf<coupon>().asObservable()) {
    val idProperty = SimpleObjectProperty<Int>(this, "id", id)
    var id by idProperty

    val typeProperty = SimpleObjectProperty<Int>(this, "type", type)
    var type by typeProperty

    val firstNameProperty = SimpleObjectProperty<String>(this, "firstName", firstName)
    var firstName by firstNameProperty

    val lastNameProperty = SimpleObjectProperty<String>(this, "lastName", lastName)
    var lastName by lastNameProperty

    val emailProperty = SimpleObjectProperty<String>(this, "email", email)
    var email by emailProperty

    val passwordProperty = SimpleObjectProperty<String>(this, "password", password)
    var password by passwordProperty

    val birthDayProperty = SimpleObjectProperty<LocalDate>(this, "birthday", birthday)
    var birthday by birthDayProperty

    val identificationProperty = SimpleObjectProperty<String>(this, "identification", identification)
    var identification by identificationProperty

}

class UserModel(user: User) : ItemViewModel<User>(user) {
    val id = bind(User::idProperty)
    val type = bind(User::typeProperty)
    val firstName = bind(User::firstNameProperty)
    val lastName = bind(User::lastNameProperty)
    val email = bind(User::emailProperty)
    val password = bind(User::passwordProperty)
    val birthday = bind(User::birthDayProperty)
    val identification = bind(User::identificationProperty)
    val coupons = bind(User::coupons)
}


