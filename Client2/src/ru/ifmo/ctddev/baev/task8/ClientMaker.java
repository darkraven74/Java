package ru.ifmo.ctddev.baev.task8;

import java.util.concurrent.ExecutorService;

/**
 * Class for making clients in multiple threads.
 * 
 * @author Vladimir Baev
 * 
 */
public class ClientMaker implements Runnable {
	/** ExecutorService for client. */
	private ExecutorService exec;

	/**
	 * Constructor for {@link ClientMaker}.
	 * 
	 * @param exec
	 *            {@link ExecutorService} for client.
	 */
	public ClientMaker(ExecutorService exec) {
		this.exec = exec;
	}

	@Override
	public void run() {
		Client client = new Client();
		client.runTasks(exec);
	}
}