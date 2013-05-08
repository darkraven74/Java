package ru.ifmo.ctddev.baev.task6;

import java.util.Random;

/**
 * Class for multi-threaded matrix multiplication.
 * 
 * @author Vladimir Baev
 * 
 */
public class MatrixMultiplication {
	/** number of matrices. */
	private static int n;
	/** number of threads. */
	private static int m;
	/** matrix a. */
	private static int[][] matrixA;
	/** matrix b. */
	private static int[][] matrixB;
	
	/**
	 * Initialization of 2 matrices with random numbers.
	 */
	private static void matrixInit() {
		Random random = new Random();
		matrixA = new int[n][n];
		matrixB = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixA[i][j] = random.nextInt();
				matrixB[i][j] = random.nextInt();
			}
		}
	}
	

	/**
	 * Main method of {@link MatrixMultiplication}
	 * 
	 * @param args
	 *            arguments of command line (number of matrices and threads).
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Enter 2 arguments");
		}
		try {
			n = Integer.valueOf(args[0]);
			m = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Enter 2 integers");
		}
		
		matrixInit();
		
		
		

	}

}
