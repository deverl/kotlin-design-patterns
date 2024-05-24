import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

sealed class Country {
    object Canada : Country() {
        init {
            println("Initializing Canada")
        }
    }
}


object Spain : Country()
class Greece(val someProp: String) : Country()
data class USA(val someProp: String) : Country()


class Currency(val code: String)


object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency {
        return when (country) {
            is Country.Canada -> Currency("CAD")
            Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
        }
    }
}


class FactoryMethodTest {
    @Test
    fun testFactoryMethod() {
        val currency1 = CurrencyFactory.currencyForCountry(Country.Canada)
        assertEquals("CAD", currency1.code)
        val currency2 = CurrencyFactory.currencyForCountry(Spain)
        assertEquals("EUR", currency2.code)
        val currency3 = CurrencyFactory.currencyForCountry(Greece("Athens"))
        assertEquals("EUR", currency3.code)
        val currency4 = CurrencyFactory.currencyForCountry(USA("Washington"))
        assertEquals("USD", currency4.code)
    }
}
