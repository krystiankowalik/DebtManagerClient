package kryx07.expensereconcilerclient.model.transactions


import android.os.Parcel
import android.os.Parcelable
import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User
import org.joda.time.LocalDate
import java.io.Serializable
import java.math.BigDecimal

//@Entity(tableName = "transactions")
class Transaction(
        var id: Int,
        var amount: BigDecimal,
        var description: String,
        var date: LocalDate,
        var common: Boolean,
        var group: Group,
        var payer: User

) : Serializable, Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readSerializable() as BigDecimal,
            source.readString(),
            source.readSerializable() as LocalDate,
            1 == source.readInt(),
            source.readParcelable<Group>(Group::class.java.classLoader),
            source.readParcelable<User>(User::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeSerializable(amount)
        writeString(description)
        writeSerializable(date)
        writeInt((if (common) 1 else 0))
        writeParcelable(group, 0)
        writeParcelable(payer, 0)
    }

    override fun toString(): String {
        return "Transaction(id=$id, amount=$amount, description='$description', date=$date, common=$common, group=$group, payer=$payer)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Transaction> = object : Parcelable.Creator<Transaction> {
            override fun createFromParcel(source: Parcel): Transaction = Transaction(source)
            override fun newArray(size: Int): Array<Transaction?> = arrayOfNulls(size)
        }
    }


}