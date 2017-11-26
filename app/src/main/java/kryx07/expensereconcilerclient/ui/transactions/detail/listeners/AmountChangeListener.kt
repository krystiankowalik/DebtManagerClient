package kryx07.expensereconcilerclient.ui.transactions.detail.listeners

import android.text.Editable
import android.text.TextWatcher
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailMvpView

class AmountChangeListener(val view: TransactionDetailMvpView) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        view.onAmountChanged(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}