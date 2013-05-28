package ru.ifmo.ctddev.baev.task8;

/**
 * Interface of task.
 * 
 * @author Vladimir Baev
 * 
 * @param <X>
 *            the type of output value.
 * @param <Y>
 *            the type of input value.
 */
public interface Task<X, Y> {
	/**
	 * Runs the task.
	 * 
	 * @param value
	 *            input value
	 * @return output value.
	 */
	X run(Y value);
}
