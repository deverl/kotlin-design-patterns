import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

// Chain of Responsibility Pattern
// Define a chain of receiver handlers to process a request
// Each handler contains a reference to the next handler in the chain
// Each handler decides either to process the request or to pass it to the next handler in the chain
// Requests can be of any type

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(val token: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String): String {
        val nl = if (inputHeader.isEmpty()) "" else "\n"
        return "$inputHeader${nl}Authorization: Bearer $token"
            .let {
                next?.addHeader(it) ?: it
            }
    }
}


class ContentType(val contentType: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String): String {
        val nl = if (inputHeader.isEmpty()) "" else "\n"
        return "$inputHeader${nl}Content-Type: $contentType"
            .let {
                next?.addHeader(it) ?: it
            }
    }
}

class BodyPayloadHeader(val body: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String): String {
        val nl = if (inputHeader.isEmpty()) "" else "\n"
        return "$inputHeader${nl}$body"
            .let {
                next?.addHeader(it) ?: it
            }
    }
}


// Test

class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("12345")
        val contentType = ContentType("application/json")
        val bodyPayloadHeader = BodyPayloadHeader("{\"name\": \"John\"}")

        authenticationHeader.next = contentType
        contentType.next = bodyPayloadHeader

        val messageWithAuth = authenticationHeader.addHeader("Headers with authentication")
        println("---------------------------------------------------------")
        println(messageWithAuth)
        println("---------------------------------------------------------")
        val messageWithoutAuth = contentType.addHeader("Headers without authentication")
        println(messageWithoutAuth)
        println("---------------------------------------------------------")

        assertThat(messageWithAuth).contains("Authorization: Bearer 12345")
        assertThat(messageWithAuth).contains("Content-Type: application/json")
        assertThat(messageWithAuth).contains("{\"name\": \"John\"")
        assertThat(messageWithoutAuth).doesNotContain("Authorization: Bearer 12345")
        assertThat(messageWithoutAuth).contains("Content-Type: application/json")
        assertThat(messageWithoutAuth).contains("{\"name\": \"John\"")
    }
}
