package kryx07.expensereconcilerclient.model.transactions


import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User
import org.joda.time.LocalDate
import java.io.Serializable
import java.math.BigDecimal

//@Entity(tableName = "transactions")
class Transaction(
        var id: Int,
        var amount: BigDecimal,
        var description: String,
        var date: LocalDate,
        var common: Boolean,
        var group: Group,
        var payer: User

) : Serializable {

   /* var id: Int? = null
    var amount: BigDecimal? = null
    var description: String? = null
    var date: LocalDate? = null
    var common: Boolean? = null
    var group: Group? = null
    var payer: User? = null*/
    //@Embedded var payer: User? = null
    //@Embedded var group: Group? = null
    //@Ignore var payables: Payables? = null

    override fun toString(): String =
            "Transaction(id=$id, amount=$amount, description=$description, date=$date, common=$common)"


}
