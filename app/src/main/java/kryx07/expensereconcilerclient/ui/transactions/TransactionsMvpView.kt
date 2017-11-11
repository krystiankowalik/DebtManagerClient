package kryx07.expensereconcilerclient.ui.transactions

import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.transactions.Transaction

interface TransactionsMvpView : MvpView {

    fun updateData(transactions: List<Transaction>)

    fun showSnackAndLog(string:String)
    fun showSnackAndLog(int:Int)


}