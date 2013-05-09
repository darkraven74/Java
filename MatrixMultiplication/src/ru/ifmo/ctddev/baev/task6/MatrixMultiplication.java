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
	/** matrix anwser. */
	private static int[][] matrixAnwser;
	/** time of calculating. */
	private static double workTime;

	public static double getWorkTime() {
		return workTime;
	}

	/**
	 * Initializes 2 matrices with random numbers.
	 */
	private static void matrixInit() {
		Random random = new Random();
		matrixA = new int[n][n];
		matrixB = new int[n][n];
		matrixAnwser = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixA[i][j] = random.nextInt();
				matrixB[i][j] = random.nextInt();
			}
		}
	}

	/**
	 * Calculates <code>i</code>, <code>j</code> cell of {@link #matrixAnwser}.
	 * 
	 * @param i
	 *            index of row.
	 * @param j
	 *            index of column.
	 */
	private static void calculateCell(int i, int j) {
		int anwser = 0;
		for (int k = 0; k < n; k++) {
			anwser += matrixA[i][k] * matrixB[k][j];
		}
		matrixAnwser[i][j] = anwser;
	}

	/**
	 * Calculates sum of elements in {@link #matrixAnwser}.
	 * 
	 * @return Sum of elements in {@link #matrixAnwser}.
	 */
	private static int calculateSum() {
		int anwser = 0;
		for (int[] currentRow : matrixAnwser) {
			for (int currentCell : currentRow) {
				anwser += currentCell;
			}
		}
		return anwser;
	}

	/**
	 * Class MyRunnable calculates data in thread.
	 * 
	 * @author Vladimir Baev
	 * 
	 */
	private static class MyRunnable implements Runnable {
		/** start position to calculating. */
		private int curPos;
		/** number of cells in thread. */
		private int cellsInThread;

		/**
		 * 
		 * @param curPos
		 *            start position to calculating.
		 * @param cellsInThread
		 *            number of cells in thread.
		 */
		public MyRunnable(int curPos, int cellsInThread) {
			this.curPos = curPos;
			this.cellsInThread = cellsInThread;
		}

		@Override
		public void run() {
			for (int i = 0; i < cellsInThread; i++) {
				calculateCell(curPos / n, curPos % n);
				curPos++;
			}
		}
	}

	/**
	 * Main method of {@link MatrixMultiplication}
	 * 
	 * @param args
	 *            arguments of command line (number of matrices and threads).
	 * 
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
		if (n <= 0 || m <= 0) {
			throw new IllegalArgumentException("Enter 2 positive integers");
		}

		matrixInit();

		int cellsInThread = (int) Math.floor(1.0 * n * n / m);
		int cellsInLastThread = n * n - cellsInThread * (m - 1);
		int curPos = 0;
		Thread[] threads = new Thread[m];

		long startTime = System.nanoTime();

		for (int i = 0; i < m - 1; i++) {
			threads[i] = new Thread(new MyRunnable(curPos, cellsInThread));
			threads[i].start();
			curPos += cellsInThread;
		}
		threads[m - 1] = new Thread(new MyRunnable(curPos, cellsInLastThread));
		threads[m - 1].start();

		for (int i = 0; i < m; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		workTime = (System.nanoTime() - startTime) / 1e6;
		System.out.println("Time: " + workTime);
		System.out.println("Sum: " + calculateSum());
	}
}
