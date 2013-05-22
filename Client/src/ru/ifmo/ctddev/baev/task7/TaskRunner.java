package ru.ifmo.ctddev.baev.task7;

/**
 * Interface of task runner.
 * 
 * @author Vladimir Baev
 * 
 */
public interface TaskRunner {
	/**
	 * Runs task.
	 * 
	 * @param task
	 *            task to be runned.
	 * @param value
	 *            input value.
	 * @return output value.
	 * @throws InterruptedException in case of interrupting.
	 */
	<X, Y> X run(Task<X, Y> task, Y value) throws InterruptedException;
}
