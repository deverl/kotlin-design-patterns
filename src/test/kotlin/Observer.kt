import org.junit.jupiter.api.Test
import java.io.File

// Observer design pattern
//
// Define a one-to-many dependency between objects so that when one object
// changes state, all its dependents are notified and updated automatically
//
// Notify multiple objects simultaneously about a change in state


interface EventListener {
    fun update(event: String?, file: File?)
}

class EventManager(vararg operations: String) {
    private val listeners = hashMapOf<String, MutableList<EventListener>>()

    init {
        operations.forEach { operation ->
            listeners[operation] = mutableListOf()
        }
    }

    fun subscribe(eventType: String, listener: EventListener) {
        listeners[eventType]?.add(listener)
    }

    fun unsubscribe(eventType: String, listener: EventListener) {
        listeners[eventType]?.remove(listener)
    }

    fun notify(eventType: String, file: File) {
        listeners[eventType]?.forEach { it.update(eventType, file) }
    }
}


class Editor {
    private val eventManager = EventManager("open", "save")
    private var file: File? = null

    fun openFile(filename: String) {
        println("Opening file $filename")
        eventManager.notify("open", File(filename))
    }

    fun saveFile(filename: String) {
        println("Saving file $filename")
        eventManager.notify("save", File(filename))
    }

    fun subscribe(eventType: String, listener: EventListener) {
        eventManager.subscribe(eventType, listener)
    }

    fun unsubscribe(eventType: String, listener: EventListener) {
        eventManager.unsubscribe(eventType, listener)
    }
}


// Listeners

class EmailNotificationListener(private val email: String) : EventListener {
    override fun update(event: String?, file: File?) {
        println("EmailNotificationListener: Email to $email. Someone has performed $event operation with the following file: $file")
    }
}


class LogOpenListener(val filename: String) : EventListener {
    override fun update(event: String?, file: File?) {
        println("LogOpenListener: Writing to log $filename. Someone has performed $event operation with the following file: $file")
    }
}


// Test

class ObserverTest {
    @Test
    fun testObserver() {
        val editor = Editor()
        val logListener = LogOpenListener("log.txt")
        val emailListener = EmailNotificationListener("user@example.com")

        editor.subscribe("open", logListener)
        editor.subscribe("open", emailListener)
        editor.subscribe("save", emailListener)

        println("---------------------------------------")
        editor.openFile("test.txt")
        editor.saveFile("test.txt")
        println("---------------------------------------")
    }
}
