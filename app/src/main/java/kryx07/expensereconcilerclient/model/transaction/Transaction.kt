package kryx07.expensereconcilerclient.model.transaction

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kryx07.expensereconcilerclient.model.money.Money
import kryx07.expensereconcilerclient.model.transaction.split.Split
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.io.Serializable
import java.math.BigDecimal

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Document(collection = "transaction")
@Parcelize
data class Transaction(val id: String,
                       val publicId: String,
                       val date: LocalDate,
                       val money: Money,
                       val description:String="",
                       val type: Type,
                       val split: Split,
                       val added: LocalDateTime,
                       val addedBy: String,
                       val lastModified: LocalDateTime,
                       val lastModifiedBy: String
) : Parcelable,Serializable {

    enum class Type(val factor: BigDecimal) {
        INCOME(BigDecimal.ONE), EXPENSE(INCOME.factor.negate())
    }
}
