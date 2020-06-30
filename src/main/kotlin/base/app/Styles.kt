package base.app

import javafx.scene.paint.*
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val UserButton by cssid("UserButton")
    }

    init {
        root {
            prefWidth = 900.px
            prefHeight = 600.px
            backgroundColor += Color.BLACK
            textFill = Color.WHITE
        }
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        UserButton {
            prefWidth = 900.px
        }
    }
}