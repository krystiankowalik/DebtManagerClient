package kryx07.expensereconcilerclient.base.presenter

import kryx07.expensereconcilerclient.base.fragment.MvpView

interface MvpPresenter<T : MvpView> {

    fun attachView(view: T)

    fun detach()

    val view: T

    val isViewAttached: Boolean
}