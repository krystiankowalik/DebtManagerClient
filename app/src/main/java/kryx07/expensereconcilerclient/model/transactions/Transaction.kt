package kryx07.expensereconcilerclient.model.transactions


import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kryx07.expensereconcilerclient.model.users.User
import kryx07.expensereconcilerclient.model.users.Users
import org.joda.time.LocalDate

import java.io.Serializable
import java.math.BigDecimal

@Entity(tableName = "transactions")
class Transaction : Serializable {

    @PrimaryKey var id: String? = null
    var amount: BigDecimal? = null
    var description: String? = null
    var date: LocalDate? = null
    var common: Boolean? = null
    @Embedded var payer: User? = null
    @Ignore var users: Users? = null
    @Ignore var payables: Payables? = null

    override fun toString(): String {
        return "Transaction(id=$id, amount=$amount, description=$description, date=$date, common=$common, payer=$payer, users=$users, payables=$payables)"
    }


}
