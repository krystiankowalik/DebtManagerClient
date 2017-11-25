package kryx07.expensereconcilerclient.ui.transactions

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import kryx07.expensereconcilerclient.R
import javax.inject.Inject

class TransactionSelectionAction(private val transactionsMvpView: TransactionsMvpView) : ActionMode.Callback {

    override fun onCreateActionMode(mode: android.support.v7.view.ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_select_item, menu)
        transactionsMvpView.onCreateActionMode()
        return true
    }

    override fun onPrepareActionMode(mode: android.support.v7.view.ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: android.support.v7.view.ActionMode, item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_item -> {
                transactionsMvpView.onDeleteActionClicked()

                mode.finish()
                return true
            }

            else -> return false
        }
    }

    override fun onDestroyActionMode(mode: android.support.v7.view.ActionMode) {
        transactionsMvpView.onDestroyActionMode()
    }


}