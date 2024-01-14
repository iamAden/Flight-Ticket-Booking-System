package com.ft.flight.entity;

import org.springframework.stereotype.Component;

public class Node<E extends Comparable<E>> implements Comparable<Node<E>>{
    public E data;
    public Node next;
    public Node(E data){
        this.data=data;
        this.next=null;
    }
    @Override
    public int compareTo(Node<E> otherNode){
        return this.data.compareTo(otherNode.data);
    }
}
