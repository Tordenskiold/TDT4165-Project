import TransactionStatus._

class Bank(val allowedAttempts: Integer = 3) {

  private val transactionsQueue: TransactionQueue     = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()

  // TODO
  // project task 2
  // create a new transaction object and put it in the queue
  // spawn a thread that calls processTransactions
  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    transactionsQueue.push {
      new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
    }
    Main.thread {
      processTransactions
    }
  }

  // TODO
  // project task 2
  // Function that pops a transaction from the queue
  // and spawns a thread to execute the transaction.
  // Finally do the appropriate thing, depending on whether
  // the transaction succeeded or not
  private def processTransactions: Unit = {
    val transaction = transactionsQueue.pop

    val th = new Thread(transaction)
    th.start
    th.join
    transaction.status match {
      case FAILED | SUCCESS => processedTransactions.push(transaction)
      case PENDING => {
        transactionsQueue.push(transaction)
        processTransactions
      }
    }
  }

  def addAccount(initialBalance: Double): Account =
    new Account(this, initialBalance)

  def getProcessedTransactionsAsList: List[Transaction] =
    processedTransactions.iterator.toList

}
