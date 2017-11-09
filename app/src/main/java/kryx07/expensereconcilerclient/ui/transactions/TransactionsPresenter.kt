package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(private var apiClient: ApiClient,
                                                var context: Context,
                                                private val sharedPrefs: SharedPreferencesManager
                                                ) : BasePresenter<TransactionsMvpView>() {

    fun start() {
        requestTransactions()
    }

    fun requestTransactions() {
        showProgress()

        Timber.d(sharedPrefs.read(context.getString(R.string.my_user)))

        apiClient.service
                .getUsersTransactions(sharedPrefs.read(context.getString(R.string.my_user)).toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ transactions ->
                    handleSuccessFullApiRequest(transactions)
                }, { error ->
                    handleFailedApiRequest(error)
                    hideProgress()
                    Timber.e(error.message)
                }
                )
    }

    private fun handleSuccessFullApiRequest(transactions: List<Transaction>) {
        // insertTransactionsToDb(transactions)

        // readTransactionsFromDb()

        updateData(transactions)
    }

/*
    private fun readTransactionsFromDb() {
        database.transactionDao().getAll()
                .subscribeOn(Schedulers.io())
                // .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ transactionsFromDb ->
                    Timber.e("Read from DB: ")
                    transactionsFromDb.forEach({ t -> Timber.e(t.toString()) })
                }, { error -> Timber.e("some error") })
    }
*/

    /*  private fun insertTransactionsToDb(transactions: List<Transaction>) {
          Observable.fromCallable {
              database.transactionDao()
                      .insert(transactions)
          }
                  .subscribeOn(Schedulers.computation())
                  .subscribe()
      }*/

    private fun handleFailedApiRequest(error: Throwable) {
        hideProgress()
        Timber.e(error.message)
    }

    private fun updateData(transactions: List<Transaction>) {
        hideProgress()
        view.updateData(transactions)
    }

    private fun showProgress() {
        EventBus.getDefault().post(ShowProgress())

    }

    private fun hideProgress() {
        EventBus.getDefault().post(HideProgress())
        EventBus.getDefault().post(HideRefresher())
    }

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }

}