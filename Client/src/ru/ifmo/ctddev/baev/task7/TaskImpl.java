package ru.ifmo.ctddev.baev.task7;

/**
 * Task implementation.
 * 
 * @author Vladimir Baev
 * 
 * @param <X>
 *            the type of output value.
 * @param <Y>
 *            the type of input value.
 */
public class TaskImpl<X, Y> implements Task<X, Y> {

	@SuppressWarnings("unchecked")
	@Override
	public X run(Y value) {
		return (X)((String)value).concat("!");
	}

}
