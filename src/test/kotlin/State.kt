import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

// State design pattern
// An object changes its behavior based on its internal state
// At any moment, there's a finite number of states the object/program can be in
// State can be encapsulated in an object

sealed class AuthState

object Unauthenticated : AuthState()

data class Authenticated(val username: String) : AuthState()

class AuthPresenter {
    private var state: AuthState = Unauthenticated

    fun login(username: String) {
        state = Authenticated(username)
    }

    fun logout() {
        state = Unauthenticated
    }

    fun showState() {
        println("User $username is ${state::class.simpleName}")
    }

    val isAuthorized: Boolean
        get() = state is Authenticated

    val username: String
        get() = (state as? Authenticated)?.username ?: "guest"

    fun loginUser(username: String) {
        login(username)
    }

    fun logoutUser() {
        logout()
    }

    override fun toString(): String {
        return "AuthPresenter: user $username is logged in: $isAuthorized"
    }
}


// Test

class StateTest {
    @Test
    fun testState() {
        val authPresenter = AuthPresenter()
        authPresenter.showState()
        assertThat(authPresenter.toString()).isEqualTo("AuthPresenter: user guest is logged in: false")
        authPresenter.loginUser("admin")
        assertThat(authPresenter.toString()).isEqualTo("AuthPresenter: user admin is logged in: true")
        authPresenter.showState()
        authPresenter.logoutUser()
        assertThat(authPresenter.toString()).isEqualTo("AuthPresenter: user guest is logged in: false")
        authPresenter.showState()
    }
}
