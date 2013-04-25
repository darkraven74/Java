package ru.ifmo.ctddev.baev.task1;

public class WrongArgumentMatrixException extends RuntimeException {
	private static final long serialVersionUID = -132958331839642876L;

	public WrongArgumentMatrixException(String message) {
		super(message);
	}

	public WrongArgumentMatrixException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongArgumentMatrixException() {
		super();
	}
}
