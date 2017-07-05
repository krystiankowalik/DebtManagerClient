package kryx07.expensereconcilerclient.model.transactions


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kryx07.expensereconcilerclient.model.users.User
import kryx07.expensereconcilerclient.model.users.Users
import org.joda.time.LocalDate

import java.io.Serializable
import java.math.BigDecimal

open class Transaction(@PrimaryKey var id: String? = null,
                       var amount: BigDecimal? = null,
                       var description: String? = null,
                       var date: LocalDate? = null,
                       var common: Boolean? = null,
                       var payer: User? = null,
                       var users: Users? = null,
                       var payables: Payables? = null) : RealmObject(), Serializable {



}
