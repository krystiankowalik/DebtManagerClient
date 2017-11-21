package kryx07.expensereconcilerclient.ui.transactions.detail

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.DatePicker
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.*
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.ui.group.GroupsFragment
import kryx07.expensereconcilerclient.ui.transactions.TransactionsAdapter
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import kryx07.expensereconcilerclient.utils.StringUtilities
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.joda.time.LocalDate
import java.math.BigDecimal
import javax.inject.Inject


class TransactionDetailFragment : android.support.v4.app.Fragment(), TransactionDetailMvpView, DatePickerDialog.OnDateSetListener {

    @Inject lateinit var presenter: TransactionDetailPresenter
    private lateinit var adapter: TransactionsAdapter
    @Inject lateinit var eventBus: EventBus
    private lateinit var datePickerDialog: DatePickerDialog

    var transaction = Transaction()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        ButterKnife.bind(this, view)

        setHasOptionsMenu(true)

        activity.dashboard_toolbar.title = getString(R.string.transaction_detail)
        eventBus.post(SetDrawerStatusEvent(false))

        addAmountChangedListener(view)
        addDescriptionChangedListener(view)

        return view
    }

    private fun addAmountChangedListener(view: View) {
        view.amount_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    transaction.amount = BigDecimal(s.toString())
                } catch (e: NumberFormatException) {
                    transaction.amount = BigDecimal.ZERO
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun addDescriptionChangedListener(view: View) {
        view.description_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                transaction.description = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    override fun popBackStack() {
        fragmentManager.popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_transaction_detail, menu)
        menu.findItem(R.id.menu_save).setOnMenuItemClickListener({
            presenter.saveTransaction(transaction)
            return@setOnMenuItemClickListener true
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideProgress()
        datePickerDialog = DatePickerDialog(context, this, 2017, 1, 1)
        if (arguments != null) {
            handleReceivedBundle(arguments)
        } else {
            initDate()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun handleReceivedBundle(bundle: Bundle?) {
        if (bundle != null) {
            val transaction = bundle.getParcelable<Transaction>(getString(R.string.clicked_transaction))
            if (transaction != null) {
                this.transaction = transaction
            }
        }
    }

    private fun initDate() {
        transaction.date = LocalDate.now()
        date_input.setText(StringUtilities.formatDate(transaction.date))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.appComponent.inject(this)
        eventBus.register(this)
        presenter.attachView(this)
    }

    override fun onDetach() {
        eventBus.unregister(this)
        presenter.detach()
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        updateView(transaction)
    }

    override fun updateView(transaction: Transaction) {
        date_input.setText(StringUtilities.formatDate(transaction.date))
        amount_input.setText(transaction.amount.toString())
        description_input.setText(transaction.description)
        payer_input.setText(transaction.payer.username)
        group_input.setText(transaction.group.name)
        common_input.isChecked = transaction.common
    }

    @Subscribe
    fun onUpdatePayerEvent(updatePayerEvent: UpdatePayerEvent) {
        transaction.payer = updatePayerEvent.payer
    }

    @Subscribe
    fun onUpdateGroupEvent(updateGroupEvent: UpdateGroupEvent) {
        transaction.group = updateGroupEvent.group
    }

    @OnClick(R.id.date_input)
    fun onDateClick() = showCalendar()


    @OnClick(R.id.payer_input)
    fun onPayerClick() {
        showProgress()
        showFragment(UserSearchFragment())
    }

    @OnClick(R.id.group_input)
    fun onGroupClick() {
        showProgress()
        val bundle = Bundle()
        bundle.putString(getString(R.string.fragment_action), getString(R.string.update_group_from_detail_view))
        showFragment(GroupsFragment(), bundle)
    }


    private fun showCalendar() {
        datePickerDialog.updateDate(transaction.date.year, transaction.date.monthOfYear - 1, transaction.date.dayOfMonth)
        datePickerDialog.show()
    }


    override fun onDateSet(p0: DatePicker, year: Int, month: Int, day: Int) =
            date_input
                    .setText(StringUtilities
                            .formatDate(presenter.getDateOf(year, month, day)))

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    private fun showFragment(fragment: Fragment) = eventBus.post(ReplaceFragmentEvent(fragment))
    private fun showFragment(fragment: Fragment, bundle: Bundle) = eventBus.post(ReplaceFragmentEvent(fragment, bundle))
    private fun showFragment(fragment: Fragment, customTag: String) = eventBus.post(ReplaceFragmentEvent(fragment, customTag))

    override fun showProgress() = EventBus.getDefault().post(ShowProgressEvent())

    override fun hideProgress() = EventBus.getDefault().post(HideProgressEvent())

    @OnClick(R.id.common_input)
    fun onCheckBoxClick() {
        common_input.isChecked = !common_input.isChecked
        transaction.common = common_input.isChecked
    }


}

