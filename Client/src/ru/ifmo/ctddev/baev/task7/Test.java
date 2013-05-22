package ru.ifmo.ctddev.baev.task7;

import java.util.Random;

public class Test {
	
	
	public static void main(String[] args) {
		Random r1 = new Random();
		System.out.println(r1.nextInt(100));
		System.out.println(r1.nextInt(100));
		
		Random r2 = new Random();
		System.out.println(r2.nextInt(100));
		System.out.println(r2.nextInt(100));
	}
}
