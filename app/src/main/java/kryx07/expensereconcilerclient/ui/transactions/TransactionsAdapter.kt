package kryx07.expensereconcilerclient.ui.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_card_transactions_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.adapter.SelectableAdapter
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.utils.StringUtilities
import java.util.*

class TransactionsAdapter(private val transactionsView: TransactionsMvpView) : SelectableAdapter<TransactionsAdapter.TransactionsHolder>() /*RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>()*/ {

    var transactions = (mutableListOf<Transaction>())

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionsHolder {
        return TransactionsHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_card_transactions_adapter, parent, false), transactionsView)
    }


    override fun onBindViewHolder(holder: TransactionsHolder?, position: Int) {
        holder?.setupTransaction(transactions[position], position)
        if (isSelected(position)) {
            holder?.itemView?.transaction_item_background?.visibility = View.VISIBLE
        } else {
            holder?.itemView?.transaction_item_background?.visibility = View.INVISIBLE

        }
    }

    override fun getItemCount(): Int = transactions.size

    interface OnTransactionClickListener {
        fun onTransactionClick(position: Int)
        fun onTransactionLongClick(position: Int): Boolean
    }

    fun removeItem(position: Int) {
        transactions.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getTransactionsFromPositions(positions: List<Int>): List<Transaction> {
        val requestedTransactions = mutableListOf<Transaction>()
        positions.forEach({ pos -> requestedTransactions.add(transactions[pos]) })
        return requestedTransactions
    }

    fun removeItems(positions: MutableList<Int>) {
        // Reverse-sort the list
        Collections.sort(positions) { lhs, rhs -> rhs!! - lhs!! }

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size == 1) {
                removeItem(positions[0])
                positions.removeAt(0)
            } else {
                var count = 1
                while (positions.size > count && positions[count] == positions[count - 1] - 1) {
                    ++count
                }

                if (count == 1) {
                    removeItem(positions[0])
                } else {
                    removeRange(positions[count - 1], count)
                }

                for (i in 0 until count) {
                    positions.removeAt(0)
                }
            }
        }
    }

    private fun removeRange(positionStart: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            transactions.removeAt(positionStart)
        }
        notifyItemRangeRemoved(positionStart, itemCount)
    }


    class TransactionsHolder(itemView: View, private val transactionsView: TransactionsMvpView) : RecyclerView.ViewHolder(itemView) {


        fun setupTransaction(transaction: Transaction, position: Int) {
            itemView.setOnClickListener { transactionsView.onTransactionClick(position) }
            itemView.isLongClickable = true
            itemView.setOnLongClickListener { transactionsView.onTransactionLongClick(position) }

            itemView.material_initials_text.text = transaction.payer.username.toUpperCase().substring(0, 2)
            itemView.date_text.text = transaction.date.toString()
            itemView.description_text.text = transaction.description
            itemView.amount.text = StringUtilities.formatCurrency(transaction.amount, itemView.context.getString(R.string.currency))
            itemView.group_text.text = transaction.group.name

        }

    }

    fun updateData(transactions: List<Transaction>) {

        this.transactions.clear()
        this.transactions.addAll(transactions)
        notifyDataSetChanged()
    }
}