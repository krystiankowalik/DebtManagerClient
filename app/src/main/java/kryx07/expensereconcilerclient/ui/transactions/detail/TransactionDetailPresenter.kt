package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.TransactionDetailMvpView
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.joda.time.DateTime
import org.joda.time.chrono.GregorianChronology
import javax.inject.Inject


class TransactionDetailPresenter @Inject constructor(var apiClient: ApiClient,
                                                     var context: Context,
                                                     val sharedPrefs: SharedPreferencesManager) : BasePresenter<TransactionDetailMvpView>() {

    fun start() {

    }

    fun parseGregorianDate(string: String): DateTime {
        val date = try {
            DateTime.parse(string)
        } catch (e: IllegalArgumentException) {
            DateTime.now()
        }
        return date.withMonthOfYear(date.monthOfYear - 1)
    }

    fun getDateOf(year: Int, month: Int, day: Int) =
            DateTime(GregorianChronology
                    .getInstance())
                    .withDate(year, month + 1, day)


}
