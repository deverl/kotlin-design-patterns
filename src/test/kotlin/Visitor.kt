import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

// Visitor design pattern
// Separation between an algorithm and the objects they operate on
// 2 concepts: visitor and contract (visitable)
// Visitor is an interface with a visit method for each contract type
// The contract accepts a visitor and calls the visitor's method
// Visitors perform operations on elements


interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}


class FixedPriceContract(val costPerYear: Int) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}

class TimeAndMaterialsContract(val costPerHour: Int, val hours: Int) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}

class SupportContract(val costPerMonth: Int) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }
}


interface ReportVisitor<R> {
    fun visit(contract: FixedPriceContract): R
    fun visit(contract: TimeAndMaterialsContract): R
    fun visit(contract: SupportContract): R
}


class MonthlyCostReportVisitor : ReportVisitor<Int> {
    override fun visit(contract: FixedPriceContract): Int {
        return contract.costPerYear / 12
    }

    override fun visit(contract: TimeAndMaterialsContract): Int {
        return contract.costPerHour * contract.hours
    }

    override fun visit(contract: SupportContract): Int {
        return contract.costPerMonth
    }
}


class YearlyCostReportVisitor : ReportVisitor<Int> {
    override fun visit(contract: FixedPriceContract): Int {
        return contract.costPerYear
    }

    override fun visit(contract: TimeAndMaterialsContract): Int {
        return contract.costPerHour * contract.hours
    }

    override fun visit(contract: SupportContract): Int {
        return contract.costPerMonth * 12
    }
}


// Test

class VisitorTest {
    @Test
    fun testVisitor() {
        val contracts = listOf(
            FixedPriceContract(12_000),
            TimeAndMaterialsContract(50, 150),
            TimeAndMaterialsContract(40, 140),
            SupportContract(500)
        )

        val monthlyCostReport = MonthlyCostReportVisitor()
        val yearlyCostReport = YearlyCostReportVisitor()

        val monthlyCost = contracts.sumOf { it.accept(monthlyCostReport) }
        val yearlyCost = contracts.sumOf { it.accept(yearlyCostReport) }

        println("Monthly cost: $monthlyCost")
        println("Yearly cost: $yearlyCost")

        assertThat(monthlyCost).isEqualTo(12_000 / 12 + 50 * 150 + 40 * 140 + 500)
        assertThat(yearlyCost).isEqualTo(12_000 + 50 * 150 + 40 * 140 + 500 * 12)
    }
}
