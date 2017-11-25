package kryx07.expensereconcilerclient.ui.transactions

import android.view.View
import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionsMvpView : MvpView, TransactionsAdapter.OnTransactionClickListener {

    fun updateData(transactions: List<Transaction>)

    fun showSnackAndLog(string: String)
    fun showSnackAndLog(int: Int)


}