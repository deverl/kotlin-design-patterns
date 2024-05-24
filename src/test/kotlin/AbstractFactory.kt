import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

interface DataSource

class DatabaseDataSource : DataSource

class NetworkDataSource : DataSource

abstract class DataSourceFactory {
    abstract fun createDataSource(): DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory {
            return when (T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class NetworkFactory : DataSourceFactory() {
    override fun createDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory : DataSourceFactory() {
    override fun createDataSource(): DataSource = DatabaseDataSource()
}

class AbstractFactoryTest {
    @Test
    fun abstractFactoryTest() {
        val dataSourceFactory: DataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = dataSourceFactory.createDataSource()
        println("Created datasource $dataSource")
        assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)

        val dataSourceFactory2 = DataSourceFactory.createFactory<NetworkDataSource>()
        val dataSource2 = dataSourceFactory2.createDataSource()
        println("Created datasource $dataSource2")
        assertThat(dataSource2).isInstanceOf(NetworkDataSource::class.java)
    }
}
