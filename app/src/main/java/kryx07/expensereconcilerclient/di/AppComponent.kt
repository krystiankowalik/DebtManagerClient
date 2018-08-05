package kryx07.expensereconcilerclient.di


//import kryx07.expensereconcilerclient.ui.payables.PayablesFragment
import kryx07.expensereconcilerclient.network.ApiClient
import kryx07.expensereconcilerclient.network.BaseApiClient
import kryx07.expensereconcilerclient.network.TransactionApiClient
import kryx07.expensereconcilerclient.ui.DashboardActivity
import kryx07.expensereconcilerclient.ui.LoginActivity
import kryx07.expensereconcilerclient.ui.group.GroupsFragment
import kryx07.expensereconcilerclient.ui.transactions.master.TransactionSelectionAction
import kryx07.expensereconcilerclient.ui.transactions.master.TransactionsFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.TransactionDetailFragment
import kryx07.expensereconcilerclient.ui.transactions.detail.calculator.CalculatorDialogFragment
import kryx07.expensereconcilerclient.ui.transactions.master.NewTransactionsFragment
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

    fun inject(groupsFragment: GroupsFragment)

    fun inject(transactionSelectionAction: TransactionSelectionAction)

    fun inject(apiClient: ApiClient)

    fun inject(loginActivity: LoginActivity)

//    fun inject(baseApiClient: BaseApiClient)

    fun inject(calculatorDialogFragment: CalculatorDialogFragment)
    fun inject(transactionApiClient: TransactionApiClient)
    fun inject(newTransactionsFragment: NewTransactionsFragment)
    /*void inject(LoginActivity loginActivity);

    void inject(DashboardActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
