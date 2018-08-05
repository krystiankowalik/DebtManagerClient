package kryx07.expensereconcilerclient.ui.users

import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_user.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.users.User

class UsersAdapter(private val onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<UsersAdapter.UsersHolder>() {

    //var users = (mutableListOf<User>())

    interface OnUserClickListener {
        fun onUserClick(user:User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        return UsersAdapter.UsersHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false),onUserClickListener)
    }

    override fun getItemCount(): Int = users.size()

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        holder.setupItem(users[position])
    }

    class UsersHolder(itemView: View, private val listener: OnUserClickListener) : RecyclerView.ViewHolder(itemView) {
        fun setupItem(user: User) {
            itemView.setOnClickListener { listener.onUserClick(user) }
            itemView.username.text = user.username
        }

    }

    fun updateData(users: List<User>) {

        this.users.clear()
        this.users.addAll(users)

        notifyDataSetChanged()
    }


    var users = SortedList<User>(User::class.java, object : SortedList.Callback<User>() {
        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun compare(o1: User?, o2: User?): Int {
            if (o1 != null && o2 != null) {
                return Integer.compare(o1.id.toInt(), o2.id.toInt())
            }
            return 0
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count)
        }

        override fun areItemsTheSame(item1: User?, item2: User?): Boolean {
            return item1 == item2
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })

}