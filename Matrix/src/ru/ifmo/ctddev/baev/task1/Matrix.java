package ru.ifmo.ctddev.baev.task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Matrix {
	private int[][] data;
	private int height, width;

	private Matrix(int height, int width) {
		this.height = height;
		this.width = width;
		data = new int[height][width];
	}

	public Matrix(int[][] data) {
		checkSizeOnCreatingMatrix(data);
		height = data.length;
		width = data[0].length;
		this.data = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	public static Matrix createMatrix(Scanner input)
			throws WrongFileDataMatrixException {
		try {
			int height = input.nextInt();
			int width = input.nextInt();
			if ((height <= 0) || (width <= 0)) {
				throw new WrongFileDataMatrixException(
						"Wrong size on matrix constructor: "
								+ "height and width should be above zero, and you used "
								+ height + " and " + width);
			}
			Matrix ans = new Matrix(height, width);
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					ans.data[i][j] = input.nextInt();
				}
			}
			return ans;
		} catch (NoSuchElementException e) {
			throw new WrongFileDataMatrixException(
					"Wrong data on matrix constructor: not enough integers.");
		}
	}

	public Matrix(File inputFile) throws WrongFileDataMatrixException,
			FileNotFoundException {
		Scanner scanner = new Scanner(inputFile);
		Matrix temp;
		try {
			temp = Matrix.createMatrix(scanner);
		} finally {
			scanner.close();
		}
		this.data = temp.data;
		this.height = temp.height;
		this.width = temp.width;
	}

	public int get(int row, int column) {
		checkIndexOnGetElement(row, column);
		return data[row - 1][column - 1];
	}

	public void set(int row, int column, int source) {
		checkIndexOnSetElement(row, column);
		data[row - 1][column - 1] = source;
	}

	public Matrix scale(int scale) {
		Matrix result = new Matrix(data);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.data[i][j] *= scale;
			}
		}
		return result;
	}

	public Matrix add(Matrix source) {
		checkSizeOnAdditionAndSubtraction(source);
		Matrix result = new Matrix(data);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.data[i][j] += source.get(i + 1, j + 1);
			}
		}
		return result;
	}

	public Matrix subtract(Matrix source) {
		checkSizeOnAdditionAndSubtraction(source);
		Matrix result = new Matrix(data);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.data[i][j] -= source.get(i + 1, j + 1);
			}
		}
		return result;
	}

	public Matrix multiply(Matrix source) {
		checkSizeOnMultiplication(source);
		Matrix result = new Matrix(height, source.width);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < source.width; k++) {
				int temp = 0;
				for (int j = 0; j < width; j++) {
					temp += data[i][j] * source.data[j][k];
				}
				result.data[i][k] = temp;
			}
		}
		return result;
	}

	public Matrix transpose() {
		Matrix result = new Matrix(width, height);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.data[j][i] = data[i][j];
			}
		}
		return result;
	}

	public void writeToConsole() {
		System.out.println(height + "x" + width);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void write(File outputFile) throws IOException {
		PrintWriter output = new PrintWriter(outputFile);
		output.println(height + " " + width);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				output.print(data[i][j] + " ");
			}
			output.println();
		}
		
		output.close();
		if (output.checkError()) {
			throw new IOException("Error while writing to file");
		}
	}

	private void checkSizeOnCreatingMatrix(int[][] data) {
		if (data == null) {
			throw new NullPointerException(
					"Wrong argument in matrix constructor.");
		}
		if (data.length == 0) {
			throw new WrongDataMatrixException(
					"Wrong length of array in matrix constructor (it should be larger than zero).");
		}
		int width = data[0].length;
		if (width == 0) {
			throw new WrongDataMatrixException(
					"Wrong length of row in matrix constructor.");
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i].length != width) {
				throw new WrongDataMatrixException(
						"Wrong length of row in matrix constructor.");
			}
		}
	}

	private void checkIndexOnGetElement(int row, int column) {
		if ((row > height) || (column > width) || (row < 1) || (column < 1)) {
			throw new WrongArgumentMatrixException(
					"Error in getting element: row should be in 1.." + height
							+ ", column should be in 1.." + width
							+ ", and you use " + row + " and " + column + ".");
		}
	}

	private void checkIndexOnSetElement(int row, int column) {
		if ((row > height) || (column > width) || (row < 1) || (column < 1)) {
			throw new WrongArgumentMatrixException(
					"Error in setting element: row should be in 1.." + height
							+ ", column should be in 1.." + width
							+ ", and you use " + row + " and " + column + ".");
		}
	}

	private void checkSizeOnAdditionAndSubtraction(Matrix source) {
		if ((height != source.height) || (width != source.width)) {
			throw new WrongArgumentMatrixException(
					"Error in addition: height should be " + height
							+ ", width should be " + width + ", and you use "
							+ source.height + " and " + source.width + ".");
		}
	}

	private void checkSizeOnMultiplication(Matrix source) {
		if (width != source.height) {
			throw new WrongArgumentMatrixException(
					"Error in multiplication: height should be " + width
							+ ", and you use " + source.height + ".");
		}
	}

}
