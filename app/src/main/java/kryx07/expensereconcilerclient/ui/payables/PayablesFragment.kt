/*
package kryx07.expensereconcilerclient.ui.payables

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_payables.*
import kotlinx.android.synthetic.main.fragment_payables.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.RefreshableFragment
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.*
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.StringUtilities
import java.math.BigDecimal
import javax.inject.Inject


class PayablesFragment : RefreshableFragment(), PayablesMvpView {


    @Inject lateinit var presenter: PayablesPresenter
    @Inject lateinit var sharedPrefs: SharedPreferencesManager
    lateinit var adapter: PayablesAdapter
    lateinit var myUserName: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_payables, container, false)
        super.onCreateView(inflater, view.payables_swipe_refresher, savedInstanceState)
        ButterKnife.bind(this, view)
        App.appComponent.inject(this)

        this.myUserName = sharedPrefs.read(getString(kryx07.expensereconcilerclient.R.string.my_user))
        this.adapter = PayablesAdapter(myUserName)
        view.payables_recycler.layoutManager = LinearLayoutManager(context)
        view.payables_recycler.adapter = adapter

        presenter.attachView(this)

        (activity as DashboardActivity).supportActionBar?.setTitle(R.string.payables)

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

    override fun updateData(payables: List<Payable>) {
        adapter.updateData(payables)
    }


    override fun updateTotals(receivablesTotal: BigDecimal, payablesTotal: BigDecimal) {
        payables_total_amount.text = StringUtilities.formatCurrency(payablesTotal, getString(R.string.currency))
        receivables_total_amount.text = StringUtilities.formatCurrency(receivablesTotal, getString(R.string.currency))
        payables_total_amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        receivables_total_amount.setTextColor(ContextCompat.getColor(context, R.color.green))
    }

    override fun onRefresh() {
        presenter.requestPayables()
    }

}*/
