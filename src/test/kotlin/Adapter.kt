import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

// 3rd party
data class DisplayDataType(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayDataType) {
        println("Displaying data: ${data.index} - ${data.data}")
    }
}


// Our code

data class DatabaseData(val position: Int, val amount: Int)

class DatabaseGenerator {
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(1, 100))
        list.add(DatabaseData(2, 200))
        list.add(DatabaseData(3, 300))
        return list
    }
}

interface DataBaseAdapter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay) : DataBaseAdapter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val displayData = arrayListOf<DisplayDataType>()
        data.forEach {
            val datum = DisplayDataType(it.position.toFloat(), it.amount.toString())
            displayData.add(datum)
            display.displayData(datum)
        }
        return displayData
    }
}


class AdapterTest {
    @Test
    fun testAdapter() {
        val generator = DatabaseGenerator()
        val data = generator.generateData()
        val display = DataDisplay()
        val adapter = DataDisplayAdapter(display)
        val convertedData = adapter.convertData(data)

        assertThat(convertedData.size).isEqualTo(3)
        assertThat(convertedData[0]).isEqualTo(DisplayDataType(1.0f, "100"))
        assertThat(convertedData[1]).isEqualTo(DisplayDataType(2.0f, "200"))
        assertThat(convertedData[2]).isEqualTo(DisplayDataType(3.0f, "300"))
    }
}
