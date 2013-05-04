package ru.ifmo.ctddev.baev.task3;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedBag<E> extends AbstractCollection<E> {

	private Map<E, List<Node>> data;
	private int size;
	private int version;
	private Node begin;
	private Node end;

	private class Node {
		public E val;
		public Node next;
		public Node prev;
		public int id;

		public Node(E val, int id, Node prev) {
			this.val = val;
			this.id = id;
			this.prev = prev;
			this.next = null;
		}
	}

	public LinkedBag() {
		data = new HashMap<E, List<Node>>();
		size = 0;
		version = 0;
		begin = new Node(null, -1, null);
		end = begin;
	}

	public LinkedBag(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	@Override
	public void clear() {
		data.clear();
		version++;
		size = 0;
		begin = new Node(null, -1, null);
		end = begin;
	}

	@Override
	public boolean contains(Object o) {
		return data.containsKey(o);
	}

	@Override
	public boolean add(E o) {
		size++;
		version++;
		int id = 0;
		if (data.containsKey(o)) {
			id = data.get(o).size();
		}
		Node node = new Node(o, id, end);
		end.next = node;
		end = node;
		List<Node> list = data.get(o);
		if (list == null) {
			list = new ArrayList<Node>();
			data.put(o, list);
		}
		list.add(node);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (!data.containsKey(o)) {
			return false;
		}
		size--;
		version++;
		List<Node> list = data.get(o);
		Node node = list.get(list.size() - 1);
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		node.prev.next = node.next;
		if (list.size() == 1) {
			data.remove(o);
		} else {
			list.remove(list.size() - 1);
		}
		return true;
	}

	@Override
	public LinkedBagIterator iterator() {
		return new LinkedBagIterator();
	}

	@Override
	public int size() {
		return size;
	}

	public class LinkedBagIterator implements Iterator<E> {

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
			return cur.next != null;
		}

		@Override
		public E next() {
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
			size--;
			if (cur.next != null) {
				cur.next.prev = cur.prev;
			}
			cur.prev.next = cur.next;
			List<Node> list = data.get(cur.val);
			list.set(cur.id, list.get(list.size() - 1));
			list.get(cur.id).id = cur.id;
			list.remove(list.size() - 1);
			if (list.isEmpty()) {
				data.remove(cur.val);
			}
			cur = cur.prev;
		}
	}
}
