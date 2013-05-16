package ru.ifmo.ctddev.baev.task6;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class for testing {@link MatrixMultiplication}
 * 
 * @author Vladimir Baev
 * 
 */
public class Test {

	/**
	 * Main method of {@link Test}
	 * 
	 * @param args
	 *            command line arguments.
	 * @throws IOException
	 *             in cause of I/O problems.
	 */
	public static void main(String[] args) throws IOException {

		PrintWriter out = new PrintWriter(new File("out.txt"));
		String[] argsForMatrixMultiplication = { "50", "1" };
		for (int threads = 1; threads < 17; threads++) {
			argsForMatrixMultiplication[1] = String.valueOf(threads);
			double sumTime = 0;
			for (int i = 0; i < 50; i++) {
				try {
					MatrixMultiplication.main(argsForMatrixMultiplication);
				} catch (InterruptedException e) {
					System.out.println("Error! Thread was interrupted");
					e.printStackTrace();
				}
				sumTime += MatrixMultiplication.getWorkTime();
			}
			out.println(sumTime / 50);
		}
		out.close();
	}
}
