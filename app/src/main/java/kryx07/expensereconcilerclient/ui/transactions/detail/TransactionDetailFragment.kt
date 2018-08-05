package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.SnackableFragment
import kryx07.expensereconcilerclient.ui.group.GroupsFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.calculator.CalculatorDialogFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.date.DatePickerFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.listeners.DescriptionChangeListener
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject


class TransactionDetailFragment : SnackableFragment(), TransactionDetailMvpView {

    @Inject
    lateinit var presenter: TransactionDetailPresenter
    @Inject
    lateinit var eventBus: EventBus

    private val datePickerFragment = DatePickerFragment()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.appComponent.inject(this)
        presenter.attachView(this)
    }

    override fun onDetach() {
        presenter.detach()
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        ButterKnife.bind(this, view)

        setHasOptionsMenu(true)

        activity!!.dashboard_toolbar.title = getString(R.string.transaction_detail)

        addChangeListeners(view)

        return view
    }

    private fun addChangeListeners(view: View) {
        view.description_input.addTextChangedListener(DescriptionChangeListener(this))
    }

    override fun onDescriptionChanged(description: String) {
        presenter.handleDescriptionChanged(description)
    }

    override fun onAmountChanged(amount: String) {
        presenter.handleAmountChanged(amount)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_transaction_detail, menu)
        menu.findItem(R.id.menu_save).setOnMenuItemClickListener({
            presenter.saveTransaction()
            return@setOnMenuItemClickListener true
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideProgress()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun getTransactionDetailBundle(): Bundle? = arguments

    @OnClick(R.id.date_input)
    fun onDateClick() = pickDate()

    private fun pickDate() {
        datePickerFragment.initialDate = presenter.getInitialTransactionDate()
        datePickerFragment.show(fragmentManager, null)
        hideSoftInput()
    }

    @OnClick(R.id.amount_input)
    fun onAmountClick() {
        //showProgress()
//        val calculatorDialogFragment = CalculatorDialogFragment()
        val calculatorDialogFragment: DialogFragment = CalculatorDialogFragment()
        Timber.e(view!!.toString())
        val text = view?.amount_input?.text
        if (text != null && !text.isBlank()) {
            calculatorDialogFragment.passStringInBundle(
                    getString(R.string.amount_from_detail_transaction_screen), text.toString())
        }
        hideSoftInput()
        calculatorDialogFragment.show(fragmentManager, null)
    }

    @OnClick(R.id.description_input)
    fun onDescClick() {
    }

    @OnClick(R.id.payer_input)
    fun onPayerClick() {
        showProgress()
        hideSoftInput()
        showFragment(UserSearchFragment())
    }

    @OnClick(R.id.group_input)
    fun onGroupClick() {
        showProgress()
        /*val bundle = Bundle()
        bundle.putString(getString(R.string.fragment_action), getString(R.string.update_group_from_detail_view))*/
        hideSoftInput()
        showFragment(GroupsFragment().passStringInBundle(
                getString(R.string.fragment_action), getString(R.string.update_group_from_detail_view)))
    }

    @OnClick(R.id.common_input)
    fun onCheckBoxClick() = presenter.toggleCommonInput()

    override fun popBackStack() {
        fragmentManager?.popBackStack()
    }

    override fun updateDateView(date: String) = date_input.setText(date)
    override fun updateAmountView(amount: String) = amount_input.setText(amount)
    override fun updateDescriptionView(description: String) = description_input.setText(description)
    override fun updatePayerView(payer: String) = payer_input.setText(payer)
    override fun updateGroupView(group: String) = group_input.setText(group)
    override fun updateCommonView(common: Boolean) = common_input.setChecked(common)

    private fun Fragment.passStringInBundle(key: String, value: String): Fragment {
        val bundle = Bundle()
        bundle.putString(key, value)
        this@passStringInBundle.arguments = bundle
        return this
    }

    override fun hideSoftInput() {
        val view = this.activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}


