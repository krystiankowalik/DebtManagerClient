package kryx07.expensereconcilerclient.model.users

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
        @Id var id: Long,
        var username: String,
        var password: String
)