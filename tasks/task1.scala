object Hello extends App {

  // Task 1a
  var numbers: Array[Int] = new Array[Int](50)
  for (i: Int <- 0 until 50)
    numbers(i) = i + 1


  // Task 1b
  def addNumbersIterative(numbers: Array[Int]): Int = {
    var sum: Int = 0
    for (number: Int <- numbers)
      sum += number
    sum
  }


  // Task 1c
  def addNumbersRecursive(numbers: Array[Int], index: Int = 0, sum: Int = 0): Int = {
    if (index >= numbers.size) sum
    else addNumbersRecursive(numbers, index + 1, sum + numbers(index))
  }


  // Task 1d
  /*
  The difference between BigInt and Int is that Int is limited to a maximum value of (2^31 - 1)
  and a minimum value of (- 2^31), while BigInt isn't limited to neither a maximum nor a minimum
  value (only limited by the system's memory).
  */
  def fibonacci(n: Int, a: BigInt = 0, b: BigInt = 1): BigInt = {
    if (n == 1) a
    else fibonacci(n - 1, b, a + b)
  }
}


