package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {


    private Comparator<E> comparator;

    private E[] data;
    private int len;


    public OrderedVector(int max, Comparator<E> comparator) {
        this.comparator = comparator;
        data = (E[])new Object[max];
        len = 0;
    }

    public E elementAt(int i) {
        return this.data[i];
    }

    private boolean compare(E elem1, E elem2) {
        return ((Comparable<E>)elem1).compareTo(elem2) == 0;
    }
    public void rshift(int i) {
        // desplazamiento de todos los elementos una posición
        int p = len-1;
        while (p >= i) {
            data[p+1] = data[p];
            p--;
        }
    }
    public void lshift(int i) {
        // desplazamiento de todos los elementos una posición
        int p = i;
        while (p < len-1) {
            data[p] = data[p+1];
            p++;
        }
    }
    public void update(E vIn) {
        int i = 0;
        if (isFull()) {
            E pOut = last();
            if (comparator.compare(pOut, vIn) < 0) {
                delete(pOut);
                update(vIn);
                return;
            }
            else {
                return;
            }
        }
        while (i < len && data[i] != null && comparator.compare(data[i], vIn) >= 0) { 
            i++;
        }
        rshift(i);
        data[i] = vIn;
        len++;

    }

    public void delete (E elem) {
        int i = 0;
        boolean found = false;

        while (!found && i < len)
            found = compare(elem, data[i++]);

        if (found) {
            if (i<len) {
                lshift(i-1);
            }
            else {
            	lshift(i);
            }
            len--;
        }
    }
    public boolean isFull() {
        return len == data.length;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public E last() {
        return data[len-1];
    }
    public int size() {
        return len;
    }
    public Iterator<E> values() {
        return (Iterator<E>)new IteratorArrayImpl<>(data, len,0);
    }

}
