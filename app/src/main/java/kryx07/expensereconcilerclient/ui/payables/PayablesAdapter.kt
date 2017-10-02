package kryx07.expensereconcilerclient.ui.payables

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_payables_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.utils.StringUtilities
import timber.log.Timber

class PayablesAdapter(var currentUserName: String) : RecyclerView.Adapter<PayablesAdapter.PayablesHolder>() {

    var payables = (mutableListOf<Payable>())

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PayablesHolder {
        Timber.e("onCreateViewHolder")
        return PayablesHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_payables_adapter, parent, false), currentUserName)
    }

    override fun onBindViewHolder(holder: PayablesHolder?, position: Int) {
        Timber.e("onBindViewHolder")
        holder?.setupItem(payables[position])
    }

    override fun getItemCount(): Int = payables.size

    class PayablesHolder(itemView: View, val currentUserId: String) : RecyclerView.ViewHolder(itemView) {

        fun setupItem(payable: Payable) {
            Timber.e("setupTransaction")
            //var currentUser = Users(HashSet<User>()).getByUserName(currentUserName)

            //itemView.amount.text = "%.2f".format(payable.amount) + " " + itemView.context.getString(R.string.currency)
            itemView.amount.text = StringUtilities.formatCurrency(payable.amount, itemView.context.getString(R.string.currency))
            if (payable.debtor.userName.toString() == currentUserId) {
                itemView.type.text = itemView.context.getString(R.string.payable)
                itemView.direction.text = " -> "
                itemView.another_person.text = payable.payer.userName
                itemView.amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))

            } else if (payable.payer.userName.toString() == currentUserId) {
                itemView.type.text = itemView.context.getString(R.string.receivable)
                itemView.direction.text = " <- "
                itemView.another_person.text = payable.debtor.userName
                itemView.amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }

        }

    }

    fun updateData(payables: List<Payable>) {
        Timber.e("update Data: " + payables)

        this.payables.clear()
        this.payables.addAll(payables)
        notifyDataSetChanged()
    }
}