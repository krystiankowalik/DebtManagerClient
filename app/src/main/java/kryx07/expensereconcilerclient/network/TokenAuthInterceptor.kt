package kryx07.expensereconcilerclient.network

import android.content.Context
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenAuthInterceptor(val context: Context) : Interceptor {

    private val sharedPreferencesManager = SharedPreferencesManager(context);

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val authenticatedRequest = request.newBuilder()
                .header("Authorization", "Bearer " + sharedPreferencesManager.read(context.getString(R.string.access_token))).build()
        return chain.proceed(authenticatedRequest)
    }

}