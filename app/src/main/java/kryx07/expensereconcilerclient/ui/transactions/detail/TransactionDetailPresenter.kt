package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.events.UpdateTransactionDateEvent
import kryx07.expensereconcilerclient.events.UpdateTransactionGroupEvent
import kryx07.expensereconcilerclient.events.UpdateTransactionPayerEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.joda.time.LocalDate
import timber.log.Timber
import javax.inject.Inject


class TransactionDetailPresenter @Inject constructor(var apiClient: ApiClient,
                                                     var context: Context,
                                                     val sharedPrefs: SharedPreferencesManager,
                                                     val eventBus: EventBus

) : BasePresenter<TransactionDetailMvpView>() {


    private var transaction: Transaction = Transaction()

    override fun attachView(view: TransactionDetailMvpView) {
        super.attachView(view)
        eventBus.register(this)
    }

    override fun detach() {
        eventBus.unregister(this)
        super.detach()
    }

    fun start() {
        initTransaction(view.getTransactionDetailBundle())
    }

    private fun initTransaction(bundle: Bundle?) {
        if (transaction.isEmpty()) {
            val transaction = getTransactionFromBundle(bundle)
            if (transaction != null) {
                this.transaction = transaction
            } else {
                this.transaction.date = LocalDate.now()
            }
        }
        updateView()

    }

    private fun getTransactionFromBundle(bundle: Bundle?): Transaction? =
            bundle?.getParcelable(context.getString(R.string.clicked_transaction))

    fun getInitialTransactionDate(): LocalDate =
            this.transaction.date


    fun saveTransaction() {
        view.showProgress()

        if (this.transaction.id == 0) {
            addTransaction(this.transaction)
        } else {
            updateTransaction(this.transaction)
        }

    }

    private fun addTransaction(transaction: Transaction) {
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

    private fun updateTransaction(transaction: Transaction) {
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

    @Subscribe
    fun onUpdatePayerEvent(updateTransactionPayerEvent: UpdateTransactionPayerEvent) {
        transaction.payer = updateTransactionPayerEvent.payer
    }

    @Subscribe
    fun onUpdateGroupEvent(updateTransactionGroupEvent: UpdateTransactionGroupEvent) {
        transaction.group = updateTransactionGroupEvent.group
    }

    @Subscribe
    fun onDateUpdateEvent(updateTransactionDateEvent: UpdateTransactionDateEvent) {
        transaction.date = updateTransactionDateEvent.date
        view.updateDateView(StringUtilities.formatDate(transaction.date))
    }

    fun toggleCommonInput() {
        transaction.common = !transaction.common
        view.updateCommonView(transaction.common)
    }

    private fun updateView() {

        view.updateDateView(StringUtilities.formatDate(transaction.date))
        view.updateAmountView(transaction.amount.toString())
        view.updateDescriptionView(transaction.description)
        view.updatePayerView(transaction.payer.username)
        view.updateGroupView(transaction.group.name)
        view.updateCommonView(transaction.common)

    }


}
