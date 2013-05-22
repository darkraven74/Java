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
		/** task to solve. */
		public Task<X, Y> task;
		/** input value. */
		public Y value;
		/** output value. */
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
	}

	/**
	 * Class for solving tasks.
	 * 
	 * @author Vladimir Baev
	 * 
	 */
	private class Worker implements Runnable {

		@Override
		public void run() {
			while (!isStopped) {
				Element e = queue.poll();
				if (e != null) {
					e.result = e.task.run(e.value);
					e.notify();
				} else {
					try {
						isNotEmpty.wait();
					} catch (InterruptedException e1) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	/** queue of tasks. */
	private BlockingQueue<Element> queue;
	/** number of working threads. */
	private int threadNumber;
	/** isStopped flag of {@link TaskRunnerImpl}. */
	private volatile boolean isStopped;
	/** isEmpty flag of {@link TaskRunnerImpl}. */
	private Object isNotEmpty;

	/**
	 * Constructor for {@link TaskRunnerImpl}
	 * 
	 * @param threadNumber
	 *            number of working threads.
	 */
	public TaskRunnerImpl(int threadNumber) {
		this.threadNumber = threadNumber;
		queue = new LinkedBlockingQueue<Element>();
		isStopped = false;
		Thread[] threads = new Thread[threadNumber];
		for (int i = 0; i < threadNumber; i++) {
			threads[i] = new Thread(new Worker());
			threads[i].start();
		}
	}

	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) {
		Element<X, Y> e = new Element<X, Y>(task, value);
		queue.add(e);
		isNotEmpty.notify();

		try {
			e.wait();
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}
		return e.result;
	}

	public void stop() {
		isStopped = true;
	}

}
