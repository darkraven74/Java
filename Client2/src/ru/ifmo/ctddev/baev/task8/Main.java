package ru.ifmo.ctddev.baev.task8;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Main class for testing.
 * 
 * @author DarkRaven
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
		Executor exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, 
				new LinkedBlockingQueue<Runnable>());
		

	}
}
