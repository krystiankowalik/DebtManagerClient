package com.krystiankowalik.debtmanager.transactionapi.model.due

import kryx07.expensereconcilerclient.model.money.Money

//@Document(collection = "due")
data class Due(val id: String="",
               val publicId: String,
               val debtorId: String,
               val creditorId: String,
               val money: Money,
               val settled: Boolean,
               val transactionId: String
) {

}

