package ru.ifmo.ctddev.baev.task8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TaskRunner implementation.
 * 
 * @author Vladimir Baev
 * 
 */
public class TaskRunnerImpl implements TaskRunner {

	/** ExecutorService for {@link TaskRunnerImpl} */
	private ExecutorService exec;

	/**
	 * Constructor for {@link TaskRunnerImpl}
	 * 
	 * @param maxThreadNumber
	 *            maximum number of working threads.
	 */
	public TaskRunnerImpl(int maxThreadNumber) {
		int corePoolSize = 1;
		int maximumPoolSize = maxThreadNumber;
		long keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
				new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public <X, Y> X run(final Task<X, Y> task, final Y value) {
		Future<X> future = exec.submit(new Callable<X>() {

			@Override
			public X call() throws Exception {
				return task.run(value);
			}
		});

		X result = null;
		try {
			result = future.get();
		} catch (InterruptedException e) {
			System.out.println("thread was interrupted");
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			System.out.println("computation threw an exception");
			e.printStackTrace();
		}
		return result;
	}
}