package ru.ifmo.ctddev.baev.task3;

import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		//LinkedBag<Integer> t = new LinkedBag<Integer>();
		Bag<Integer> t = new Bag<Integer>();

		t.add(1);
		t.add(2);
		t.add(3);
		t.add(1);
		t.add(2);
		t.add(4);
		t.add(4);
		
		Iterator<Integer> i = t.iterator();
		
		while (i.hasNext()) {
			System.out.print(i.next().toString() + " ");
		}
	}
}
