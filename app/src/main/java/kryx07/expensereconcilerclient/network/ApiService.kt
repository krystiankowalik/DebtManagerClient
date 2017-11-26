package kryx07.expensereconcilerclient.network

import io.reactivex.Observable
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @get:GET("users/")
    val allUsers: Observable<List<User>>

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
