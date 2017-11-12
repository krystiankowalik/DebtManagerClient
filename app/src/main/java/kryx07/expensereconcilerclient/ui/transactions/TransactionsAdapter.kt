package kryx07.expensereconcilerclient.ui.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_transactions_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.utils.StringUtilities

class TransactionsAdapter(private val transactionClickListener: TransactionClickListener) : RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {

    interface TransactionClickListener {
        fun onTransactionClick(transaction: Transaction)
    }

    var transactions = (mutableListOf<Transaction>())

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionsHolder {
        return TransactionsHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_transactions_adapter, parent, false), transactionClickListener)
    }

    override fun onBindViewHolder(holder: TransactionsHolder?, position: Int) {
        holder?.setupTransaction(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionsHolder(itemView: View, private val transactionClickListener: TransactionClickListener) : RecyclerView.ViewHolder(itemView) {

        fun setupTransaction(transaction: Transaction) {
            itemView.setOnClickListener({
                transactionClickListener.onTransactionClick(transaction)
            })

            itemView.date_text.text = transaction.date.toString()
            itemView.description_text.text = transaction.description.toString()
            itemView.amount.text = StringUtilities.formatCurrency(transaction.amount, itemView.context.getString(R.string.currency))

        }

    }

    fun updateData(transactions: List<Transaction>) {

        this.transactions.clear()
        this.transactions.addAll(transactions)
        notifyDataSetChanged()
    }
}