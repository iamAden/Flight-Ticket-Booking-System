package com.ft.flight.entity;

import org.springframework.stereotype.Component;
import java.util.stream.Stream;

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

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        // if list is empty
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void remove(T data){
        if(contains(data)){
            
        }
        else{

        }
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
    public Stream<T> stream() {
        return Stream.iterate(head, node -> node != null, node -> node.next)
                     .map(node -> node.data);
    }
}