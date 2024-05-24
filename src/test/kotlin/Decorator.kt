import kotlin.test.Test

// Attach new behavior to an object
// Without altering existing code
// Over riding existing behavior


interface CoffeeMaker {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}


class NormalCoffeeMaker : CoffeeMaker {
    override fun makeSmallCoffee() {
        println("Normal: Making small coffee")
    }

    override fun makeLargeCoffee() {
        println("Normal: Making large coffee")
    }
}


// Decorator

class EnhancedCoffeeMaker(private val coffeeMaker: CoffeeMaker) : CoffeeMaker by coffeeMaker {
    fun makeMilkCoffee() {
        println("Enhanced: Making milk coffee")
        coffeeMaker.makeLargeCoffee()
        println("Enhanced: Adding milk")
    }

    fun makeSugarCoffee() {
        println("Enhanced: Making sugar coffee")
        coffeeMaker.makeSmallCoffee()
        println("Enhanced: Adding sugar")
    }

    override fun makeLargeCoffee() {
        println("Enhanced: Making large coffee")
    }

//    override fun makeSmallCoffee() {
//        println("Enhanced: Making small coffee")
//    }
}


class DecoratorTest {
    @Test
    fun testDecorator() {
        val normalCoffeeMaker = NormalCoffeeMaker()
        val enhancedCoffeeMaker = EnhancedCoffeeMaker(normalCoffeeMaker)

        println("----------------------------")
        enhancedCoffeeMaker.makeSmallCoffee()
        println("----------------------------")
        enhancedCoffeeMaker.makeLargeCoffee()
        println("----------------------------")
        enhancedCoffeeMaker.makeMilkCoffee()
        println("----------------------------")
        enhancedCoffeeMaker.makeSugarCoffee()
        println("----------------------------")
    }
}
