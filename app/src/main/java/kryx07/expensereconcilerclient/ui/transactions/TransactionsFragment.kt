package kryx07.expensereconcilerclient.ui.transactions

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.R.string.transactions
import kryx07.expensereconcilerclient.base.fragment.RefreshableFragment
import kryx07.expensereconcilerclient.events.*
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject


class TransactionsFragment : RefreshableFragment(), TransactionsMvpView {


    @Inject lateinit var presenter: TransactionsPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus


    var actionMode: android.support.v7.view.ActionMode? = null

    private var selectionMode = false

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

    override fun onTransactionClick(position: Int) {
        Timber.e("onTransactionClick")
        Timber.e(selectionMode.toString())
        if (actionMode != null) {
            onTransactionLongClick(position)
        } else {
            showProgress()
            val transactionDetailFragment = TransactionDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(getString(R.string.clicked_transaction), adapter.transactions[position])
            transactionDetailFragment.arguments = bundle
            eventBus.post(ReplaceFragmentEvent(transactionDetailFragment))
        }
    }

    override fun onTransactionLongClick(position: Int): Boolean {
        Timber.e("onTransactionLongClick")
        Timber.e("Position: " + position)
        if (actionMode == null) {
            this.actionMode = (activity as AppCompatActivity).startSupportActionMode(actionModeCallback)
        }
        toggleSelection(position)

        return true
    }

    private fun toggleSelection(position: Int) {
        Timber.e("All items: ")
        for (i in 0 until adapter.transactions.size) {
            Timber.e(i.toString() + adapter.transactions[i])
        }
        adapter.toggleSelection(position)

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

    override fun updateData(transactions: List<Transaction>) = adapter.updateData(transactions)

    override fun onRefresh() = presenter.requestTransactions()

    @OnClick(R.id.fab)
    fun addTransactionClick() {
        showProgress()
        showFragment(TransactionDetailFragment())
    }

    private fun showFragment(fragment: Fragment) = eventBus.post(ReplaceFragmentEvent(fragment))
    override fun showProgress() = EventBus.getDefault().post(ShowProgressEvent())
    override fun hideProgress() {
        EventBus.getDefault().post(HideProgressEvent())
        EventBus.getDefault().post(HideRefresherEvent())
    }

    override fun showSnackAndLog(string: String) {
        Timber.e(string)
        Snackbar.make(view!!, string, Snackbar.LENGTH_LONG).show()
    }

    override fun showSnackAndLog(int: Int) {
        Timber.e(context.getString(int))
        Snackbar.make(view!!, context.getString(int), Snackbar.LENGTH_LONG).show()
    }

    val actionModeCallback = object : android.support.v7.view.ActionMode.Callback {

        override fun onCreateActionMode(mode: android.support.v7.view.ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_select_item, menu)
            view?.fab?.visibility = View.GONE
            view?.transactions_swipe_refresher?.isEnabled = false
            return true
        }

        override fun onPrepareActionMode(mode: android.support.v7.view.ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: android.support.v7.view.ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.delete_item -> {
                    val transactionsToRemove = adapter.getTransactionsFromPositions(adapter.getSelectedItemsPositions())
                    adapter.removeItems(adapter.getSelectedItemsPositions())
                    presenter.removeTransactions(transactionsToRemove)
                    mode.finish()
                    return true
                }

                else -> return false
            }
        }

        override fun onDestroyActionMode(mode: android.support.v7.view.ActionMode) {
            view?.fab?.visibility = View.VISIBLE
            view?.transactions_swipe_refresher?.isEnabled = true
            adapter.clearSelection()
            actionMode = null
        }


    }
}

