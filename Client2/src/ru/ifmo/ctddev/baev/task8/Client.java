package ru.ifmo.ctddev.baev.task8;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Generates and runs tasks on {@link ExecutorService}.
 * 
 * @author Vladimir Baev
 * 
 */
public class Client {
	/**
	 * Generates random {@link String}.
	 * 
	 * @return generated {@link String}.
	 */
	private String nextString() {
		Random rand = new Random();
		int ansLength = rand.nextInt(20);
		char[] ans = new char[ansLength];
		for (int i = 0; i < ansLength; i++) {
			ans[i] = (char) ('a' + rand.nextInt(26));
		}
		return new String(ans);
	}

	/**
	 * Generates and runs tasks on {@link ExecutorService}.
	 * 
	 * @param exec
	 *            {@link ExecutorService} for tasks.
	 */
	public void runTasks(ExecutorService exec) {
		while (true) {
			final Task<String, String> task = new TaskImpl<String, String>();
			final String value = nextString();
			Future<String> future = exec.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					return task.run(value);
				}
			});

			try {
				String result = future.get();
				System.out.println(result);
			} catch (InterruptedException e) {
				System.out.println("thread was interrupted");
				e.printStackTrace();
				return;
			} catch (ExecutionException e) {
				System.out.println("computation threw an exception");
				e.printStackTrace();
			}
		}
	}
}
