package kryx07.expensereconcilerclient.ui.transactions.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_transaction_detail.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.ui.group.GroupsFragment
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class TransactionDetailFragment : android.support.v4.app.Fragment(), TransactionDetailMvpView {

    @Inject lateinit var presenter: TransactionDetailPresenter
    @Inject lateinit var eventBus: EventBus

    private val datePickerFragment = DatePickerFragment()

    override fun onAttach(context: Context?) {
        Timber.e("onAttach")
        super.onAttach(context)
        App.appComponent.inject(this)
        presenter.attachView(this)
    }

    override fun onDetach() {
        Timber.e("onDetach")
        presenter.detach()
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_transaction_detail, container, false)
        ButterKnife.bind(this, view)

        setHasOptionsMenu(true)

        activity.dashboard_toolbar.title = getString(R.string.transaction_detail)

        /*addAmountChangedListener(view)
        addDescriptionChangedListener(view)*/
        Timber.e("onCreateView")


        return view
    }

    /*private fun addAmountChangedListener(view: View) {
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

    }*/

    override fun popBackStack() {
        fragmentManager.popBackStack()
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
        Timber.e("onViewCreated")
        hideProgress()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        Timber.e("onResume")
        super.onResume()
    }

    override fun getTransactionDetailBundle(): Bundle? = arguments



    @OnClick(R.id.date_input)
    fun onDateClick() = pickDate()

    private fun pickDate() {
        datePickerFragment.initialDate = presenter.getInitialTransactionDate()
        datePickerFragment.show(fragmentManager, null)
    }

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

    override fun onStart() {
        Timber.e("onStart")
        super.onStart()
         presenter.start()
    }

    @OnClick(R.id.common_input)
    fun onCheckBoxClick() = presenter.toggleCommonInput()

    override fun updateDateView(date: String) = date_input.setText(date)
    override fun updateAmountView(amount: String) = amount_input.setText(amount)
    override fun updateDescriptionView(description: String) = description_input.setText(description)
    override fun updatePayerView(payer: String) = payer_input.setText(payer)
    override fun updateGroupView(group: String) = group_input.setText(group)
    override fun updateCommonView(common: Boolean) = common_input.setChecked(common)

}


