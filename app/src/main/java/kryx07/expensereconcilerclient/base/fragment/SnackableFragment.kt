package kryx07.expensereconcilerclient.base.fragment

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import timber.log.Timber

abstract class SnackableFragment : Fragment(), Snackable {

    override fun showSnack(text: String) {
        Snackbar.make(view!!, text, Snackbar.LENGTH_LONG).show()
    }

    override fun showSnack(text: Int) {
        showSnack(view!!.context.getString(text))
    }

    override fun showSnackAndLog(text: String) {
        Timber.e(text)
        showSnack(text)
    }

    override fun showSnackAndLog(text: Int) {
        showSnackAndLog(view!!.context.getString(text))
    }

}