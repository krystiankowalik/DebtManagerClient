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
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.ShowProgress
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment
import kryx07.expensereconcilerclient.utils.SharedPreferencesManager
import kryx07.expensereconcilerclient.utils.ViewUtilities
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
        dashboard_progress.indeterminateDrawable.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN)

        App.appComponent.inject(this)

        setupActionBar()
        setupNavigationDrawer()

        //setupDrawerAndActionBar()
        if (savedInstanceState == null) {
            val newFragment = TransactionsFragment()
            ViewUtilities.showFragment(supportFragmentManager, newFragment, newFragment.javaClass.toString(), this.toString())
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
                    /*supportFragmentManager.inTransaction {
                        add(R.id.fragment_container, TransactionsFragment(), TransactionsFragment::class.java.toString())
                        addToBackStack(this@DashboardActivity::class.java.toString())
                    }*/
                    val newFragment = TransactionsFragment()
                    ViewUtilities.showFragment(supportFragmentManager, newFragment, newFragment.javaClass.toString(), this.toString())
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
    fun onShowProgress(showProgress: ShowProgress) {
        fragment_container.visibility = View.INVISIBLE
        dashboard_progress.visibility = View.VISIBLE
    }

    @Subscribe
    fun onHideProgress(showProgress: HideProgress) {
        fragment_container.visibility = View.VISIBLE
        dashboard_progress.visibility = View.GONE
    }

    /*  override fun onBackPressed() {
          if (fragmentManager.backStackEntryCount == 0) {
              super.onBackPressed()
          } else if (fragmentManager.backStackEntryCount == 1) {
              moveTaskToBack(false)
          } else {
              fragmentManager.popBackStack()
          }
      }*/

    /*fun showFragment(fragment: Fragment) {
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
    }*/


    fun showFragment(supportFragmentManager: FragmentManager, fragment: Fragment, fragmentTag: String, transactionTag: String) {

        if (supportFragmentManager.findFragmentByTag(fragmentTag) == null) {
            // This fragment does not lie on back stack, need to be added
            supportFragmentManager.beginTransaction()
                    // Add a tag to prevent duplicating insertions of the same fragment
                    .replace(R.id.fragment_container, fragment, fragmentTag)
                    .addToBackStack(transactionTag)
                    .commit()
        } else {
            // Get the fragment from the back stack
            supportFragmentManager.popBackStackImmediate(transactionTag, 0)
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
            beginTransaction().func().commit()

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }


    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }


}
