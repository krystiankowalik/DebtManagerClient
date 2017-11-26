package kryx07.expensereconcilerclient.ui.transactions

import android.support.v4.app.Fragment
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.events.HideRefresherEvent
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import org.greenrobot.eventbus.EventBus

interface TransactionsMvpView : MvpView, TransactionsAdapter.OnTransactionClickListener {

    fun updateData(transactions: List<Transaction>)

    fun showSnackAndLog(string: String)

    fun showSnackAndLog(int: Int)

    fun onCreateActionMode()

    fun showFragment(fragment: Fragment) {
        EventBus.getDefault().postSticky(ReplaceFragmentEvent(fragment))
    }

    fun onDeleteActionClicked()

    fun onDestroyActionMode()

    override fun hideProgress() {
        super.hideProgress()
        EventBus.getDefault().postSticky(HideRefresherEvent())
    }
}