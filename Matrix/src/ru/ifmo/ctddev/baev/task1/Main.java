package ru.ifmo.ctddev.baev.task1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException, WrongFileDataMatrixException {
		// int[][] a = { { 1, 2 }, { 3, 4 } };
		// int[][] b = { { 5, 6 }, { 7, 8 } };
		// Matrix m1 = new Matrix(a);
		// Matrix m2 = new Matrix(b);
		// Matrix m3 = m1.multiply(m1.transpose()).add(m2);
		// m3.writeToConsole();

		if (args.length != 2) {
			throw new WrongCommandLineArgumentException(
					"2 arguments expected, you entered " + args.length);
		}
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		if (!inputFile.exists()) {
			throw new WrongCommandLineArgumentException(
					"Input file not found (" + args[0] + ").");
		}
		Scanner input = new Scanner(inputFile);

		Matrix m1 = Matrix.createMatrix(input);
		Matrix m2 = Matrix.createMatrix(input);
		Matrix m3 = Matrix.createMatrix(input);
		
		
		Matrix ans = m1.multiply(m1).add(m2.multiply(m3));
		ans.write(outputFile);

		input.close();
	}
}
