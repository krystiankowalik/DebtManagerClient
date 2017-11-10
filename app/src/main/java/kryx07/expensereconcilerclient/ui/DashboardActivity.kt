package kryx07.expensereconcilerclient.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import io.objectbox.BoxStore
import kotlinx.android.synthetic.main.activity_dashboard.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.AddFragmentEvent
import kryx07.expensereconcilerclient.events.HideProgressEvent
import kryx07.expensereconcilerclient.events.ReplaceFragmentEvent
import kryx07.expensereconcilerclient.events.ShowProgressEvent
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class DashboardActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager
    @Inject lateinit var boxStore: BoxStore
    @Inject lateinit var eventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        App.appComponent.inject(this)

        ButterKnife.bind(this)
        eventBus.register(this)
        dashboard_progress.indeterminateDrawable.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN)

        setupActionBar()
        setupNavigationDrawer()

        //setupDrawerAndActionBar()
        if (savedInstanceState == null) {
            showFragment(TransactionsFragment())
        }

        //to be replaced with login!!!
        sharedPreferencesManager.write(getString(R.string.my_user), "2")


    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun setupActionBar() {
        setSupportActionBar(dashboard_toolbar)
        val toggle = ActionBarDrawerToggle(
                this, dashboard_drawer, dashboard_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        // Handle clicks on drawer
        dashboard_drawer.addDrawerListener(toggle)
        // Sync state to have a hamburger menu icon
        toggle.syncState()
    }

    private fun setupNavigationDrawer() {
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

    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        return fragments.firstOrNull { it != null && it.isVisible }
    }

    @Subscribe
    fun onShowProgress(showProgressEventEvent: ShowProgressEvent) {
        fragment_container.visibility = View.INVISIBLE
        dashboard_progress.visibility = View.VISIBLE
    }

    @Subscribe
    fun onHideProgress(hideProgressEvent: HideProgressEvent) {
        fragment_container.visibility = View.VISIBLE
        dashboard_progress.visibility = View.GONE
    }

    private fun showFragment(fragment: Fragment) {
        eventBus.post(ReplaceFragmentEvent(fragment, javaClass.toString()))
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
            beginTransaction().func().commit()


    @Subscribe
    fun addFragment(addFragmentEvent: AddFragmentEvent) {
        supportFragmentManager.inTransaction {
            add(R.id.fragment_container,
                    addFragmentEvent.fragment,
                    addFragmentEvent.fragment.javaClass.toString())
                    .addToBackStack(addFragmentEvent.tag)
        }
    }

    @Subscribe
    fun replaceFragment(replaceFragmentEvent: ReplaceFragmentEvent) {
        supportFragmentManager.inTransaction {
            replace(R.id.fragment_container,
                    replaceFragmentEvent.fragment,
                    replaceFragmentEvent.fragment.javaClass.toString())
                    .addToBackStack(replaceFragmentEvent.tag)
        }
    }


}
