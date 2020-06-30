package base.view

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/*
class MainView : View() {
    override val root = vbox {
        title = "Maybe"
        top<UserView>
    }
}
*/

class MainView : View() {

    val input = SimpleStringProperty()
    override val root = vbox {
        title = "User"
        form {
            fieldset {
                field("Input") {
                    textfield(input)
                }

                button("Commit") {
                    action {
                        openInternalWindow<MyFragment>()
                        input.value = ""
                    }
                }
            }
        }
        button ("WORKER") {
            action {
                replaceWith(WorkerView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.UP))
            }
        }
    }
}

class WorkerView : View() {

    val input = SimpleStringProperty()
    override val root = vbox {
        form {
            fieldset {
                field("Input") {
                    textfield(input)
                }

                button("Commit") {
                    action {
                        openInternalWindow<MyFragment>()
                        input.value = ""
                    }
                }
            }
        }
        button ("USER") {
            id = "UserButton"
            action {
                replaceWith(MainView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.DOWN))
            }
        }
    }
}


class MyFragment: Fragment() {
    override val root = label("This is a popup")

    fun changeSize(num: Int){
        
    }
}