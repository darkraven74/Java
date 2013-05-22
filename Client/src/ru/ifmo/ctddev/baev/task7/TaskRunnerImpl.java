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

	/** queue of tasks. */
	private BlockingQueue<Element<?, ?>> queue;
	/** number of working threads. */
	private int threadNumber;

	/**
	 * Constructor for {@link TaskRunnerImpl}
	 * 
	 * @param threadNumber
	 *            number of working threads.
	 */
	public TaskRunnerImpl(int threadNumber) {
		this.threadNumber = threadNumber;
		queue = new LinkedBlockingQueue<Element<?, ?>>();
	}

	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) throws InterruptedException {
		Element<X, Y> e = new Element<X, Y>(task, value);
		queue.add(e);
		e.wait();
		return e.result;
	}

}
