package kryx07.expensereconcilerclient.model.transaction.split

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.math.BigDecimal

@Parcelize
data class UserAmounts(val userId: String,
                  val exchanged: BigDecimal,
                  val share: BigDecimal) : Parcelable, Serializable {


}

