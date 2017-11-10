package kryx07.expensereconcilerclient.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import kryx07.expensereconcilerclient.R
import kryx07.expensereconcilerclient.events.HideProgress
import kryx07.expensereconcilerclient.events.HideRefresher
import kryx07.expensereconcilerclient.events.ShowProgress
import org.greenrobot.eventbus.EventBus

object ViewUtilities {

    fun showProgress() {
        EventBus.getDefault().post(ShowProgress())

    }

    fun hideProgress() {
        EventBus.getDefault().post(HideProgress())
        EventBus.getDefault().post(HideRefresher())
    }


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

    fun showPreviousFragment(supportFragmentManager: FragmentManager,fragmentToRemove:Fragment){
            supportFragmentManager.beginTransaction()
                    // Add a tag to prevent duplicating insertions of the same fragment
                    .remove(fragmentToRemove)
                    .addToBackStack(null)
                    .commit()
    }

}