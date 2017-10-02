package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionsMvpView : MvpView {

    fun updateData(transactions: List<Transaction>)


}