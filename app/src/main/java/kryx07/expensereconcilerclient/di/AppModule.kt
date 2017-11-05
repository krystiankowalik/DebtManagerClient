package kryx07.expensereconcilerclient.di

import android.arch.persistence.room.Room
import android.content.Context

import javax.inject.Singleton

import dagger.Provides
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.TransactionsPresenter
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager

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

    @Provides @Singleton
    fun providesDb(context: Context): MyDatabase {
        return Room
                .databaseBuilder(context, MyDatabase::class.java, "we-need-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

/*
    @Provides @Singleton
    fun providesTransactionsPresenter(apiClient: ApiClient, context: Context) = TransactionsPresenter(apiClient, context)
*/

}
