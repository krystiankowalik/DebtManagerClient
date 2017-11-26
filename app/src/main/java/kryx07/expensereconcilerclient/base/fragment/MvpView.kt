package kryx07.expensereconcilerclient.base.fragment

import android.support.design.widget.Snackbar
import android.view.View
import android.view.ViewParent
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.HideRefresherEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import org.greenrobot.eventbus.EventBus

interface MvpView {
    fun showProgress() {
        EventBus.getDefault().postSticky(ShowProgressEvent())
    }

    fun hideProgress() {
        EventBus.getDefault().post(HideProgressEvent())
    }


}
