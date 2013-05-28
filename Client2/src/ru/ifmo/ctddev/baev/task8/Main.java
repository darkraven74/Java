package ru.ifmo.ctddev.baev.task8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
		int corePoolSize = 2;
		int maximumPoolSize = 5;
		long keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		ExecutorService exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
				new LinkedBlockingQueue<Runnable>());
		Client c1 = new Client();
		c1.runTasks(exec);

		// Thread t1 = new Thread(new ClientMaker(exec));
		// Thread t2 = new Thread(new ClientMaker(exec));
		// t1.start();
		// t2.start();

	}
}
