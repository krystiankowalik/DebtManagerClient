package kryx07.expensereconcilerclient.events

import android.support.v4.app.Fragment

class RemoveFragmentEvent(
        val fragment: Fragment
){
    var fragmentTag: String = fragment.javaClass.toString()



}



