package kryx07.expensereconcilerclient.base.presenter

import kryx07.expensereconcilerclient.base.fragment.MvpView
import java.lang.ref.WeakReference

abstract class BasePresenter<T : MvpView> : MvpPresenter<T> {

    var reference: WeakReference<T>? = null

    override fun attachView(view: T) {
        this.reference = WeakReference(view)
    }

    override fun detach() {
        if (reference != null) {
            this.reference!!.clear()
            this.reference = null
        }
    }

    override val view: T
        get() = reference!!.get()!!

    override val isViewAttached: Boolean
        get() = reference != null && reference!!.get() != null


}
