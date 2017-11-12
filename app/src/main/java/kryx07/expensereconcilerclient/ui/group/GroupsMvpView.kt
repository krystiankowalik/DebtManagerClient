package kryx07.expensereconcilerclient.ui.group

import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.users.Group

interface GroupsMvpView : MvpView {

    fun updateData(groups:List<Group>)

}