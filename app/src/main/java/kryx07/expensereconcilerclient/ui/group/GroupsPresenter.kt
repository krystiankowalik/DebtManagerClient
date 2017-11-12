package kryx07.expensereconcilerclient.ui.group

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import javax.inject.Inject

class GroupsPresenter @Inject constructor(private var apiClient: ApiClient,
                                          var context: Context,
                                          private val sharedPrefs: SharedPreferencesManager
) : BasePresenter<GroupsMvpView>() {
    fun start() {
        view.showProgress()
        requestUsersGroups()
    }

    fun requestUsersGroups() {
        apiClient
                .service
                .getUsersGroups(Integer.parseInt(sharedPrefs.read(context.getString(R.string.my_user))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { groups ->
                    view.updateData(groups)
                    view.hideProgress()
                }
    }
}