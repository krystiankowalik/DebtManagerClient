package kryx07.expensereconcilerclient.ui.transactions.detail.calculator

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_transaction_amount_calculator.*
import kotlinx.android.synthetic.main.fragment_transaction_amount_calculator.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import javax.inject.Inject


class CalculatorDialogFragment() : BottomSheetDialogFragment(), CalculatorDialogMvpView {

    @Inject lateinit var presenter: CalculatorDialogPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_amount_calculator, container, false)
        App.appComponent.inject(this)

        return view

    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        setRegularButtonsListeners(view as ViewGroup)

        view.button_clear.setOnClickListener({ clearTextView(view.calculator_screen) })
        view.button_backspace.setOnClickListener({ backspaceTextView(view.calculator_screen) })
        view.button_equals.setOnClickListener({ handleEqualsClick() })
        view.button_empty_equals.setOnClickListener({ handleEqualsClick() })

        presenter.handleReceivedBundle(arguments)
        //getAmountFromBundle(view)
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun getAmountFromBundle(view: View) {
        if (arguments != null) {
            view.calculator_screen.text =
                    arguments
                            .getString(getString(R.string.amount_from_detail_transaction_screen))
        }

    }

    private fun setRegularButtonsListeners(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            //  Timber.e(viewGroup.getChildAt(i).toString())
            val child = viewGroup.getChildAt(i)
            if (child is ViewGroup) {
                for (j in 0 until child.childCount) {
                    setRegularButtonListenerByTag(child.getChildAt(j))
                }
            }
            setRegularButtonListenerByTag(viewGroup)
        }

    }

    private fun setRegularButtonListenerByTag(viewWidget: View?) {
        if (viewWidget?.tag != null && viewWidget.tag != "") {
            viewWidget.setOnClickListener({
                writeNumber(viewWidget.tag.toString())
            })
        }
    }

    private fun writeNumber(string: String) {
        calculator_screen.text = calculator_screen.text.toString() + string
    }

    private fun clearTextView(textView: TextView) {
        textView.text = ""
    }

    private fun backspaceTextView(textView: TextView) {
        val text = textView.text
        if (text.count() > 0) {
            textView.text = text.substring(0, text.count() - 1)
        }
    }

    private fun handleEqualsClick() {
        val stringFromView: String? = view?.calculator_screen?.text.toString()
        presenter.onEqualsClick(stringFromView)
    }

    override fun showResult(string: String) {
        view?.calculator_screen?.text = string
    }
}