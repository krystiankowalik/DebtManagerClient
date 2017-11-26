package kryx07.expensereconcilerclient.di

import android.content.Context
import com.google.gson.*
import dagger.Provides
import io.objectbox.BoxStore
import kryx07.expensereconcilerclient.model.users.MyObjectBox
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.joda.time.LocalDate
import javax.inject.Singleton

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
    fun providesApiClient(sharedPreferencesManager: SharedPreferencesManager, gson: Gson): ApiClient =
            ApiClient(sharedPreferencesManager, gson)

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
        return GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ -> LocalDate(json.asString) })
                .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                    val `object` = JsonObject()
                    `object`.addProperty("date", src.toString("yyyy-MM-dd"))
                    `object`.getAsJsonPrimitive("date")
                })
                .create()
    }

}
