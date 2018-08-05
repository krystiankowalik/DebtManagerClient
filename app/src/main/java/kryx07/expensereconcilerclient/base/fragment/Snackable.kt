package kryx07.expensereconcilerclient.base.fragment

interface Snackable {

    fun showSnack(text: String)

    fun showSnack(text: Int)

    fun showSnackAndLog(text: String)

    fun showSnackAndLog(text: Int)
}