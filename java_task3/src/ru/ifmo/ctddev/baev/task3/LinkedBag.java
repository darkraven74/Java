package ru.ifmo.ctddev.baev.task3;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedBag extends AbstractCollection<Object> {

	private HashMap<Object, LinkedList<Node>> data;
	private int number;
	private int version;
	private Node begin;
	private Node end;

	private class Node {
		public Object val;
		public Node next;
		public Node prev;
		public int id;

		public Node(Object val, int id, Node prev) {
			this.val = val;
			this.id = id;
			this.prev = prev;
			this.next = null;
		}
	}

	public LinkedBag() {
		data = new HashMap<Object, LinkedList<Node>>();
		number = 0;
		version = 0;
		begin = new Node(null, -1, null);
		end = begin;
	}

	public LinkedBag(Collection<?> c) {
		data = new HashMap<Object, LinkedList<Node>>();
		number = 0;
		version = 0;
		begin = new Node(null, -1, null);
		end = begin;
		Iterator<?> i = c.iterator();
		Object cur;
		while (i.hasNext()) {
			cur = i.next();
			this.add(cur);
		}
	}

	@Override
	public void clear() {
		data.clear();
		version++;
		number = 0;
		begin = new Node(null, -1, null);
		end = begin;
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		Object cur;
		while (i.hasNext()) {
			cur = i.next();
			if (!this.contains(cur)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean add(Object o) {
		number++;
		version++;
		int id = 0;
		if (data.containsKey(o)) {
			id = data.get(o).size();
		}
		Node node = new Node(o, id, end);
		end.next = node;
		end = node;
		LinkedList<Node> list;
		if (id > 0) {
			list = data.get(o);
			list.add(node);
		} else {
			list = new LinkedList<>();
			list.add(node);
		}
		data.put(o, list);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (!data.containsKey(o)) {
			return false;
		}
		number--;
		version++;
		LinkedList<Node> list = data.get(o);
		Node node = list.get(list.size() - 1);
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		node.prev.next = node.next;
		if (list.size() == 1) {
			data.remove(o);
		} else {
			list.remove(list.size() - 1);
			data.put(o, list);
		}

		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		Object cur;
		boolean isChanged = false;
		while (i.hasNext()) {
			cur = i.next();
			if (this.remove(cur)) {
				isChanged = true;
			}
		}
		return isChanged;
	}

	@Override
	public LinkedBagIterator iterator() {
		return new LinkedBagIterator();
	}

	@Override
	public int size() {
		return number;
	}

	public class LinkedBagIterator implements Iterator<Object> {

		private boolean canRemove;
		private Node cur;
		private int iteratorVersion;

		public LinkedBagIterator() {
			cur = begin;
			iteratorVersion = version;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if (cur.next != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object next() {
			if (iteratorVersion != version) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			canRemove = true;
			cur = cur.next;
			return cur.val;
		}

		@Override
		public void remove() {
			if (iteratorVersion != version) {
				throw new ConcurrentModificationException();
			}
			if (!canRemove) {
				throw new IllegalStateException();
			}
			iteratorVersion++;
			version++;
			number--;
			if (cur.next != null) {
				cur.next.prev = cur.prev;
			}
			cur.prev.next = cur.next;
			LinkedList<Node> list = data.get(cur.val);
			list.set(cur.id, list.get(list.size() - 1));
			list.get(cur.id).id = cur.id;
			list.remove(list.size() - 1);
			if (list.isEmpty()) {
				data.remove(cur.val);
			} else {
				data.put(cur.val, list);
			}
			cur = cur.prev;
		}

	}

}
