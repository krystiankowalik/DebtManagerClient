package kryx07.expensereconcilerclient.di

import android.content.Context
import dagger.Provides
import io.objectbox.BoxStore
import kryx07.expensereconcilerclient.model.users.MyObjectBox
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
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
    fun providesApiClient(sharedPreferencesManager: SharedPreferencesManager): ApiClient =
            ApiClient(sharedPreferencesManager)

    @Provides
    @Singleton
    fun providesBoxStore(context: Context): BoxStore =
            MyObjectBox.builder().androidContext(context).build()


    @Provides
    @Singleton
    fun providesEventBus(): EventBus = EventBus.getDefault()

/*
    @Provides @Singleton
    fun providesTransactionsPresenter(apiClient: ApiClient, context: Context) = TransactionsPresenter(apiClient, context)
*/

}
