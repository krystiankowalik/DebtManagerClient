package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import kryx07.expensereconcilerclient.model.transactions.Transaction

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun getAll(): Flowable<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactions: List<Transaction>)
}
