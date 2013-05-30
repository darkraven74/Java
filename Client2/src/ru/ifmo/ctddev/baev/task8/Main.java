package ru.ifmo.ctddev.baev.task8;

/**
 * Main class for testing.
 * 
 * @author Vladimir Baev
 * 
 */
public class Main {

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		int maxThreadNumber = 4;
		TaskRunner runner = new TaskRunnerImpl(maxThreadNumber);
		Client c1 = new Client();
		c1.runTasks(runner);

		// Thread t1 = new Thread(new ClientMaker(runner));
		// Thread t2 = new Thread(new ClientMaker(runner));
		// t1.start();
		// t2.start();

	}
}
