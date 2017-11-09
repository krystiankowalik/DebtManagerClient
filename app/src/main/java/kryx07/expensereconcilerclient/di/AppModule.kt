package kryx07.expensereconcilerclient.di

import android.content.Context
import dagger.Provides
import io.objectbox.BoxStore
import kryx07.expensereconcilerclient.model.users.MyObjectBox
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import javax.inject.Singleton

@dagger.Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides @Singleton
    fun sharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }


    @Provides @Singleton
    fun providesApiClient(sharedPreferencesManager: SharedPreferencesManager): ApiClient {
        return ApiClient(sharedPreferencesManager)
    }

   /* @Provides @Singleton
    fun providesDb(context: Context): MyDatabase {
        return Room
                .databaseBuilder(context, MyDatabase::class.java, "we-need-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }*/

    @Provides @Singleton
    fun providesBoxStore(context: Context) : BoxStore {
        return MyObjectBox.builder().androidContext(context).build()
    }

/*
    @Provides @Singleton
    fun providesTransactionsPresenter(apiClient: ApiClient, context: Context) = TransactionsPresenter(apiClient, context)
*/

}
