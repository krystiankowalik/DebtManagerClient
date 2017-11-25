package kryx07.expensereconcilerclient

import android.app.Application

import kryx07.expensereconcilerclient.di.AppComponent
import kryx07.expensereconcilerclient.di.AppModule
import kryx07.expensereconcilerclient.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {


    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
