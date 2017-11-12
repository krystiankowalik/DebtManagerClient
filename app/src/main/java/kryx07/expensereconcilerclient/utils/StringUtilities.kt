package kryx07.expensereconcilerclient.utils

import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

object StringUtilities {

    fun formatCurrency(number: BigDecimal, currency: String): String {
        return NumberFormat.getCurrencyInstance().format(number) + " " + currency
    }

    fun formatDate(date: DateTime): String {
        return date.toString("yyyy-MM-dd", Locale.US)
    }
    fun formatDate(date:LocalDate): String {
        return date.toString("yyyy-MM-dd", Locale.US)
    }
}
