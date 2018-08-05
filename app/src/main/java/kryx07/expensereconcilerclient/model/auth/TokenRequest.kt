package kryx07.expensereconcilerclient.model.auth

class TokenRequest(val username: String,
                   val password: String,
                   val grant_type: String,
                   val client_id: String,
                   val scope: String)
