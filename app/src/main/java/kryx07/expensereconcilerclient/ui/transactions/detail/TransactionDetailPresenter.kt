package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.joda.time.DateTime
import org.joda.time.chrono.GregorianChronology
import timber.log.Timber
import javax.inject.Inject


class TransactionDetailPresenter @Inject constructor(var apiClient: ApiClient,
                                                     var context: Context,
                                                     val sharedPrefs: SharedPreferencesManager) : BasePresenter<TransactionDetailMvpView>() {


    fun start() {

    }

    fun requestTransaction(id: Int) {
        view.showProgress()

        Timber.d(sharedPrefs.read(context.getString(R.string.my_user)))

        apiClient.service
                .getTransactionById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ transactions ->
                    handleSuccessfulApiRequest(transactions)
                }
                )
    }

    private fun handleSuccessfulApiRequest(transaction: Transaction) {
        view.updateView(transaction)
        view.hideProgress()
    }

    fun parseGregorianDate(string: String): DateTime {
        val date = try {
            DateTime.parse(string)
        } catch (e: IllegalArgumentException) {
            DateTime.now()
        }
        return date.withMonthOfYear(date.monthOfYear)
    }

    fun getDateOf(year: Int, month: Int, day: Int) =
            DateTime(GregorianChronology
                    .getInstance())
                    .withDate(year, month + 1, day)

    fun saveTransaction(transaction: Transaction) {
        view.showProgress()

        if (transaction.id == 0) {
            addTransaction(transaction)
        } else {
            updateTransaction(transaction)
        }

    }

    fun addTransaction(transaction: Transaction) {
        apiClient.service
                .addTransaction(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ receivedTransaction ->
                 //   transactionsView.updateView(receivedTransaction)
                    Timber.e("Received after adding new transaction: " + receivedTransaction)
                    view.popBackStack()
                })
    }

    fun updateTransaction(transaction: Transaction) {
        apiClient.service
                .updateTransaction(transaction.id, transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ receivedTransaction ->
                //    transactionsView.updateView(receivedTransaction)
                    Timber.e("Received after update: " + receivedTransaction)
                    view.popBackStack()
                })
    }


}
