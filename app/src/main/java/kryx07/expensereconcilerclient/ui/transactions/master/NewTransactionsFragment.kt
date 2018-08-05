package kryx07.expensereconcilerclient.ui.transactions.master

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.RefreshableFragment
import kryx07.expensereconcilerclient.events.SetDrawerStatusEvent
import kryx07.expensereconcilerclient.model.transaction.Transaction
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NewTransactionsFragment : RefreshableFragment(), TransactionsMvpView {


    @Inject
    lateinit var presenter: TransactionsPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject
    lateinit var eventBus: EventBus

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)
        super.onCreateView(inflater, view.transactions_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)
        presenter.attachView(this)
        setupAdapter(view)
        return view
    }

    private fun setupAdapter(view: View) {
        adapter = TransactionsAdapter(this)
        view.transactions_recycler.layoutManager = LinearLayoutManager(context)
        view.transactions_recycler.adapter = adapter
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

    override fun onRefresh() {
        transactions_recycler.visibility = View.GONE
        presenter.requestTransactions()
    }

    override fun hideProgress() {
        super.hideProgress()
        transactions_recycler.visibility = View.VISIBLE
    }


    /*override fun showSnackAndLog(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSnackAndLog(int: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    override fun onCreateActionMode() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteActionClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyActionMode() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTransactionClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTransactionLongClick(position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}