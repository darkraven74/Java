package ru.ifmo.ctddev.baev.task3;

import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		//LinkedBag t = new LinkedBag();
		Bag t = new Bag();

		t.add(1);
		t.add(2);
		t.add(3);
		t.add(1);
		t.add(2);
		t.add(4);
		t.add(4);

		Iterator<Object> i = t.iterator();
		//i.remove();
		while (i.hasNext()) {
			System.out.print(i.next().toString() + " ");
		}

	}
}
