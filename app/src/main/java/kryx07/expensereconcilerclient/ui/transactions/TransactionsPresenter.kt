package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.ViewUtilities
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
        ViewUtilities.showProgress()

        Timber.d(sharedPrefs.read(context.getString(R.string.my_user)))

        apiClient.service
                .getUsersTransactions(sharedPrefs.read(context.getString(R.string.my_user)).toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ transactions ->
                    handleSuccessfulApiRequest(transactions)
                }, { error ->
                    handleFailedApiRequest(error)
                    ViewUtilities.hideProgress()
                    Timber.e(error.message)
                }
                )
    }

    private fun handleSuccessfulApiRequest(transactions: List<Transaction>) {
        updateData(transactions)
    }

    private fun handleFailedApiRequest(error: Throwable) {
        ViewUtilities.hideProgress()
        Timber.e(error.message)
        view.showToastAndLog(R.string.fetching_error)
    }

    private fun updateData(transactions: List<Transaction>) {
        view.updateData(transactions)
        ViewUtilities.hideProgress()
    }


}