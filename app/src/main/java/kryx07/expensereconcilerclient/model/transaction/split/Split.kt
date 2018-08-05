package kryx07.expensereconcilerclient.model.transaction.split

import android.os.Parcelable
import com.krystiankowalik.debtmanager.transactionapi.model.due.Due
import kotlinx.android.parcel.Parcelize
import kryx07.expensereconcilerclient.model.money.Currency
import kryx07.expensereconcilerclient.model.money.Money
import kryx07.expensereconcilerclient.model.transaction.Transaction
import java.io.Serializable
import java.math.BigDecimal

@Parcelize
data class Split(val userAmountsList: List<UserAmounts>,
                 val splitCurrency: Currency) : Parcelable, Serializable {

    /*@JsonIgnore
    private val userAmountsMap = userAmountsList.map { it.userId to it }.toMap()

    @JsonIgnore
    val totalAmount = userAmountsMap.map { it.value.exchanged }.fold(BigDecimal.ZERO, BigDecimal::add).roundWithContext()

    @JsonIgnore
    private val totalShare = userAmountsMap.map { it.value.share }.fold(BigDecimal.ZERO, BigDecimal::add).roundWithContext()

    @JsonIgnore
    private val isIncome = totalAmount > BigDecimal.ZERO

    @JsonIgnore
    val transactionType = if (isIncome) Transaction.Type.INCOME else Transaction.Type.EXPENSE

    @JsonIgnore
    private fun UserAmounts.getUserShareRatio(): BigDecimal {
        val ratio = userAmountsList[userAmountsList.indexOf(this)].share.divideWithScale(totalShare)
        return ratio

    }

    @JsonIgnore
    private fun BigDecimal.adjustSign() = this.abs().multiply(transactionType.factor)

    @JsonIgnore
    private fun UserAmounts.getUserShareAsAmount(): BigDecimal =
            (this.getUserShareRatio().multiply(totalAmount)).adjustSign()

    @JsonIgnore
    private fun UserAmounts.getUserDueTotalAmount(): BigDecimal {
        val dueAmount = this.exchanged - this.getUserShareAsAmount()
        return dueAmount
    }

    @JsonIgnore
    private fun getTotalOverpaid(): BigDecimal =
            userAmountsList
                    .filter { it.getUserDueTotalAmount() > BigDecimal.ZERO }
                    .map { it.getUserDueTotalAmount() }
                    .fold(BigDecimal.ZERO, BigDecimal::add)
                    .roundWithContext()

    @JsonIgnore
    val creditors = userAmountsList.filter { it.getUserDueTotalAmount() < BigDecimal.ZERO }
    @JsonIgnore
    val debtors = userAmountsList.filter { it.getUserDueTotalAmount() >= BigDecimal.ZERO }

    @JsonIgnore
    fun calculateDues(transactionId: String): List<Due> {
        val dues = mutableListOf<Due>()
        debtors.forEach { debtor ->
            creditors.forEach { creditor ->
                dues.add(Due(id = generateRandomId(),
                        publicId = generateRandomId(),
                        debtorId = debtor.userId,
                        creditorId = creditor.userId,
                        money = Money(amount = debtor
                                .getUserDueTotalAmount()
                                .multiply(creditor
                                        .getUserDueTotalAmount()
                                        .divideWithScale(getTotalOverpaid()).abs()).roundWithContext(),
                                currency = splitCurrency),
                        settled = false,
                        transactionId = transactionId))
            }
        }
        creditors.forEach { debtor ->
            creditors.forEach { creditor ->
                if (debtor != creditor) {
                    dues.add(Due(id = generateRandomId(),
                            publicId = generateRandomId(),
                            debtorId = debtor.userId,
                            creditorId = creditor.userId,
                            money = Money(amount = debtor
                                    .getUserDueTotalAmount()
                                    .multiply(creditor
                                            .getUserDueTotalAmount()
                                            .divideWithScale(getTotalOverpaid()).abs()).roundWithContext(),
                                    currency = splitCurrency),
                            settled = false,
                            transactionId = transactionId))
                }
            }
        }
        return dues.filter { it.money.amount > BigDecimal.ZERO && it.money.amount.abs() > BigDecimal("0.001") }
    }

    private fun BigDecimal.divideWithScale(other: BigDecimal): BigDecimal = this.divide(other, 30, java.math.RoundingMode.FLOOR)

    private fun BigDecimal.roundWithContext() = this.round(java.math.MathContext(4, java.math.RoundingMode.FLOOR))*/


}

