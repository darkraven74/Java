package ru.ifmo.ctddev.baev.task8;

/**
 * Class for making clients in multiple threads.
 * 
 * @author Vladimir Baev
 * 
 */
public class ClientMaker implements Runnable {
	/** TaskRunner for client. */
	private TaskRunner taskRunner;

	/**
	 * Constructor for {@link ClientMaker}.
	 * 
	 * @param taskRunner
	 *            {@link TaskRunner} for client.
	 */
	public ClientMaker(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}

	@Override
	public void run() {
		Client client = new Client();
		client.runTasks(taskRunner);
	}
}