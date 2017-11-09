package kryx07.expensereconcilerclient.ui

//import kryx07.expensereconcilerclient.ui.payables.PayablesFragment
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import butterknife.ButterKnife
import io.objectbox.BoxStore
import kotlinx.android.synthetic.main.activity_dashboard.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class DashboardActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager
    @Inject lateinit var boxStore: BoxStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        ButterKnife.bind(this)
        EventBus.getDefault().register(this)

        setupDrawerAndToolbar()
        if (savedInstanceState == null) {
            showFragment(TransactionsFragment())
        }

        App.appComponent.inject(this)

        //to be replaced with login!!!
        sharedPreferencesManager.write(getString(R.string.my_user), "2")

        dashboard_progress.indeterminateDrawable.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN)

       test()
    }

    fun test() {

        /*val userStore = boxStore.boxFor<User>(User::class.java)
        userStore.put(User(0, "dupa", "dupa2"))
        userStore.query().build().find().forEach({ u -> Timber.e(u.toString()) })*/

/*

        database.userDao().getAll().forEach({ u -> Timber.e(u.toString()) })
        var group1: Group = Group(1, "group")
        var group2: Group = Group(2, "group2")

        database.groupDao().insert(group1)
        database.groupDao().insert(group2)

        var user1: User = User(1, "myuser", "password", 1)
        var user2: User = User(2, "myuser2", "password2", 1)
        database.userDao().insert(user1)
        database.userDao().insert(user2)

        database.groupDao().getAll().forEach({ group -> Timber.e(group.toString()) })

        database.groupDao().getAllGroupsWithUsers().forEach({ gwu -> Timber.e(gwu.toString()) })
*/

        /* var usersGroups1 = UsersGroups(1, user1.id, group1.id)
         var usersGroups2 = UsersGroups(2, user2.id, group2.id)

         database.usersGroupsDao().insert(usersGroups1)
         database.usersGroupsDao().insert(usersGroups2)

         database.groupDao().getAll().forEach({ g -> Timber.e(g.toString()) })

         database.userDao().getAllGroups().groupIdsList.forEach({ id -> Timber.e(id.toString()) })*/
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun setupDrawerAndToolbar() {
        setSupportActionBar(dashboard_toolbar)
        val toggle = ActionBarDrawerToggle(
                this, dashboard_drawer, dashboard_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        // Handle clicks on drawer
        dashboard_drawer.addDrawerListener(toggle)
        // Sync state to have a hamburger menu icon
        toggle.syncState()

        dashboard_nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_transactions -> {
                    showFragment(TransactionsFragment())
                    return@OnNavigationItemSelectedListener true
                }
            /*R.id.menu_payables -> {
                showFragment(PayablesFragment())
                return@OnNavigationItemSelectedListener true
            }*/
            }
            false
        })
    }

    @Subscribe
    fun onShowProgress(showProgress: ShowProgress) {
        fragment_container.visibility = View.INVISIBLE
        dashboard_progress.visibility = View.VISIBLE
    }

    @Subscribe
    fun onHideProgress(showProgress: HideProgress) {
        fragment_container.visibility = View.VISIBLE
        dashboard_progress.visibility = View.GONE
    }

        fun showFragment(fragment: Fragment) {
            val tag = fragment.javaClass.name
            val manager = supportFragmentManager

            if (manager.findFragmentByTag(tag) == null) {
                // This fragment does not lie on back stack, need to be added
                manager.beginTransaction()
                        // Add a tag to prevent duplicating insertions of the same fragment
                        .replace(R.id.fragment_container, fragment, tag)
                        .addToBackStack(tag)
                        .commit()
            } else {
                // Get the fragment from the back stack
                manager.popBackStackImmediate(tag, 0)
            }
            dashboard_drawer.closeDrawer(Gravity.START)
        }


}
