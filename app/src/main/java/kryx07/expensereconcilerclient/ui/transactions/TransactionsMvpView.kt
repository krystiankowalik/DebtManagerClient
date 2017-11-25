package kryx07.expensereconcilerclient.ui.transactions

import android.app.Application
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

interface TransactionsMvpView : MvpView, TransactionsAdapter.OnTransactionClickListener {

    fun updateData(transactions: List<Transaction>)

    fun showSnackAndLog(string: String)

    fun showSnackAndLog(int: Int)

    fun onCreateActionMode()

    fun showFragment(fragment: Fragment) {
        EventBus.getDefault().post(ReplaceFragmentEvent(fragment))
    }

    fun onDeleteActionClicked()

    fun onDestroyActionMode()


}