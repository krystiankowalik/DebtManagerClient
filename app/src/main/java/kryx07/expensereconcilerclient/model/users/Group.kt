package kryx07.expensereconcilerclient.model.users

import android.os.Parcel
import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Group(
        @Id var id: Long,
        var name: String


) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Group> = object : Parcelable.Creator<Group> {
            override fun createFromParcel(source: Parcel): Group = Group(source)
            override fun newArray(size: Int): Array<Group?> = arrayOfNulls(size)
        }
    }
}