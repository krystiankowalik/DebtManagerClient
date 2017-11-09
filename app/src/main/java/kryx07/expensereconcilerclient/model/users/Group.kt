package kryx07.expensereconcilerclient.model.users

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Group(
        @Id var id: Long,
        var name: String
)