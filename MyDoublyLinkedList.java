/*
Author: Carlos Garcia
Description: This class creates a list with the data structure of doubly linked lists. The methods of this class are tested
in TestMyDoublyLinkedList.java. All supported methods work like those in java.util.LinkedList.
 */

import java.util.*;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable {
    private Node<E> head;

    // constructor for new doubly linked list
    public MyDoublyLinkedList() {
        head = new Node<>(null);
        head.next = head;
        head.previous = head;
    }

    // returns the size of the list
    @Override
    public int size() {
        return size;
    }

    // returns the element at the beginning of the list
    @Override
    public E getFirst() {
        return get(0);
    }

    // returns the element at the end of the list
    @Override
    public E getLast() {
        return get(size - 1);
    }

    // adds the given element to the beginning of the list
    @Override
    public void addFirst(E e) {
        add(0, e);
    }

    // adds the given element to the end of the list
    @Override
    public void addLast(E e) {
        add(size, e);
    }

    // removes and returns the element at the beginning of the list
    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Size: " + size);
        }
        else {
            return remove(0);
        }
    }

    // removes and returns the element at the end of the list
    @Override
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Size: " + size);
        }
        else {
            return remove(size - 1);
        }
    }

    // creates and returns an iterator at a specific index
    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Size: " + size);
        }
        else {
            return new DoublyLinkedListIterator(index);
        }
    }

    // adds the given element at the given index
    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Size: " + size);
        }
        else {
            Node<E> newNode = new Node<>(e);
            Node<E> prev = getNode(index - 1);
            Node<E> next = prev.next;
            prev.next = newNode;
            newNode.previous = prev;
            next.previous = newNode;
            newNode.next = next;
            size++;
        }
    }

    // adds the given element at the end of the list
    @Override
    public void add(E e) {
        add(size, e);
    }

    // clears the list
    @Override
    public void clear() {
        size = 0;
        head.next = head = null;
    }

    // returns a boolean on whether the list contains the given element
    @Override
    public boolean contains(E e) {
        Node<E> current = head.next;
        while (current != head) {
            if (e == null ? current.element == null : e.equals(current.element)) {
                return true;
            }
            else {
                current = current.next;
            }
        }
        return false;
    }

    // gets and returns the element at the given index
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size: " + size);
        }
        else {
            return getNode(index).element;
        }
    }

    // gets and returns the node at the given index
    private Node<E> getNode(int index) {
        Node<E> current = head;
        if (index < size / 2) {
            for (int i = -1; i < index; i++) {
                current = current.next;
            }
        }
        else {
            for (int i = size; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    // returns the index of the given element
    @Override
    public int indexOf(E e) {
        Node<E> current = head.next;
        int index = 0;
        while (current != head) {
            if (e == null ? current.element == null : e.equals(current.element)) {
                return index;
            }
            else {
                current = current.next;
                index++;
            }
        }
        return -1;
    }

    // returns the last index of the given element
    @Override
    public int lastIndexOf(E e) {
        Node<E> current = head.previous;
        int index = size -1;
        while (current != head) {
            if (e == null ? current.element == null : e.equals(current.element)) {
                return index;
            }
            else {
                current = current.previous;
                index--;
            }
        }
        return -1;
    }

    // returns a boolean on whether the given object's elements are equal to the elements in the list
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        else if (!(other instanceof MyList)) {
            return false;
        }
        if (this.size != ((MyList<?>) other).size()) {
            return false;
        }
        else {
            java.util.Iterator<E> thisIterator = this.iterator();
            java.util.Iterator<?> otherIterator = ((MyList<?>)other).iterator();
            while (thisIterator.hasNext()) {
                E thisElement = thisIterator.next();
                Object otherElement = otherIterator.next();
                if (thisElement == null && otherElement != null ) {
                    return false;
                }
                else if (thisElement != null && otherElement == null) {
                    return false;
                }
                else if (thisElement != null && !(thisElement.equals(otherElement))) {
                    return false;
                }
            }
            return true;
        }
    }

    // removes and returns the element at the given index
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        else {
            Node<E> toRemove = getNode(index);
            Node<E> prev = toRemove.previous;
            Node<E> next = toRemove.next;
            prev.next = next;
            next.previous = prev;
            size--;
            return toRemove.element;
        }
    }

    // replaces the element at the given index with the element given and returns the origninal element
    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size: " + size);
        }
        else {
            Node<E> temp = getNode(index);
            E returnValue = temp.element;
            temp.element = e;
            return returnValue;
        }
    }

    // clones the list and returns that object
    @Override
    public Object clone() {
        try {
            MyDoublyLinkedList<E> listClone = (MyDoublyLinkedList<E>) super.clone();
            listClone.head = new Node<>(null);
            listClone.head.next = listClone.head;
            listClone.head.previous = listClone.head;
            listClone.size = 0;
            java.util.Iterator<E> thisIterator = this.iterator();
            while (thisIterator.hasNext()) {
                listClone.add(thisIterator.next());
            }
            return listClone;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException();
        }
    }

    // returns a string containing all elements in the list
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head.next;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != head) {
                result.append(", ");
            }
            else {
                result.append("]");
            }
        }

        return result.toString();
    }

        private class DoublyLinkedListIterator implements java.util.ListIterator<E> {
        private Node<E> nextNode;
        int nextIndex;
        Node<E> lastReturned = null;

        // constructor for doubly linked list iterator
        public DoublyLinkedListIterator(int index) {
            nextNode = getNode(index);
            nextIndex = index;
        }

        // checks whether iterator has a next element and returns a boolean
        @Override
        public boolean hasNext() {
            return (nextNode != head);
        }

        // checks whether there is a next element in the list iterator and returns that element
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                nextNode = nextNode.next;
                nextIndex++;
                lastReturned = nextNode.previous;
                return nextNode.previous.element;
            }
        }

        // checks whether iterator has a previous element and returns a boolean
        @Override
        public boolean hasPrevious() {
            return (nextIndex > 0);
        }

        // checks whether there is a previous element in the list and returns that element
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            else {
                nextIndex--;
                nextNode = nextNode.previous;
                lastReturned = nextNode;
                return nextNode.element;
            }
        }

        // returns the next index of the list iterator
        @Override
        public int nextIndex() {
            return nextIndex;
        }

        // returns the previous index of the list iterator
        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        // removes an element in the list iterator
        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            else {
                if (lastReturned == nextNode) {
                    nextNode = nextNode.next;
                }
                else {
                    nextIndex--;
                }
                nextNode.previous = (nextNode.previous).previous;
                (nextNode.previous).next = nextNode;
                size--;
                lastReturned = null;
            }
        }

        // replaces the given element with the last element returned
        @Override
        public void set(E e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            else {
                Node<E> temp = getNode(indexOf(lastReturned.element));
                temp.element = e;
            }
        }

        // adds the given element in the list iterator
        @Override
        public void add(E e) {
            Node<E> newNode = new Node<E>(e);
            Node<E> oldPrev = getNode(nextIndex - 1);
            Node<E> next = oldPrev.next;
            oldPrev.next = newNode;
            newNode.previous = oldPrev;
            next.previous = newNode;
            newNode.next = next;
            size++;
            nextIndex++;
            lastReturned = null;
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }
    }
}
