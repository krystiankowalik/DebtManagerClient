package kryx07.expensereconcilerclient.ui.group

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.fragment_groups.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.events.SetActivityTitleEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import kryx07.expensereconcilerclient.model.users.Group
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class GroupsFragment @Inject constructor() : Fragment(), GroupsMvpView {

    @Inject lateinit var presenter: GroupsPresenter
    private lateinit var adapter: GroupsAdapter
    @Inject lateinit var eventBus: EventBus

    /*  @Inject lateinit var apiClient: ApiClient
      @Inject lateinit var sharedPrefs: SharedPreferencesManager*/

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_groups, container, false)
        App.appComponent.inject(this)
        ButterKnife.bind(this, view)

        adapter = GroupsAdapter()
        view.groups_recycler.layoutManager = LinearLayoutManager(context)
        view.groups_recycler.adapter = adapter
        presenter.attachView(this)

        eventBus.post(SetActivityTitleEvent(getString(R.string.groups)))

        return view

    }


    override fun updateData(groups: List<Group>) {
        adapter.updateData(groups)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideProgress()
        super.onViewCreated(view, savedInstanceState)
    }

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