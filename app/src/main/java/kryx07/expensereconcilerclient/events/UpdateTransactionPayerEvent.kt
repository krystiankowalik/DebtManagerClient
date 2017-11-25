package kryx07.expensereconcilerclient.events

import kryx07.expensereconcilerclient.model.users.User

class UpdateTransactionPayerEvent(val payer: User) {
}