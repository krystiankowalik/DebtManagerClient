package kryx07.expensereconcilerclient.ui.users

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.events.UpdateTransactionPayerEvent
import kryx07.expensereconcilerclient.model.users.User
import kryx07.expensereconcilerclient.network.ApiClient
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class UserSearchPresenter @Inject constructor(private val eventBus: EventBus,
                                              private val apiClient: ApiClient
                                              ) : BasePresenter<UserSearchMvpView>() {

    private var usersList = arrayListOf<User>()


    fun start() {
        requestUsers()
    }

    private fun requestUsers() {

        view.showProgress()

        apiClient.service.allUsers
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                    usersList.addAll(users)
                    view.updateData(users)
                })
    }


    fun filter(query: String) {
        view.showProgress()
        view.updateData(getUsersFilteredByName(usersList, query))
    }

    private fun getUsersFilteredByName(users: List<User>, query: String): List<User> {
        val lowerCaseQuery = query.toLowerCase()
        val filteredUsers = arrayListOf<User>()

        users.forEach { u ->
            if (u.username.contains(lowerCaseQuery)) {
                filteredUsers.add(u)
            }
        }
        return filteredUsers
    }

    fun handleUserClick(user: User) {
        eventBus.postSticky(UpdateTransactionPayerEvent(user))
        view.popBackStack()
    }
}