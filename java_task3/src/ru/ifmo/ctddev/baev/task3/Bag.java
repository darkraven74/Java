package ru.ifmo.ctddev.baev.task3;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

public class Bag extends AbstractCollection<Object> {

	private Map<Object, LinkedList<Object>> data;
	private int version, number;

	public Bag() {
		data = new HashMap<Object, LinkedList<Object>>();
		version = 0;
		number = 0;
	}

	public Bag(Collection<?> c) {
		this();
		addAll(c);
	}

	@Override
	public void clear() {
		data.clear();
		number = 0;
		version++;
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public boolean add(Object o) {
		version++;
		number++;
		LinkedList<Object> list = data.get(o);
		if (list == null) {
			list = new LinkedList<Object>();
			data.put(o, list);
		}
		list.add(o);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (!data.containsKey(o)) {
			return false;
		}
		LinkedList<Object> list = data.get(o);
		if (list.size() == 1) {
			data.remove(o);
		} else {
			list.remove(0);
		}
		version++;
		number--;
		return true;
	}

	@Override
	public BagIterator iterator() {
		return new BagIterator();
	}

	@Override
	public int size() {
		return number;
	}

	public class BagIterator implements Iterator<Object> {

		private Iterator<Entry<Object, LinkedList<Object>>> entryIterator;
		private Entry<Object, LinkedList<Object>> entry;
		private int count, iteratorVersion;
		private LinkedList<Object> list;
		private boolean canRemove;

		public BagIterator() {
			entryIterator = data.entrySet().iterator();
			iteratorVersion = version;
		}

		@Override
		public boolean hasNext() {
			if (iteratorVersion != version) {
				throw new ConcurrentModificationException();
			}
			return count > 0 || entryIterator.hasNext();
		}

		@Override
		public Object next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (count == 0) {
				entry = entryIterator.next();
				list = entry.getValue();
				count = list.size();
			}
			count--;
			canRemove = true;
			return list.get(list.size() - count - 1);
		}

		@Override
		public void remove() {
			if (iteratorVersion != version) {
				throw new ConcurrentModificationException();
			}
			if (!canRemove) {
				throw new IllegalStateException();
			}
			if (list.size() == 1) {
				entryIterator.remove();
			} else {
				list.remove(list.size() - count - 1);
				data.put(entry.getKey(), list);
			}
			iteratorVersion++;
			version++;
			number--;
			canRemove = false;
		}

	}
}
