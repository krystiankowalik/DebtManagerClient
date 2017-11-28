package kryx07.expensereconcilerclient.ui.transactions.detail.calculator

import android.content.Context
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.events.UpdateTransactionDateEvent
import kryx07.expensereconcilerclient.events.UpdateTransactionGroupEvent
import kryx07.expensereconcilerclient.events.UpdateTransactionPayerEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailMvpView
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.joda.time.LocalDate
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class CalculatorDialogPresenter @Inject constructor(private var apiClient: ApiClient,
                                                    private var context: Context,
                                                    private val eventBus: EventBus

) : BasePresenter<CalculatorDialogMvpView>() {


    private var transaction: Transaction = Transaction()



    fun start() {
    }




}
