package kryx07.expensereconcilerclient.ui.users

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import kotlinx.android.synthetic.main.fragment_user_search_view.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.HideRefresherEvent
import kryx07.expensereconcilerclient.events.SetActivityTitleEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import kryx07.expensereconcilerclient.model.users.User
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class UserSearchFragment : Fragment(), SearchView.OnQueryTextListener, UserSearchMvpView {


    @Inject lateinit var presenter: UserSearchPresenter
    //@Inject lateinit var apiClient: ApiClient
    @Inject lateinit var eventBus: EventBus

    lateinit var adapter: UsersAdapter

    //private val usersList = arrayListOf<User>()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_user_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_user_search_view, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        App.appComponent.inject(this)

//        activity.actionBar.setDisplayHomeAsUpEnabled(true)

        adapter = UsersAdapter()
        view.users_recycler.layoutManager = LinearLayoutManager(context)
        view.users_recycler.adapter = adapter

        presenter.attachView(this)

        eventBus.post(SetActivityTitleEvent("Select the payer"))
        setHasOptionsMenu(true)

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

    override fun updateData(users: List<User>) {
        adapter.updateData(users)
        hideProgress()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        presenter.filter(query)
        return false
    }


    override fun onQueryTextChange(query: String): Boolean {
        presenter.filter(query)
        return true
    }

    override fun showProgress() {
        eventBus.post(ShowProgressEvent())
    }

    override fun hideProgress() {
        eventBus.post(HideProgressEvent())
        eventBus.post(HideRefresherEvent())
    }


}