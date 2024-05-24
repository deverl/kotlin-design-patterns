import kotlin.test.Test

// Command design pattern
// Encapsulate a request as an object that contains all the information about the request
// Decouple sender from receiver
// The command object is then passed to the correct handler

interface Command {
    fun execute()
}


class OrderAddCommand(val id: Long) : Command {
    override fun execute() {
        println("Adding order with id $id")
    }
}


class OrderPayCommand(val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}


class CommandProcessor {
    private val q = mutableListOf<Command>()
    fun addToQueue(command: Command): CommandProcessor = apply { q.add(command) }
    fun processCommands(): CommandProcessor = apply {
        q.forEach { it.execute() }
        q.clear()
    }
}


// Test

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor()
            .addToQueue(OrderAddCommand(1))
            .addToQueue(OrderAddCommand(2))
            .addToQueue(OrderPayCommand(2))
            .addToQueue(OrderPayCommand(1))
            .processCommands()
    }
}
