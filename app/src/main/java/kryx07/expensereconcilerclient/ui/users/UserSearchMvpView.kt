package kryx07.expensereconcilerclient.ui.users

import kryx07.expensereconcilerclient.base.fragment.MvpView
import kryx07.expensereconcilerclient.model.users.User

interface UserSearchMvpView : MvpView {

    fun updateData(users: List<User>)

    fun popBackStack()
}