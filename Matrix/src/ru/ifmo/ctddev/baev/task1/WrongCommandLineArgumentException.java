package ru.ifmo.ctddev.baev.task1;

public class WrongCommandLineArgumentException extends RuntimeException {
	private static final long serialVersionUID = -132958731839642876L;

	public WrongCommandLineArgumentException(String message) {
		super(message);
	}

	public WrongCommandLineArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongCommandLineArgumentException() {
		super();
	}
}
