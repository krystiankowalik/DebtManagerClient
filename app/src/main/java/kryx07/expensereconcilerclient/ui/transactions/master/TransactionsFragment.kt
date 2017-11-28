package kryx07.expensereconcilerclient.ui.transactions.master

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.RefreshableFragment
import kryx07.expensereconcilerclient.events.SetDrawerStatusEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject


class TransactionsFragment : RefreshableFragment(), TransactionsMvpView {

    @Inject lateinit var presenter: TransactionsPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus

    private var actionMode: android.support.v7.view.ActionMode? = null
    private var actionModeCallback: android.support.v7.view.ActionMode.Callback =
            TransactionSelectionAction(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transactions, container, false)
        super.onCreateView(inflater, view.transactions_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)
        presenter.attachView(this)
        setupAdapter(view)

        activity.dashboard_toolbar.title = getString(R.string.my_transactions)
        registerForContextMenu(view.transactions_recycler)
        eventBus.post(SetDrawerStatusEvent(false))

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

    override fun onTransactionClick(position: Int) {
        if (actionMode != null) {
            onTransactionLongClick(position)
        } else {
            presenter.handleTransactionClick(adapter.transactions[position])
        }
    }

    override fun onTransactionLongClick(position: Int): Boolean {
        if (actionMode == null) {
            this.actionMode = (activity as AppCompatActivity).startSupportActionMode(actionModeCallback)
        }
        toggleTransactionSelection(position)
        return true
    }

    private fun toggleTransactionSelection(position: Int) {
        adapter.toggleSelection(position)
        displaySelectedCountOnActionBar()
    }

    private fun displaySelectedCountOnActionBar() {
        val count = adapter.selectedItemCount
        if (count == 0) {
            actionMode?.finish()
        } else {
            if (count == 1) {
                actionMode?.title = count.toString() + " " + getString(R.string.transaction_selected)
            } else {
                actionMode?.title = count.toString() + " " + getString(R.string.transactions_selected)
            }
            actionMode?.invalidate()
        }
    }

    override fun onCreateActionMode() {
        view?.fab?.visibility = View.GONE
        view?.transactions_swipe_refresher?.isEnabled = false
    }

    override fun onDeleteActionClicked() {
        val transactionsToRemove = adapter.getTransactionsFromPositions(adapter.getSelectedItemsPositions())
        adapter.removeItems(adapter.getSelectedItemsPositions())
        presenter.removeTransactions(transactionsToRemove)
    }

    override fun onDestroyActionMode() {
        view?.fab?.visibility = View.VISIBLE
        view?.transactions_swipe_refresher?.isEnabled = true
        adapter.clearSelection()
        actionMode = null
    }

    override fun onRefresh() = presenter.requestTransactions()

    @OnClick(R.id.fab)
    fun addTransactionClick() {
        showProgress()
        showFragment(TransactionDetailFragment())
    }

    override fun updateData(transactions: List<Transaction>) = adapter.updateData(transactions)

    override fun showSnackAndLog(string: String) {
        Timber.e(string)
        Snackbar.make(view!!, string, Snackbar.LENGTH_LONG).show()
    }

    override fun showSnackAndLog(int: Int) {
        Timber.e(context.getString(int))
        Snackbar.make(view!!, context.getString(int), Snackbar.LENGTH_LONG).show()
    }


}