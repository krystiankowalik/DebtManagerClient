package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionDetailMvpView : MvpView {

    fun updateData(transactions: List<Transaction>)


}