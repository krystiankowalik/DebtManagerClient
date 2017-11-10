package kryx07.expensereconcilerclient.events

import android.support.v4.app.Fragment

class ReplaceFragmentEvent(
        val fragment: Fragment,
        val tag: String
)