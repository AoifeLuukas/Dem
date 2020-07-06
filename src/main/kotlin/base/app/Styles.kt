package base.app

import javafx.scene.paint.*
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val LoginRight by cssid("LoginRight")
    }

    init {
        root {
            prefWidth = 900.px
            prefHeight = 600.px
            backgroundColor += Color.WHITE
        }

        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        LoginRight {
            borderColor += box( Color.BLACK )
            borderWidth += box(5.px)
        }
    }
}