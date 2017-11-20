package kryx07.expensereconcilerclient.ui.group

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_groups_adapter.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.users.Group
import timber.log.Timber

class GroupsAdapter(val onGroupClickListener: OnGroupClickListener) : RecyclerView.Adapter<GroupsAdapter.GroupsHolder>() {

    interface OnGroupClickListener {
        fun onGroupClick(group: Group)
    }

    var groups = mutableListOf<Group>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GroupsHolder {
        return GroupsHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_groups_adapter, parent, false),onGroupClickListener)
    }

    override fun onBindViewHolder(holder: GroupsHolder?, position: Int) {
        holder?.setupGroup(groups[position])
    }

    override fun getItemCount(): Int = groups.size

    class GroupsHolder(itemView: View, private val onGroupClickListener: OnGroupClickListener) : RecyclerView.ViewHolder(itemView) {

        fun setupGroup(group: Group) {
            Timber.e("onClick added?")
            itemView.setOnClickListener({ onGroupClickListener.onGroupClick(group) })
            itemView.group_name.text = group.name
        }

    }

    fun updateData(groups: List<Group>) {
        this.groups.clear()
        this.groups.addAll(groups)
        notifyDataSetChanged()
    }

}