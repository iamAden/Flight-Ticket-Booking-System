package com.ft.flight.entity;

import org.springframework.stereotype.Component;

@Component
public class MyLinkedList{
    public static class Node {
        public Flight data;
        public Node next;
        public Node(Flight data){
            this.data=data;
            this.next=null;
        }
    }
    private Node head;
    private Node tail;
    private int size;
    public MyLinkedList(){
        this.head=null;
        this.tail=null;
        this.size=0;
    }
    public int getSize(){
        return this.size;
    }
    //assigning new head
    public void addFirst(Node newNode){
        //if list is empty
        if(head==null){
            head=newNode;
        }
        else{
            newNode.next=head;
            head=newNode;
            tail=head.next;
        }
        size++;
    }
    public void add(Node newNode){
        //if list is empty
        if(head==null){
            head = tail = newNode;
        }
        else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    public Flight removeFirst(){
        Node temp = head;
        head = head.next;
        size--;
        return temp.data;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[size=").append(this.size).append("]");

        Node temp=head;
        while(temp!=null){
            sb.append(">>").append(temp.data);
            temp=temp.next;
        }
        return sb.toString();
    }
    public boolean contains(Flight data){
        Node temp=head;
        while(temp!=null){
            //matches
            if(temp.data==data) return true;
            //else
            temp=temp.next;
        }
        //return false if list is empty
        return false;
    }
    public void clear(){
        head = null;
        tail = null;

        size=0;
        System.out.println("The list is cleared.");
    }
    public void combine(MyLinkedList otherList){
        Node temp=otherList.head;
        while(temp!=null){
            this.add(temp);
            temp=temp.next;
        }
    }
}

