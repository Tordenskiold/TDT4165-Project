import exceptions._

sealed trait AccountError
case class NegativeAmount(desc: String)    extends AccountError
case class InsufficientFunds(desc: String) extends AccountError

class Account(val bank: Bank, initialBalance: Double) {

  class Balance(var amount: Double) {}

  val balance = new Balance(initialBalance)

  // TODO
  // for project task 1.2: implement functions
  // for project task 1.3: change return type and update function bodies
  def withdraw(amount: Double): Either[Double, AccountError] =
    amount match {
      case i if i < 0.0 => Right(NegativeAmount(s"Can't withdraw negative amount [$amount]"))
      case i if i > getBalanceAmount =>
        Right(InsufficientFunds("Can't withdraw more than balance [$amount]"))
      case _ => {
        this.synchronized {
          val newBalanceAmount = getBalanceAmount - amount
          balance.amount = newBalanceAmount
          Left(newBalanceAmount)
        }
      }
    }

  def deposit(amount: Double): Either[Double, NegativeAmount] =
    amount match {
      case i if i < 0.0 => Right(NegativeAmount("Can't deposit negative amount [$amount]"))
      case _ => {
        this.synchronized {
          val newBalanceAmount = getBalanceAmount + amount
          balance.amount = newBalanceAmount
          Left(newBalanceAmount)
        }
      }
    }

  def getBalanceAmount: Double = balance.amount

  def transferTo(account: Account, amount: Double) =
    bank.addTransactionToQueue(this, account, amount)
}
