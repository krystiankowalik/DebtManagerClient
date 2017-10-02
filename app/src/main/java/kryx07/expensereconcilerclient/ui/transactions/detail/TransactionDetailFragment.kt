package kryx07.expensereconcilerclient.ui.transactions.detail

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.TransactionDetailMvpView
import kryx07.expensereconcilerclient.ui.transactions.TransactionsAdapter
import javax.inject.Inject

class TransactionDetailFragment : Fragment(), TransactionDetailMvpView {

    @Inject lateinit var presenter: TransactionDetailPresenter
    lateinit var adapter: TransactionsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        App.appComponent.inject(this)

        //Adapter setup
        presenter.attachView(this)

        val supportActionBar = (activity as DashboardActivity).supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.transactions)
            supportActionBar.setHomeButtonEnabled(true)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    override fun updateData(transactions: List<Transaction>) {
        adapter.updateData(transactions)
    }



}

