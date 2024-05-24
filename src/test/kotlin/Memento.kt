import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

// Memento design patter
// Save and restore the previous state of an object without revealing the details of its implementation
// 3 components:
//     Memento - stores the state
//     Originator - creates the state
//     Caretaker - decides to save and restore the state


data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento(): Memento {
        return Memento(state)
    }

    fun restoreFromMemento(memento: Memento) {
        state = memento.state
    }
}


class Caretaker {
    private val mementos = mutableListOf<Memento>()

    fun saveState(state: Memento) {
        mementos.add(state)
    }

//    fun restorePreviousState(): Memento {
//        if (mementos.size > 0) {
//            return mementos.removeLast()
//        } else {
//            throw IndexOutOfBoundsException()
//        }
//    }

    fun restoreState(index: Int): Memento {
        if (mementos.size > index) {
            return mementos[index]
        } else {
            throw IndexOutOfBoundsException()
        }
    }
}


// Test

class MementoTest {
    @Test
    fun testMemento() {
        val caretaker = Caretaker()
        val originator = Originator("State1")
        caretaker.saveState(originator.createMemento())
        originator.state = "State2"
        caretaker.saveState(originator.createMemento())
        originator.state = "State3"
        caretaker.saveState(originator.createMemento())
        assertThat(originator.state).isEqualTo("State3")
        originator.restoreFromMemento(caretaker.restoreState(1))
        assertThat(originator.state).isEqualTo("State2")
        originator.restoreFromMemento(caretaker.restoreState(0))
        assertThat(originator.state).isEqualTo("State1")
    }
}
