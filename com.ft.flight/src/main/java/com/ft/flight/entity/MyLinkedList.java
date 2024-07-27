package com.ft.flight.entity;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class MyLinkedList<T> implements Iterable<T>{
    public static class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty(){
        return head==null;
    }

    public Node<T> getHead(){
        return this.head;
    }
    // assigning new head
    public void addFirst(Node<T> newNode) {
        // if list is empty
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
            tail = head.next;
        }
        size++;
    }

    public boolean add(T data) {
        Node<T> newNode = new Node<>(data);
        // if list is empty
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    public T removeFirst() {
        Node<T> temp = head;
        head = head.next;
        size--;
        return temp.data;
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//
//        Node<T> temp = head;
//        while (temp != null) {
//            sb.append(">>").append(temp.data);
//            temp = temp.next;
//        }
//        return sb.toString();
//    }

    public boolean contains(Object data) {
        Node<T> temp = head;
        while (temp != null) {
            // matches
            if (temp.data.equals(data)) return true;
            // else
            temp = temp.next;
        }
        // return false if list is empty
        return false;
    }
//
//    @Override
//    public Iterator<T> iterator() {
//        return null;
//    }


    public void clear() {
        head = null;
        tail = null;

        size = 0;
        System.out.println("The list is cleared.");
    }


    public Stream<T> stream() {
        return Stream.iterate(head, node -> node != null, node -> node.next).map(node -> node.data);
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        sb.append("[");
        while (current != null) {
            sb.append(current.data);
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}