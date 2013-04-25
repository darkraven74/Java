package ru.ifmo.ctddev.baev.task1;

public class WrongFileDataMatrixException extends Exception {
	private static final long serialVersionUID = -349617047759288842L;

	public WrongFileDataMatrixException(String message) {
		super(message);
	}

	public WrongFileDataMatrixException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongFileDataMatrixException() {
		super();
	}
}
