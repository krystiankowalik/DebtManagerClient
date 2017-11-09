package kryx07.expensereconcilerclient.ui.users

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.SortedList
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_search_view.view.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.model.users.User
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.ui.DashboardActivity
import javax.inject.Inject


class UserSearchFragment : Fragment(), SearchView.OnQueryTextListener {


    //    @Inject lateinit var presenter: UsersPresenter
    lateinit var adapter: UsersAdapter

    @Inject lateinit var apiClient: ApiClient

    val usersList = arrayListOf<User>()


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

        adapter = UsersAdapter()
        view.users_recycler.layoutManager = LinearLayoutManager(context)
        view.users_recycler.adapter = adapter

        //presenter.attachView(this)

        (activity as DashboardActivity).supportActionBar?.setTitle("User Search View")
        //(activity as DashboardActivity).supportActionBar?.hide()

/*        usersList.addAll(Arrays.asList(User(1, "dupa1", "dupa1"), User(2, "dupa2", "dupa2"), User(3, "cos", "cos")))*/


        requestUsers()
//        updateData(usersList)


        setHasOptionsMenu(true)
        return view
    }

    fun requestUsers() {
        apiClient.service.allUsers
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                    usersList.addAll(users)
                    updateData(usersList)
                })
    }

    override fun onStart() {
        super.onStart()
        //      presenter.start()
    }

    override fun onDestroyView() {
        //      presenter.detach()
        super.onDestroyView()
    }

    fun updateData(users: List<User>) {
        adapter.updateData(users)
    }

    private fun filter(users: List<User>, query: String): List<User> {
        val lowerCaseQuery = query.toLowerCase()
        val filteredUsers = arrayListOf<User>()
        users.forEach { u ->
            if (u.username.contains(lowerCaseQuery)) {
                filteredUsers.add(u)
            }
        }

        return filteredUsers
    }

    private fun toList(sortedList: SortedList<User>): List<User> {
        return (0 until sortedList.size()).map { sortedList[it] }

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        var filteredList = filter(usersList, query)
        updateData(filteredList)
        return false
    }


    override fun onQueryTextChange(query: String): Boolean {
        val filteredList = filter(usersList, query)
        updateData(filteredList)
        return true
    }


}