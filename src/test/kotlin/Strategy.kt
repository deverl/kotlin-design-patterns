import java.util.*
import kotlin.test.Test

// Strategy design pattern
// A class behavior or its algorithm can be changed at run time
// Objects can be replaced with another object that has a different behavior
// Objects contain behavior as a field
// Context object that can handle algorithm objects
// Useful when we want to be able to add functionality without changing program structure


class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(string: String) {
        println(stringFormatterStrategy(string))
    }
}


val lowerCaseFormatter = { it: String -> it.lowercase(Locale.getDefault()) }
val upperCaseFormatter = { it: String -> it.uppercase(Locale.getDefault()) }


// Test

class StrategyTest {
    @Test
    fun testStrategy() {
        val lowerCasePrinter = Printer(lowerCaseFormatter)
        val upperCasePrinter = Printer(upperCaseFormatter)
        val s = "Hello LOREM ipsum DOLOR sit AmeT"
        lowerCasePrinter.printString(s)
        upperCasePrinter.printString(s)
    }
}
