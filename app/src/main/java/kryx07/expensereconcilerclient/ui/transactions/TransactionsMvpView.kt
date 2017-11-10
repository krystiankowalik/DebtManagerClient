package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionsMvpView : MvpView {

    fun updateData(transactions: List<Transaction>)

    fun showToastAndLog(string:String)
    fun showToastAndLog(int:Int)


}