package kryx07.expensereconcilerclient.base.fragment

import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import org.greenrobot.eventbus.EventBus

interface MvpView : Snackable {
    fun showProgress() {
        EventBus.getDefault().postSticky(ShowProgressEvent())
    }

    fun hideProgress() {
        EventBus.getDefault().post(HideProgressEvent())
    }


}
