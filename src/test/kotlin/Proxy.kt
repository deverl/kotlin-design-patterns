import org.junit.jupiter.api.Test

// Proxy design pattern
// Provide some functionality before an/or after calling an object
// Similar to facade, except the proxy has the same interface as the object it is proxying
// Similar to decorator, except the proxy manages the lifecycle of the object it is proxying

interface Image {
    fun display()
}

class RealImage(private val filename: String) : Image {
    init {
        loadFromDisk()
    }

    override fun display() {
        println("RealImage: Displaying $filename")
    }

    private fun loadFromDisk() {
        println("RealImage: Loading $filename")
    }
}


class ProxyImage(private val filename: String) : Image {
    private var realImage: RealImage? = null

    override fun display() {
        println("ProxyImage: Displaying $filename")
        if (realImage == null) {
            realImage = RealImage(filename)
        }
        realImage?.display()
    }
}


class ProxyTest {
    @Test
    fun testProxy() {
        val image = ProxyImage("chumbo.png")
        image.display()
        println("---------------------------")
        // Load image from "cache"
        image.display()
    }
}
