package com.krystiankowalik.debtmanager.transactionapi.model.transaction

import kryx07.expensereconcilerclient.model.transaction.split.Split
import java.time.LocalDate

data class TransactionRequest(val publicId: String = "",
                              val date: LocalDate,
                              val split: Split)