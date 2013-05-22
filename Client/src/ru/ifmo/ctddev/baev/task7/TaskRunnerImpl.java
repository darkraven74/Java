package ru.ifmo.ctddev.baev.task7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TaskRunner implementation.
 * 
 * @author Vladimir Baev
 * 
 */
public class TaskRunnerImpl implements TaskRunner {

	/**
	 * Element of {@link TaskRunnerImpl#queue}
	 * 
	 * @author Vladimir Baev
	 * 
	 */
	private class Element<X, Y> {
		/** Task to solve. */
		public Task<X, Y> task;
		/** Input value. */
		public Y value;
		/** Output value. */
		public X result;

		/**
		 * Constructor for {@link Element}
		 * 
		 * @param task
		 *            task to solve.
		 * @param value
		 *            input value.
		 */
		public Element(Task<X, Y> task, Y value) {
			this.task = task;
			this.value = value;
			this.result = null;
		}

		/**
		 * Runs the task.
		 */
		public synchronized void run() {
			result = task.run(value);
			this.notify();
		}
	}

	/**
	 * Class for multi-threaded solving tasks.
	 * 
	 * @author Vladimir Baev
	 * 
	 */
	private class Worker implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Element<?, ?> e = queue.take();
					e.run();
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	/** Queue of tasks. */
	private BlockingQueue<Element<?, ?>> queue;

	/**
	 * Constructor for {@link TaskRunnerImpl}
	 * 
	 * @param threadNumber
	 *            number of working threads.
	 */
	public TaskRunnerImpl(int threadNumber) {
		queue = new LinkedBlockingQueue<Element<?, ?>>();
		Thread[] threads = new Thread[threadNumber];
		for (int i = 0; i < threadNumber; i++) {
			threads[i] = new Thread(new Worker());
			threads[i].start();
		}
	}

	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) {
		Element<X, Y> e = new Element<X, Y>(task, value);
		try {
			queue.put(e);
			synchronized (e) {
				while (e.result == null) {
					e.wait();
				}
			}
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}
		return e.result;
	}
}
