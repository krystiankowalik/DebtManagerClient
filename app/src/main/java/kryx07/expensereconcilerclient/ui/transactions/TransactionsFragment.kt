package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.RefreshableFragment
import kryx07.expensereconcilerclient.events.*
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject


class TransactionsFragment : RefreshableFragment(), TransactionsMvpView {

    @Inject lateinit var presenter: TransactionsPresenter
    lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus

    @JvmField
    @BindView(R.id.fab)
    var floatingActionButton: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        super.onCreateView(inflater, view.transactions_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)

        //Adapter setup
        setupAdapter(view)

        presenter.attachView(this)

        //(activity as DashboardActivity).supportActionBar?.setTitle(R.string.transactions)
        eventBus.post(SetActivityTitleEvent(getString(R.string.transactions)))

        return view
    }

    private fun setupAdapter(view: View) {
        adapter = TransactionsAdapter()
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

    override fun updateData(transactions: List<Transaction>) = adapter.updateData(transactions)

    override fun onRefresh() = presenter.requestTransactions()

    @OnClick(R.id.fab)
    fun addTransactionClick() {
        showProgress()
        showFragment(TransactionDetailFragment())
    }

    private fun showFragment(fragment: Fragment) = eventBus.post(ReplaceFragmentEvent(fragment, javaClass.toString()))
    override fun showProgress() = EventBus.getDefault().post(ShowProgressEvent())
    override fun hideProgress() {
        EventBus.getDefault().post(HideProgressEvent())
        EventBus.getDefault().post(HideRefresherEvent())
    }

    override fun showToastAndLog(string: String) {
        Timber.e(string)
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    override fun showToastAndLog(int: Int) {
        Timber.e(context.getString(int))
        Toast.makeText(context, context.getString(int), Toast.LENGTH_LONG).show()
    }
}

