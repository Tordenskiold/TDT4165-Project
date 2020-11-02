
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
		return sum
	}


	// Task 1c
	def addNumbersRecursive(numbers: Array[Int], index: Int = 0, sum: Int = 0): Int = {
		if (index >= numbers.size)
			return sum
		else
			return addNumbersRecursive(numbers, index + 1, sum + numbers(index))
	}


	// Task 1d
	/*
	The difference between BigInt and Int is that Int is limited to a maximum value of (2^31 - 1)
	and a minimum value of (- 2^31), while BigInt isn't limited to neither a maximum nor a minimum
	value (only limited by the system's memory).
	*/
	def fibonacci(n: Int, a: BigInt = 0, b: BigInt = 1): BigInt = {
		if (n == 1)
			return a
		else
			return fibonacci(n - 1, b, a + b)
	}


	// Task 2a
	def embedInThread(inputFunction: () => Unit): Thread = {
		return new Thread(new Runnable {
			def run() {
				inputFunction()
			}
		})
	}


	// Task 2bc
	private var counter: Int = 0
	def increaseCounter(): Unit = {
		this.synchronized {
			counter += 1
		}
	}

	def printCounter(): Unit = {
		println(counter)
	}


	// Task 2d
	/*
	A deadlock is used to describe a situation where a program cannot proceed because parts of the
	program have circular dependencies, meaning a part of the program needs the result from another
	part of the program, which in turn needs the result from another part of the program, and at
	some point in this chain of dependencies, the first part of the program mentioned is a
	dependency. The phenomenon we just described is called a circular dependency. Avoiding circular
	dependencies is key to avoiding deadlocks.
	The code below causes a deadlock if either ObjectA.valueA or ObjectB.valueB is called. This is
	because calling ObjectA.valueA will result in a call to ObjectB.valueB, which in turn will
	result in a call to ObjectA.valueA, and we're back to the starting point, in a deadlock.
	*/
	object ObjectA {
		lazy val valueA: Int = ObjectB.valueB
	}
	object ObjectB {
		lazy val valueB: Int = ObjectA.valueA
	}


}


