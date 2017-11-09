package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.RefreshableFragment
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import javax.inject.Inject


class TransactionsFragment : RefreshableFragment(), TransactionsMvpView {


    @Inject lateinit var presenter: TransactionsPresenter
    lateinit var adapter: TransactionsAdapter

    @JvmField
    @BindView(R.id.fab)
    var floatingActionButton: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        super.onCreateView(inflater, view.transactions_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)
        setupFab()

        //Adapter setup
        adapter = TransactionsAdapter()
        view.transactions_recycler.layoutManager = LinearLayoutManager(context)
        view.transactions_recycler.adapter = adapter

        presenter.attachView(this)

        (activity as DashboardActivity).supportActionBar?.setTitle(R.string.transactions)

       /* val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, TransactionDetailFragment(), javaClass.name)
        ft.commit()*/
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

    override fun onRefresh() {
        presenter.requestTransactions()
    }


    fun setupFab() {

//      /*  (activity as DashboardActivity).showFragment(TransactionDetailFragment())

        floatingActionButton?.setOnClickListener {
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, TransactionDetailFragment(), javaClass.name)
            ft.commit()
            //Toast.makeText(activity.applicationContext, "To be implemented soon :)", Toast.LENGTH_SHORT).show()

        }
    }


}

