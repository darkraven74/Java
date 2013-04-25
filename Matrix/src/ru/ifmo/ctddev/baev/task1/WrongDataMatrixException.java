package ru.ifmo.ctddev.baev.task1;

public class WrongDataMatrixException extends RuntimeException {
	private static final long serialVersionUID = -349617047259288842L;

	public WrongDataMatrixException(String message) {
		super(message);
	}

	public WrongDataMatrixException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongDataMatrixException() {
		super();
	}
}
