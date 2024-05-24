import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

// Composite
// Compose objects into tree structures
// Works when the core functionality can be represented as a tree
// Manipulate many objects as a single object


open class Equipment(open val price: Int, val name: String)

open class Composite(name: String) : Equipment(price = 0, name = name) {
    private val equipments = mutableListOf<Equipment>()

    fun add(equipment: Equipment) = apply { equipments.add(equipment) }

    fun remove(equipment: Equipment) {
        equipments.remove(equipment)
    }

    override val price: Int
        get() = equipments.sumOf { it.price }
}


class Computer : Composite(name = "Computer")
class Cabinet : Equipment(price = 150, name = "Cabinet")
class Processor : Equipment(price = 1000, name = "Processor")
class Memory : Composite(name = "Memory")
class Disk : Equipment(price = 250, name = "Disk")
class ROM : Equipment(price = 100, name = "ROM")
class RAM : Equipment(price = 200, name = "RAM")


class CompositeTest {
    @Test
    fun testComposite() {
        val computer = Computer()
        val cabinet = Cabinet()
        val processor = Processor()
        val memory = Memory().add(ROM()).add(RAM())
        val disk = Disk()

        computer.add(cabinet)
        computer.add(processor)
        computer.add(memory)
        computer.add(disk)

        assertThat(computer.name).isEqualTo("Computer")
        assertThat(computer.price).isEqualTo(1700)
    }
}
