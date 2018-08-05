package kryx07.expensereconcilerclient.ui.users

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_user_search_view.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.base.fragment.SnackableFragment
import kryx07.expensereconcilerclient.events.SetDrawerStatusEvent
import kryx07.expensereconcilerclient.model.users.User
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class UserSearchFragment : SnackableFragment(), SearchView.OnQueryTextListener, UserSearchMvpView, UsersAdapter.OnUserClickListener {


    @Inject
    lateinit var presenter: UserSearchPresenter
    @Inject
    lateinit var eventBus: EventBus
    lateinit var adapter: UsersAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_user_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_user_search_view, container, false)
        super.onCreateView(inflater, container, savedInstanceState)

        adapter = UsersAdapter(this)
        view.users_recycler.layoutManager = LinearLayoutManager(context)
        view.users_recycler.adapter = adapter


        activity!!.dashboard_toolbar.title = getString(R.string.transaction_detail)
        eventBus.post(SetDrawerStatusEvent(false))

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onUserClick(user: User) {
        presenter.handleUserClick(user)
    }

    override fun popBackStack() {
        fragmentManager!!.popBackStack()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.appComponent.inject(this)
        presenter.attachView(this)
    }

    override fun onDetach() {
        presenter.detach()
        super.onDetach()
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


}