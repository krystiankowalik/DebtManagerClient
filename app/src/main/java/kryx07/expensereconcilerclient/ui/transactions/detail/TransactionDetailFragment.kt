package kryx07.expensereconcilerclient.ui.transactions.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.events.SetActivityTitleEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.TransactionDetailMvpView
import kryx07.expensereconcilerclient.ui.transactions.TransactionsAdapter
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus
import org.joda.time.DateTime
import org.joda.time.chrono.GregorianChronology
import javax.inject.Inject


class TransactionDetailFragment : android.support.v4.app.Fragment(), TransactionDetailMvpView, DatePickerDialog.OnDateSetListener {


    @Inject lateinit var presenter: TransactionDetailPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        App.appComponent.inject(this)

        presenter.attachView(this)

        setDateInputListeners(view)
        setUsersSearchListeners(view)

        //val supportActionBar = (activity as DashboardActivity).supportActionBar
        /*if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.transactions)
//            supportActionBar.setHomeButtonEnabled(true)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }*/

        eventBus.post(SetActivityTitleEvent(getString(R.string.transaction_detail)))

        val toolbar = (activity as DashboardActivity).dashboard_toolbar
        //toolbar.

        hideProgress()
        return view

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
                //ViewUtilities.showPreviousFragment(activity.supportFragmentManager,this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDateInputListeners(view: View) {
        view.date_input.setText(StringUtilities.formatDate(DateTime.now()))

        view.date_input.setOnClickListener({
            showCalendar()
        })
    }

    private fun setUsersSearchListeners(view: View) {
        view.payer_input.setOnClickListener({
            showProgress()
            showFragment(UserSearchFragment())
        })
    }

    private fun showCalendar() {
        val date = try {
            DateTime.parse(date_input.text.toString())
        } catch (e: IllegalArgumentException) {
            DateTime.now()
        }
        DatePickerDialog(context, this, date.year, date.monthOfYear - 1, date.dayOfMonth)
                .show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) =
            date_input
                    .setText(StringUtilities
                            .formatDate(
                                    DateTime(GregorianChronology
                                            .getInstance())
                                            .withDate(year, month + 1, day)))

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

    private fun showFragment(fragment: Fragment) {
        eventBus.post(ReplaceFragmentEvent(fragment, javaClass.toString()))
    }

    override fun showProgress() = EventBus.getDefault().post(ShowProgressEvent())

    override fun hideProgress() = EventBus.getDefault().post(HideProgressEvent())


}

