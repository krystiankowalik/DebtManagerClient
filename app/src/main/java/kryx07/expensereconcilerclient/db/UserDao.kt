package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.model.users.User

/**
 * Created by wd43 on 11/5/17.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

}