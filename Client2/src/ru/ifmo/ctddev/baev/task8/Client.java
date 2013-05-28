package ru.ifmo.ctddev.baev.task8;

import java.util.Random;

/**
 * Generates and runs tasks on {@link TaskRunner}.
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
	 * Generates and runs tasks on {@link TaskRunner}.
	 * 
	 * @param taskRunner
	 *            {@link TaskRunner} for tasks.
	 */
	public void runTasks() {
		while (true) {
			Task<String, String> task = new TaskImpl<String, String>();
			String value = nextString();
			//String result = taskRunner.run(task, value);
			//System.out.println(result);
			// bad if interrupt
		}
	}
}
