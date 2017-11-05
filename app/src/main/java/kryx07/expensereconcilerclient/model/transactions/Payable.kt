package kryx07.expensereconcilerclient.model.transactions

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import kryx07.expensereconcilerclient.model.users.User
import java.io.Serializable
import java.math.BigDecimal

@Entity(tableName = "Payables")
data class Payable(@PrimaryKey var id: String)
                   /*@Embedded(prefix = "payer")
                   var payer: User,
                   @Embedded(prefix = "debtor") var debtor: User,
                   var amount: BigDecimal) : Serializable*/

