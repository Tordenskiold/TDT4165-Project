import exceptions._

import scala.collection.mutable
import scala.collection.immutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

  // TODO
  // project task 1.1
  // Add datastructure to contain the transactions
  val transactionQueue = mutable.Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop: Transaction = transactionQueue.dequeue

  // Return whether the queue is empty
  def isEmpty: Boolean = transactionQueue.isEmpty

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = transactionQueue.enqueue(t)

  // Return the first element from the queue without removing it
  def peek: Transaction = transactionQueue.last

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = transactionQueue.iterator
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int)
    extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING

  var attempt = 0

  override def run: Unit = {

    def doTransaction(): Either[Double, String] = {
      // TODO - project task 3
      // Extend this method to satisfy requirements.
      val withdrawStatus = from.withdraw(amount)
      if (withdrawStatus.isRight) return withdrawStatus
      to.deposit(amount)
    }

    // TODO - project task 3
    // make the code below thread safe
    if (status == TransactionStatus.PENDING) {
      this.synchronized {
        val transactionOut = doTransaction

        if (transactionOut.isLeft) status = TransactionStatus.SUCCESS
        else {
          if (attempt < allowedAttempts) {
            attempt += 1
            status = TransactionStatus.PENDING
          } else status = TransactionStatus.FAILED
        }
      }
      Thread.sleep(50) // you might want this to make more room for
      // new transactions to be added to the queue
    }

  }
}
