package com.ft.flight.entity;

import org.springframework.stereotype.Component;

@Component
public class MyLinkedList<T> {
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

    public void add(Node<T> newNode) {
        // if list is empty
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        Node<T> temp = head;
        head = head.next;
        size--;
        return temp.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[size=").append(this.size).append("]");

        Node<T> temp = head;
        while (temp != null) {
            sb.append(">>").append(temp.data);
            temp = temp.next;
        }
        return sb.toString();
    }

    public boolean contains(T data) {
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

    public void clear() {
        head = null;
        tail = null;

        size = 0;
        System.out.println("The list is cleared.");
    }

    public void combine(MyLinkedList<T> otherList) {
        Node<T> temp = otherList.head;
        while (temp != null) {
            this.add(new Node<>(temp.data));
            temp = temp.next;
        }
    }
}