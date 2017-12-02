package kryx07.expensereconcilerclient.ui.transactions.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.Component
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import org.greenrobot.eventbus.EventBus

interface TransactionDetailMvpView : MvpView {
    fun popBackStack()

    fun showFragment(fragment: Fragment) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment))
    fun showFragment(fragment: Fragment, bundle: Bundle) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment, bundle))
    fun showFragment(fragment: Fragment, customTag: String) = EventBus.getDefault().post(ReplaceFragmentEvent(fragment, customTag))
    fun getTransactionDetailBundle(): Bundle?

    fun updateDateView(date: String)
    fun updateAmountView(amount: String)
    fun updateDescriptionView(description: String)
    fun updatePayerView(payer: String)
    fun updateGroupView(group: String)
    fun updateCommonView(common: Boolean)
    fun onDescriptionChanged(description: String)
    fun onAmountChanged(amount: String)

    fun hideSoftInput()
}