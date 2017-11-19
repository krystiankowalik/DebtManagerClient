package kryx07.expensereconcilerclient.events

import android.os.Bundle
import android.support.v4.app.Fragment

class ReplaceFragmentEvent(
        val fragment: Fragment,
        val fragmentTag: String,
        val bundle: Bundle?

) {


    constructor(fragment: Fragment) : this(fragment, fragment.javaClass.toString(), null)
    constructor(fragment: Fragment, fragmentTag: String) : this(fragment, fragment.javaClass.toString(), null)
    constructor(fragment: Fragment, bundle: Bundle?) : this(fragment, fragment.javaClass.toString(), bundle)


}






