package kryx07.expensereconcilerclient.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import kryx07.expensereconcilerclient.model.transactions.Payable
import kryx07.expensereconcilerclient.model.transactions.Transaction
import kryx07.expensereconcilerclient.model.users.Group
import kryx07.expensereconcilerclient.model.users.User

@Database(entities = arrayOf(Transaction::class, Payable::class, User::class, Group::class), version = 11)
@TypeConverters(*arrayOf(Converters::class))
abstract class MyDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    abstract fun payablesDao(): PayablesDao
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
}

