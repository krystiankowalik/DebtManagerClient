package kryx07.expensereconcilerclient.model.money

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.math.BigDecimal

@Parcelize
data class Money(val amount: BigDecimal,
            val currency: Currency) : Parcelable, Serializable {
}