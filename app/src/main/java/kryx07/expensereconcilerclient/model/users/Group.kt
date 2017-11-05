package kryx07.expensereconcilerclient.model.users

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import kryx07.expensereconcilerclient.model.transactions.Transaction

@Entity(tableName = "groups")
data class Group(@PrimaryKey var id: Int,
                 var name: String) {


    //@Relation(parentColumn = "id", entityColumn = "userId", entity = User::class)
    //var users: MutableList<User>? = null

    //var transactions: MutableList<Transaction>? = null


}