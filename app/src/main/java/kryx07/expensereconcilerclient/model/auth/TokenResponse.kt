package kryx07.expensereconcilerclient.model.auth

class TokenResponse(val access_token: String,
                    val expires_in: String,
                    val refresh_expires_in: String,
                    val refresh_token: String,
                    val token_type: String)
