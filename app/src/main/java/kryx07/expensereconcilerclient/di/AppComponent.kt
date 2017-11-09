package kryx07.expensereconcilerclient.di


//import kryx07.expensereconcilerclient.ui.payables.PayablesFragment
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.transactions.TransactionsFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import kryx07.expensereconcilerclient.ui.users.UserSearchFragment
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(transactionsFragment: TransactionsFragment)
    //fun inject(payablesFragment: PayablesFragment)
    fun inject(transactionDetailFragment: TransactionDetailFragment)

    fun inject(userSearchFragment: UserSearchFragment)
    /*void inject(LoginActivity loginActivity);

    void inject(DashboardActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
