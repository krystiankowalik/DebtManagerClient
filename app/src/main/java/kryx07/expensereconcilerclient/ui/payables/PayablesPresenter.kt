/*
package kryx07.expensereconcilerclient.ui.payables

import android.content.Context
import android.widget.Toast
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.PayablesMvpView
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class PayablesPresenter @Inject constructor(var apiClient: ApiClient,
                                            var context: Context,
                                            val sharedprefs: SharedPreferencesManager,
                                            val database: MyDatabase) : BasePresenter<PayablesMvpView>() {

    fun start() {
        requestPayables()
    }

    fun requestPayables() {
        showProgress()

        */
/*apiClient.service.getPayables(sharedprefs.read(context.getString(R.string.my_user))).enqueue(object : Callback<List<Payable>> {

            override fun onResponse(call: Call<List<Payable>>?, response: Response<List<Payable>>?) {
                if (response!!.isSuccessful) {
                    Timber.e(response.body().toString())
                    val payables = response.body()
                    view.updateData(payables)
                    setTotals(payables)
                    payables.payables.forEach { p ->
                        database.payablesDao().insert(p)
                    }
                    Timber.e("Read from db: " + database.payablesDao().getAll().toString())
                }
                hideProgress()
            }

            override fun onFailure(call: Call<Payables>?, t: Throwable?) {
                showErrorMessage()
                hideProgress()
            }

        })*//*

    }

    private fun showProgress() {
        EventBus.getDefault().post(ShowProgress())
    }

    private fun hideProgress() {
        EventBus.getDefault().post(HideProgress())
        EventBus.getDefault().post(HideRefresher())
    }

    private fun setTotals(allPayables: List<Payable>) {
        var myPayables = BigDecimal(0)
        var myReceivables = BigDecimal(0)
        val myUserName = sharedprefs.read(context.getString(R.string.my_user))

        */
/*allPayables.payables.forEach { (id, debtor, payer, amount) ->
            if (debtor.username == myUserName) {
                myReceivables = myReceivables.add(amount)
            } else if (payer.username == myUserName) {
                myPayables = myPayables.add(amount)
            }
        }*//*


        view.updateTotals(myReceivables, myPayables)
    }

    private fun showErrorMessage() {
        Timber.e(context.getString(R.string.fetching_error))
        Toast.makeText(context, context.getString(R.string.fetching_error), Toast.LENGTH_LONG).show()
    }

}*/
