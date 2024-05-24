// Singleton pattern


import org.junit.jupiter.api.Test
import kotlin.test.assertSame

object NetworkDriver {
    init {
        println("Initializing network driver: $this")
    }

    fun connect() {
        println("Connecting to network")
    }

    fun log(): NetworkDriver = apply { println("Logging network driver: $this") }
}


class SingletonTest {
    @Test
    fun testSingleton() {
        println("Singleton test")
        val driver1 = NetworkDriver.log()
        val driver2 = NetworkDriver.log()
        assertSame(driver1, driver2)
        assertSame(driver1, NetworkDriver)
        assertSame(driver2, NetworkDriver)
    }
}
