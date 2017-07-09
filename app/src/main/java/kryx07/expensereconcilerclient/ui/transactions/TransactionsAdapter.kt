package kryx07.expensereconcilerclient.ui.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_transactions_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.model.transactions.Transactions
import timber.log.Timber

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {

    var transactions = Transactions(mutableListOf<Transaction>())


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionsHolder {
        Timber.plant(Timber.DebugTree())
        Timber.e("onCreateViewHolder")
        return TransactionsHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_transactions_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: TransactionsHolder?, position: Int) {
        Timber.e("onBindViewHolder")
        holder?.setupTransaction(transactions.transactions[position])
    }

    override fun getItemCount(): Int = transactions.transactions.size

    class TransactionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setupTransaction(transaction: Transaction) {
            Timber.e("setupTransaction")

            itemView.date_text.text = transaction.date.toString()
            itemView.description_text.text = transaction.description.toString()
            itemView.amount.text = "%.2f".format(transaction.amount) + " " + itemView.context.getString(R.string.currency)
        }

    }

    fun updateData(transactions: Transactions) {
        Timber.e("update Data: " + transactions)

        this.transactions.clear()
        this.transactions.addAll(transactions.transactions)
        notifyDataSetChanged()
    }
}