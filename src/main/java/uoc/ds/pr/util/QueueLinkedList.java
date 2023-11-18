package uoc.ds.pr.util;

import edu.uoc.ds.traversal.Iterator;

import java.util.NoSuchElementException;

public class QueueLinkedList<E> implements edu.uoc.ds.adt.sequential.Queue<E> {
    private class Node {
        E data;
        Node next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public QueueLinkedList() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    @Override
    public void add(E item) {
        Node newNode = new Node(item);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        E data = front.data;
        front = front.next;
        size--;

        if (front == null) {
            rear = null;
        }

        return data;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> values() {
        return new QueueIterator();
    }


    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    // Clase interna que implementa Iterator para la cola
    private class QueueIterator implements Iterator<E> {
        private Node current;

        public QueueIterator() {
            current = front;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más elementos en la cola");
            }
            E data = current.data;
            current = current.next;
            return data;
        }
    }

}
