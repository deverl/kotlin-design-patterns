import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

// Complex System Store

class ComplexSystemStore(private val filePath: String) {
    private val cache: HashMap<String, String>

    init {
        println("Store data to complex system")
        println("Read data from file: $filePath")
        cache = HashMap()
        // Read props from file and put into cache
    }

    fun store(key: String, payload: String) {
        cache[key] = payload
    }

    fun read(key: String): String {
        return cache[key] ?: ""
    }

    fun commit() {
        println("Write data to file: $filePath")
    }
}


data class User(val login: String)

class UserRepository {
    private val store = ComplexSystemStore("data.db")

    fun save(user: User) {
        store.store("USER_KEY", user.login)
        store.commit()
    }

    fun findFirst(): User {
        val user = store.read("USER_KEY")
        return User(user)
    }
}


class FacadeTest {
    @Test
    fun testFacade() {
        val userRepository = UserRepository()
        val user = User("user1")
        userRepository.save(user)

        val userFromDb = userRepository.findFirst()
        println(userFromDb)

        assertThat(userFromDb).isEqualTo(user)
    }
}
