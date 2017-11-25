package kryx07.expensereconcilerclient.ui.transactions.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import org.greenrobot.eventbus.EventBus

interface TransactionDetailMvpView : MvpView {
    fun updateView(transaction: Transaction)
    fun popBackStack()

    fun showFragment(fragment: Fragment) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment))
    fun showFragment(fragment: Fragment, bundle: Bundle) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment, bundle))
    fun showFragment(fragment: Fragment, customTag: String) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment, customTag))
}