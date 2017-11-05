package kryx07.expensereconcilerclient.model.users

import java.io.Serializable

data class Users(var users: Set<User>) : Serializable {

    fun getByUserName(username: String): User? = users.find { user -> user.username == username }
}