import kotlin.test.Test

// Mediator design pattern
// Provides a centralized communication medium between different objects in a system
// Objects no longer communicate directly with each other, but instead communicate through the mediator
// Reduces the dependencies between communicating objects

class ChatUser(private val name: String, private val chatMediator: Mediator) {
    fun send(message: String) {
        println("$name: Sending message: $message")
        chatMediator.sendMessage(message, this)
    }

    fun receive(message: String) {
        println("$name: Received message: $message")
    }
}

class Mediator {
    private val users = mutableListOf<ChatUser>()

    fun sendMessage(message: String, user: ChatUser) {
        users.filter { it != user }.forEach { it.receive(message) }
    }

    fun addUser(user: ChatUser): Mediator = apply { users.add(user) }
}


// Test

class MediatorTest {
    @Test
    fun testMediator() {
        val mediator = Mediator()
        val john = ChatUser("John", mediator)
        val jane = ChatUser("Jane", mediator)
        val alice = ChatUser("Alice", mediator)
        val bob = ChatUser("Bob", mediator)
        val carol = ChatUser("Carol", mediator)

        mediator.addUser(john)
        mediator.addUser(jane)
        mediator.addUser(alice)
        mediator.addUser(bob)
        mediator.addUser(carol)

        john.send("Hi there!")

        carol.send("Howdy!")
    }
}
