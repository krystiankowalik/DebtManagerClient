package kryx07.expensereconcilerclient.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_dashboard.*
import kryx07.expensereconcilerclient.App
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.*
import kryx07.expensereconcilerclient.ui.group.GroupsFragment
import kryx07.expensereconcilerclient.ui.transactions.master.TransactionsFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import javax.inject.Inject

class DashboardActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager
    //    @Inject lateinit var boxStore: BoxStore
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

        if (savedInstanceState == null) {
                 showFragment(TransactionsFragment())
        }

        //to be replaced with login!!!
        sharedPreferencesManager.write(getString(R.string.my_user), "2")

    }

    override fun onDestroy() {
        eventBus.unregister(this)
        super.onDestroy()
    }

    private fun setupActionBar() {
        setSupportActionBar(dashboard_toolbar)
        val toggle = ActionBarDrawerToggle(
                this, dashboard_drawer, dashboard_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        setupNavigationListener(toggle)
        dashboard_drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupNavigationListener(toggle: ActionBarDrawerToggle) {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                toggle.isDrawerIndicatorEnabled = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                toggle.setToolbarNavigationClickListener { onBackPressed() }
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                toggle.isDrawerIndicatorEnabled = true
            }
        }
    }


    private fun setupNavigationDrawer() {
        dashboard_nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_transactions -> {
                    return@OnNavigationItemSelectedListener this.handleDrawerOpenAction(TransactionsFragment())
                }
                R.id.menu_payables -> {
                    return@OnNavigationItemSelectedListener this.handleDrawerOpenAction(GroupsFragment())
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        eventBus.post(SetDrawerStatusEvent(true))
        super.onBackPressed()
    }

    private fun handleDrawerOpenAction(fragment: Fragment): Boolean {
        showFragment(fragment)
        dashboard_drawer.closeDrawers()
        return true
    }


    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        return fragments.firstOrNull { it != null && it.isVisible }
    }


    @Subscribe
    fun setDrawerStatus(setDrawerStatusEvent: SetDrawerStatusEvent) {

        if (setDrawerStatusEvent.expandable) {
            dashboard_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            dashboard_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        }

    }

    @Subscribe
    fun onShowProgress(showProgressEvent: ShowProgressEvent) {
        fragment_container.visibility = View.INVISIBLE
        dashboard_progress.visibility = View.VISIBLE
    }



    @Subscribe
    fun onHideProgress(hideProgressEvent: HideProgressEvent) {
        fragment_container.visibility = View.VISIBLE
        dashboard_progress.visibility = View.GONE
    }

    private fun showFragment(fragment: Fragment) = eventBus.post(ReplaceFragmentEvent(fragment))


    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
            beginTransaction().func().commit()

    @Subscribe
    fun onAddFragmentEvent(addFragmentEvent: AddFragmentEvent) {
        supportFragmentManager.inTransaction {
            add(R.id.fragment_container,
                    addFragmentEvent.fragment,
                    addFragmentEvent.fragment.javaClass.toString())
                    .addToBackStack(javaClass.toString())
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onReplaceFragmentEvent(replaceFragmentEvent: ReplaceFragmentEvent) {
        eventBus.post(SetDrawerStatusEvent(true))

        val fragment = supportFragmentManager.findFragmentByTag(replaceFragmentEvent.fragmentTag)
        if (fragment == null) {
            val newFragment = replaceFragmentEvent.fragment
            if (replaceFragmentEvent.bundle != null) {
                newFragment.arguments = replaceFragmentEvent.bundle
            }

            supportFragmentManager.inTransaction {
                replace(R.id.fragment_container,
                        newFragment,
                        replaceFragmentEvent.fragmentTag)
                        .addToBackStack(javaClass.toString())
            }
        }
        Timber.e(replaceFragmentEvent.toString())


        @Subscribe
        fun onRemoveFragmentEvent(removeFragmentEvent: RemoveFragmentEvent) {
            val fragment = supportFragmentManager.findFragmentByTag(removeFragmentEvent.fragmentTag)
            if (fragment != null) {
                supportFragmentManager.inTransaction {
                    remove(fragment)
                }
            } else {

            }
            Timber.e(removeFragmentEvent.toString())

        }

        @Subscribe
        fun onSetActivityTitle(setActivityTitleEvent: SetActivityTitleEvent) {
            Timber.e("I get that:" + setActivityTitleEvent.title)
            supportActionBar?.title = setActivityTitleEvent.title
        }




    }
}
