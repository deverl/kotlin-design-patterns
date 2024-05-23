import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Calculator  {
    fun sum(a: Int, b: Int): Int
    {
        return a + b
    }
}


class CalculatorTest {
    @Test
    fun testSum() {
        val calculator = Calculator()
        assertEquals(4, calculator.sum(2, 2))
        Assertions.assertEquals(5, calculator.sum(2, 3))
    }
}
