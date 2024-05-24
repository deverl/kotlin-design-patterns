import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test


class AlertBox {
    var message: String? = null

    fun show() {
        println("AlertBox: $this:  $message")
    }
}


class Window {
    val box by lazy { AlertBox() }

    fun showMessage(message: String) {
        box.message = message
        box.show()
    }
}


class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String) {
        if (!::box.isInitialized) {
            box = AlertBox()
        }
        // box = AlertBox()
        box.message = message
        box.show()
    }
}


class WindowTest {
    @Test
    fun testWindow() {
        val window = Window()
        window.showMessage("Hello, World1!")
        assertThat(window.box).isNotNull()
        println("Window.box: ${window.box}")
        window.showMessage("Hello, World1.2!")
        println("Window.box: ${window.box}")

        val window2 = Window2()
        window2.showMessage("Hello, World2!")
        assertThat(window2.box).isNotNull()
        println("Window2.box: ${window2.box}")
        window2.showMessage("Hello, World2.2!")
        assertThat(window2.box).isNotNull()
        println("Window2.box: ${window2.box}")
    }
}
