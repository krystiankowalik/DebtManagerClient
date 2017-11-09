package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import android.widget.Toast
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.TransactionDetailMvpView
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject



class TransactionDetailPresenter @Inject constructor(var apiClient: ApiClient,
                                                     var context: Context,
                                                     val sharedPrefs: SharedPreferencesManager) : BasePresenter<TransactionDetailMvpView>() {

    fun start() {

    }

    private fun showProgress() {
        EventBus.getDefault().post(ShowProgress())

    }

    private fun hideProgress() {
        EventBus.getDefault().post(HideProgress())
        EventBus.getDefault().post(HideRefresher())
    }

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }


}
