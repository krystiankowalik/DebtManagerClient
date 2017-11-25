package kryx07.expensereconcilerclient.ui.transactions.detail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import kryx07.expensereconcilerclient.events.UpdateDateEvent
import org.greenrobot.eventbus.EventBus
import org.joda.time.LocalDate


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {


    var initialDate: LocalDate = LocalDate.now()
        set(value) {
            field = value.withMonthOfYear(value.monthOfYear - 1)
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val year = initialDate.year
        val month = initialDate.monthOfYear
        val day = initialDate.dayOfMonth

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        EventBus.getDefault().postSticky(UpdateDateEvent(LocalDate(year, month + 1, dayOfMonth)))
    }
}
