package ru.ifmo.ctddev.baev.task7;

/**
 * Class for testing {@link Client}.
 * 
 * @author Vladimir Baev
 * 
 */
public class Test {

	/**
	 * Main method of {@link Test}.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		int threadNumber = 2;
		TaskRunner runner1 = new TaskRunnerImpl(threadNumber);
		Client client = new Client();
		client.runTasks(runner1);
		// Thread t1 = new Thread(new ClientMaker(runner1));
		// Thread t2 = new Thread(new ClientMaker(runner1));
		// t1.start();
		// t2.start();
	}
}
