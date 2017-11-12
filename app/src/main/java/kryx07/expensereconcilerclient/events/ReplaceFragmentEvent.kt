package kryx07.expensereconcilerclient.events

import android.support.v4.app.Fragment

class ReplaceFragmentEvent(
        val fragment: Fragment,
        var fragmentTag: String
) {
    constructor(fragment: Fragment) : this(fragment, fragment.javaClass.toString())


}






