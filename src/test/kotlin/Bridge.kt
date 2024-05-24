import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

interface Device {
    var volume: Int
    fun getName(): String
}


class Radio : Device {
    override var volume: Int = 0
    override fun getName(): String = "Radio"
}

class TV : Device {
    override var volume: Int = 0
    override fun getName(): String = "TV"
}

interface Remote {
    fun volumeDown()
    fun volumeUp()
}


class BasicRemote(private val device: Device) : Remote {
    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down: ${device.volume}")
    }

    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up: ${device.volume}")
    }
}


class BridgeTest {
    @Test
    fun testBridge() {
        val radio = Radio()
        val tv = TV()

        val radioRemote = BasicRemote(radio)
        val tvRemote = BasicRemote(tv)

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        assertThat(radio.volume).isEqualTo(1)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        assertThat(tv.volume).isEqualTo(2)
    }
}
