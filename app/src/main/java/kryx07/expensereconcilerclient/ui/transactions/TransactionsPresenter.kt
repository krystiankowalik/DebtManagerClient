package kryx07.expensereconcilerclient.ui.transactions

import android.content.Context
import android.widget.Toast
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.BasePresenter
import kryx07.expensereconcilerclient.db.MyDatabase
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class TransactionsPresenter @Inject constructor(var apiClient: ApiClient,
                                                var context: Context,
                                                val sharedPrefs: SharedPreferencesManager,
                                                val database: MyDatabase) : BasePresenter<TransactionsMvpView>() {

    fun start() {
        requestTransactions()
    }

    fun requestTransactions() {
        showProgress()

        Timber.d(sharedPrefs.read(context.getString(R.string.my_user)))

        apiClient.service.getUsersTransactions(sharedPrefs.read(context.getString(R.string.my_user)).toInt())
                .enqueue(object : Callback<List<Transaction>> {
                    override fun onResponse(call: Call<List<Transaction>>?, response: Response<List<Transaction>>?) {
                        if (response!!.isSuccessful) {
                            Timber.e(response.body().toString())
                            val transactions = response.body()
                            //view?.updateData(transactions)

                            for (transaction in transactions) {
                                database.transactionDao().insert(transaction)
                            }
                            view.updateData(transactions)
                            Timber.e("Read from db: " + database.transactionDao().getAll().toString())

                        }
                        hideProgress()
                    }

                    override fun onFailure(call: Call<List<Transaction>>?, t: Throwable?) {
                        showErrorMessage()
                        hideProgress()
                    }
                })
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