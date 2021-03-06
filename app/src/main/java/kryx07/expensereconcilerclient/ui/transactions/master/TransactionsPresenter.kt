package kryx07.expensereconcilerclient.ui.transactions.master

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(private var apiClient: ApiClient,
                                                private var context: Context,
                                                private val sharedPrefs: SharedPreferencesManager,
                                                private val eventBus: EventBus
) : BasePresenter<TransactionsMvpView>() {

    private val disposable = CompositeDisposable()

    fun start() {
        requestTransactions()
    }

    fun requestTransactions() {
        view.showProgress()

        Timber.d(sharedPrefs.read(context.getString(R.string.my_user)))

        val transactionSubscription = apiClient.service
                .getUsersTransactions(sharedPrefs.read(context.getString(R.string.my_user)).toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ transactions ->
                    updateData(transactions)
                }, { error ->
                    handleFailedApiRequest(error)
                    view.hideProgress()
                    Timber.e(error.message)
                }, {
                    view.hideProgress()
                }
                )
        disposable.add(transactionSubscription)
    }


    private fun handleFailedApiRequest(error: Throwable) {
        view.hideProgress()
        Timber.e(error.message)
        view.showSnackAndLog(R.string.fetching_error)
    }

    private fun updateData(transactions: List<Transaction>) {
        view.updateData(transactions)
    }

    fun removeTransactions(transactions: List<Transaction>) {
        Timber.e("Presenter is removing transaction from the server...")
        Timber.e("Transactions to remove: " + transactions)
    }

    override fun detach() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
        super.detach()
    }

    fun handleTransactionClick(transaction: Transaction) {
        view.showProgress()
        Observable
                .just(transaction)
                .subscribeOn(Schedulers.computation())
                .subscribe({
                    eventBus.post(ReplaceFragmentEvent(TransactionDetailFragment()
                            .passParcelableInBundle(
                                    context.getString(R.string.clicked_transaction), transaction)
                    ))
                })
    }

    private fun Fragment.passParcelableInBundle(key: String, value: Parcelable): Fragment {
        val bundle = Bundle()
        bundle.putParcelable(key, value)
        this@passParcelableInBundle.arguments = bundle
        return this
    }
}