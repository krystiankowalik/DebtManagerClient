package kryx07.expensereconcilerclient.ui.transactions.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.events.SetActivityTitleEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import kryx07.expensereconcilerclient.ui.transactions.TransactionsAdapter
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus
import org.joda.time.DateTime
import javax.inject.Inject


class TransactionDetailFragment : android.support.v4.app.Fragment(), TransactionDetailMvpView, DatePickerDialog.OnDateSetListener {


    @Inject lateinit var presenter: TransactionDetailPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        App.appComponent.inject(this)
        ButterKnife.bind(this, view)
        presenter.attachView(this)


        eventBus.post(SetActivityTitleEvent(getString(R.string.transaction_detail)))

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDate(view)
        hideProgress()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initDate(view: View) =
            view.date_input.setText(StringUtilities.formatDate(DateTime.now()))


    @OnClick(R.id.date_input)
    fun onDateClick() = showCalendar()


    @OnClick(R.id.payer_input)
    fun onPayerClick() {
        showProgress()
        showFragment(UserSearchFragment())
    }

    private fun showCalendar() {
        val date = presenter.parseGregorianDate(date_input.text.toString())
        DatePickerDialog(context, this, date.year, date.monthOfYear, date.dayOfMonth)
                .show()
    }

    override fun onDateSet(p0: DatePicker, year: Int, month: Int, day: Int) =
            date_input
                    .setText(StringUtilities
                            .formatDate(presenter.getDateOf(year, month, day)))

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun showFragment(fragment: Fragment) = eventBus.post(ReplaceFragmentEvent(fragment))

    override fun showProgress() = EventBus.getDefault().post(ShowProgressEvent())

    override fun hideProgress() = EventBus.getDefault().post(HideProgressEvent())


}

