package kryx07.expensereconcilerclient.ui.group

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_groups.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.*
import kryx07.expensereconcilerclient.model.users.Group
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class GroupsFragment @Inject constructor() : Fragment(), GroupsMvpView,GroupsAdapter.OnGroupClickListener{

    @Inject lateinit var presenter: GroupsPresenter
    private lateinit var adapter: GroupsAdapter
    @Inject lateinit var eventBus: EventBus

    /*  @Inject lateinit var apiClient: ApiClient
      @Inject lateinit var sharedPrefs: SharedPreferencesManager*/

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_groups, container, false)
        App.appComponent.inject(this)
        ButterKnife.bind(this, view)

        adapter = GroupsAdapter(this)
        view.groups_recycler.layoutManager = LinearLayoutManager(context)
        view.groups_recycler.adapter = adapter
        presenter.attachView(this)

        activity.dashboard_toolbar.title = getString(R.string.my_groups)
        eventBus.post(SetDrawerStatusEvent(false))

        return view

    }

    override fun onGroupClick(group: Group) {
        Timber.e("Clicked")
        eventBus.postSticky(UpdateGroupEvent(group))
        fragmentManager.popBackStack()
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