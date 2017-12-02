package kryx07.expensereconcilerclient.ui.transactions.detail.calculator

import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_transaction_amount_calculator.view.*
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.presenter.BasePresenter
import kryx07.expensereconcilerclient.events.UpdateTransactionAmountEvent
import kryx07.expensereconcilerclient.network.ApiClient
import net.sourceforge.jeval.Evaluator
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject


class CalculatorDialogPresenter @Inject constructor(private val apiClient: ApiClient,
                                                    private val context: Context,
                                                    private val eventBus: EventBus,
                                                    private val evaluator: Evaluator


) : BasePresenter<CalculatorDialogMvpView>() {


    fun start() {
    }

    fun onEqualsClick(stringFromView: String?) {
        val evaluationResult: String = if (stringFromView.isNullOrEmpty()) {
            "0"
        } else {
            evaluator.evaluate(stringFromView)
        }
        view.showResult(evaluationResult)
        EventBus.getDefault().postSticky(UpdateTransactionAmountEvent(evaluationResult))
    }

    fun handleReceivedBundle(arguments: Bundle?) {
        if (arguments != null) {
            view.showResult(
                    arguments
                            .getString(
                                    context.getString(
                                            R.string.amount_from_detail_transaction_screen)))
        }

    }


}
