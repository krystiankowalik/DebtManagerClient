package kryx07.expensereconcilerclient.ui.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_transactions_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus

class TransactionsAdapter() : RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {

    var transactions = (mutableListOf<Transaction>())

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionsHolder {
        return TransactionsHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_transactions_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: TransactionsHolder?, position: Int) {
        holder?.setupTransaction(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setupTransaction(transaction: Transaction) {
            itemView.setOnClickListener({
                TransactionDetailFragment.id = transaction.id
                EventBus.getDefault().post(ReplaceFragmentEvent(TransactionDetailFragment()))
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