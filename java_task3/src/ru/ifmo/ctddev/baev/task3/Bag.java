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

public class Bag<E> extends AbstractCollection<E> {

	private Map<E, LinkedList<E>> data;
	private int version, size;

	public Bag() {
		data = new HashMap<E, LinkedList<E>>();
		version = 0;
		size = 0;
	}

	public Bag(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	@Override
	public void clear() {
		data.clear();
		size = 0;
		version++;
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public boolean add(E o) {
		version++;
		size++;
		LinkedList<E> list = data.get(o);
		if (list == null) {
			list = new LinkedList<E>();
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
		LinkedList<E> list = data.get(o);
		if (list.size() == 1) {
			data.remove(o);
		} else {
			list.remove(0);
		}
		version++;
		size--;
		return true;
	}

	@Override
	public BagIterator iterator() {
		return new BagIterator();
	}

	@Override
	public int size() {
		return size;
	}

	public class BagIterator implements Iterator<E> {

		private Iterator<Entry<E, LinkedList<E>>> entryIterator;
		private Entry<E, LinkedList<E>> entry;
		private int count, iteratorVersion;
		private LinkedList<E> list;
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
		public E next() {
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
			}
			iteratorVersion++;
			version++;
			size--;
			canRemove = false;
		}
	}
}
