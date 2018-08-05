package kryx07.expensereconcilerclient.network

import io.reactivex.Observable
import kryx07.expensereconcilerclient.model.auth.TokenResponse
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User
import retrofit2.http.*

interface AuthApiService {

    @get:GET("users/")
    val allUsers: Observable<List<User>>

//    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("auth/realms/test/protocol/openid-connect/token")
    @FormUrlEncoded
    fun getAccessToken(@Field("username",encoded = true) username:String,
                       @Field("password",encoded = true) password:String,
                       @Field("client_id",encoded = true) clientId:String,
                       @Field("grant_type",encoded = true) grantType:String
                       ): Observable<TokenResponse>

    @GET("users/{id}/transactions")
    fun getUsersTransactions(@Path("id") id: Int): Observable<List<Transaction>>

    @GET("groups/byUserId/")
    fun getUsersGroups(@Query("userId") userId: Int): Observable<List<Group>>

    @GET("transactions/{id}")
    fun getTransactionById(@Path("id") transactionId: Int): Observable<Transaction>

    @POST("transactions")
    fun addTransaction(@Body transaction: Transaction): Observable<Transaction>

    @PUT("transactions/{id}")
    fun updateTransaction(@Path("id") transactionId: Int, @Body transaction: Transaction): Observable<Transaction>


}
