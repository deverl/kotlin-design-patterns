import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class Shape : Cloneable {
    var id: String? = null
    var type: String? = null

    abstract fun draw()

    public override fun clone() : Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}


class Rectangle: Shape() {
    init {
        type = "Rectangle"
    }

    override fun draw() {
        println("Inside Rectangle::draw() method.")
    }
}

class Square: Shape() {
    init {
        type = "Square"
    }
    override fun draw() {
        println("Inside Square::draw() method.")
    }
}

class Circle: Shape() {
    init {
        type = "Circle"
    }
    override fun draw() {
        println("Inside Circle::draw() method.")
    }
}


object ShapeCache {
    private val shapeMap = hashMapOf<String? , Shape>()

    fun loadCache() {
        val circle = Circle()
        val rectangle = Rectangle()
        val square = Square()

        shapeMap["1"] = circle
        shapeMap["2"] = square
        shapeMap["3"] = rectangle
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }
}


class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val clonedShape1 = ShapeCache.getShape("1")
        val clonedShape2 = ShapeCache.getShape("2")
        val clonedShape3 = ShapeCache.getShape("3")

        clonedShape1.draw()
        clonedShape2.draw()
        clonedShape3.draw()

        assertThat(clonedShape1.type).isEqualTo("Circle")
        assertThat(clonedShape2.type).isEqualTo("Square")
        assertThat(clonedShape3.type).isEqualTo("Rectangle")
    }
}
