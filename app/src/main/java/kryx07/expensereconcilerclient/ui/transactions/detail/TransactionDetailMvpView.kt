package kryx07.expensereconcilerclient.ui.transactions.detail

import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionDetailMvpView : MvpView {
    fun updateView(transaction: Transaction)
    fun popBackStack()
}