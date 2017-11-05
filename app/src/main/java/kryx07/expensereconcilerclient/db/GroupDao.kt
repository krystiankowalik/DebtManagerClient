package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups")
    fun getAll(): List<Group>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: Group)

}