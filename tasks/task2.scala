object Hello extends App {
	// Task 2a
	def embedInThread(inputFunction: () => Unit): Thread = {
		new Thread(new Runnable {
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
