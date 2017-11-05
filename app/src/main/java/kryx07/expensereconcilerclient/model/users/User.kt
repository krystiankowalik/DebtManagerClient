package kryx07.expensereconcilerclient.model.users

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import java.io.Serializable

@Entity(tableName = "users")
data class User(@PrimaryKey var id: Int,
                var username: String,
                var password: String) : Serializable {


    /*var username: String?=null
    var password: String?=null*/
    //@Relation(parentColumn = "id", entityColumn = "groupId", entity = Group::class)
    //var groups: MutableList<Group>?=null
    /*var username: String? = null
    var password: String? = null
    @Relation(parentColumn = "id",entityColumn = "groupId",entity = Group::class)
    var groups: MutableList<Group>? = null*/


}