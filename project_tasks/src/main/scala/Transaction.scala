import scala.collection.mutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

  // TODO
  // project task 1.1
  // Add datastructure to contain the transactions
  private val transactionQueue = mutable.Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop: Transaction = this synchronized transactionQueue.dequeue

  // Return whether the queue is empty
  def isEmpty: Boolean = this synchronized transactionQueue.isEmpty

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = this synchronized transactionQueue.enqueue(t)

  // Return the first element from the queue without removing it
  def peek: Transaction = this synchronized transactionQueue.front

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = this synchronized transactionQueue.iterator
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING

  var attempt = 0

  override def run: Unit = {

    def doTransaction(): Either[Double, AccountError] = {
      // TODO - project task 3
      // Extend this method to satisfy requirements.
      val withdrawStatus = from.withdraw(amount)
      if (withdrawStatus.isRight) return withdrawStatus
      to.deposit(amount)
    }

    // TODO - project task 3
    // make the code below thread safe
    if (status == TransactionStatus.PENDING) {
      from.synchronized {
        to.synchronized {
          status = doTransaction() match {
            case Left(_)                  => TransactionStatus.SUCCESS
            case Right(IllegalAmount)     => TransactionStatus.FAILED
            case Right(NoSufficientFunds) => {
              attempt += 1
              if (attempt >= allowedAttempts) TransactionStatus.FAILED
              else TransactionStatus.PENDING
            }
          }
        }
      }
    }
    Thread.sleep(50) // you might want this to make more room for
    // new transactions to be added to the queue
  }
}
