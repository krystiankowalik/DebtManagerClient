package kryx07.expensereconcilerclient.di

import android.content.Context
import com.google.gson.*
import dagger.Provides
import io.objectbox.BoxStore
import kryx07.expensereconcilerclient.model.users.MyObjectBox
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.network.AuthApiClient
import kryx07.expensereconcilerclient.network.TokenAuthInterceptor
import kryx07.expensereconcilerclient.network.TransactionApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import net.sourceforge.jeval.Evaluator
import org.greenrobot.eventbus.EventBus
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import javax.inject.Singleton
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import org.joda.time.chrono.ZonedChronology
import java.time.ZonedDateTime


@dagger.Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context = context


    @Provides
    @Singleton
    fun sharedPreferencesManager(context: Context): SharedPreferencesManager =
            SharedPreferencesManager(context)

    @Provides
    @Singleton
    fun providesTokenAuthInterceptor(context: Context) = TokenAuthInterceptor(context)

    @Provides
    @Singleton
    fun providesApiClient(context: Context, sharedPreferencesManager: SharedPreferencesManager, gson: Gson): ApiClient =
            ApiClient(context)

    @Provides
    @Singleton
    fun providesBoxStore(context: Context): BoxStore =
            MyObjectBox.builder().androidContext(context).build()


    @Provides
    @Singleton
    fun providesEventBus(): EventBus = EventBus.getDefault()

    @Provides
    @Singleton
    fun providesGson(): Gson {

/*
        return GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ -> LocalDate(json.asString) })
                .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                    val `object` = JsonObject()
                    `object`.addProperty("date", src.toString("yyyy-MM-dd"))
                    `object`.getAsJsonPrimitive("date")
                })
                .registerTypeAdapter(LocalDateTime::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                    val `object` = JsonObject()
                    `object`.addProperty("added", src.toString("yyyy-MM-dd"))
                    `object`.getAsJsonPrimitive("date")
                })
                .create()
*/

//        val gson = Converters.registerDateTime(GsonBuilder())
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, typeOfT, context -> LocalDate(json.asString) })
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, typeOfT, context -> LocalDateTime(json.asString) })

        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun providesJEvalEvaluator() = Evaluator()

    @Provides
    @Singleton
    fun providesAuthApiClient(): AuthApiClient = AuthApiClient()

    @Provides
    @Singleton
    fun providesTransactionApiClient(tokenAuthInterceptor: TokenAuthInterceptor, gson: Gson): TransactionApiClient = TransactionApiClient(tokenAuthInterceptor, gson)


}
