package kryx07.expensereconcilerclient.ui.transactions.detail.calculator

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.Component
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import org.greenrobot.eventbus.EventBus

interface CalculatorDialogMvpView : MvpView {

    fun showResult(string: String)


}