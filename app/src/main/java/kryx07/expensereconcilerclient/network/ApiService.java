package kryx07.expensereconcilerclient.network;

import java.util.List;

import io.reactivex.Observable;
import kryx07.expensereconcilerclient.model.transactions.Transaction;
import kryx07.expensereconcilerclient.model.users.Group;
import kryx07.expensereconcilerclient.model.users.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

   /* @GET("users/all")
    Call<Users> getUsers();*/

    @GET("users/{id}/transactions")
    Observable<List<Transaction>> getUsersTransactions(@Path("id") int id);

    @GET("users/")
    Observable<List<User>> getAllUsers();

    @GET("groups/byUserId/")
    Observable<List<Group>> getUsersGroups(@Query("userId") int userId);

    @GET("transactions/{id}")
    Observable<Transaction> getTransactionById(@Path("id") int transactionId);

    @POST("transactions")
    Observable<Transaction> addTransaction(@Body Transaction transaction);

    @PUT("transactions/{id}")
    Observable<Transaction> updateTransaction(@Path("id") int transactionId, @Body Transaction transaction);
    /*@GET("/reconciliation/payables-by-user")
    Call<Payables> getPayables(@Query("username") String username);*/
/*

    @GET("payables/all")
    Call<List<Transaction>> getPayables();
*/


  /*  @FormUrlEncoded
    @POST("Account/Login")
    Call<LoginResponse> login(@Field("Email") String email, @Field("Password") String password);
*/
   /* @GET("Activities/Get")
    Call<List<CallActivity>> getActivities();

    @GET("Clients/Get")
    Call<List<Client>> getClients();

    @PUT("Clients/Put")
    Call<Client> updateClient(@Body Client client);

    @POST("Clients/Post")
    Call<List<Client>> createClient(@Body List<Client> clients);

    @GET("Contacts/Get")
    Call<List<Contact>> getContacts();

    @GET("Contacts/Get/Property/{id}")
    Call<Contact> getContactByProperty(@Path("id") int value);
*/
}
