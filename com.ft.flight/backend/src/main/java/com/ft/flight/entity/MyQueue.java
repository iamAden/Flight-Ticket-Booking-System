package com.ft.flight.entity;

import org.springframework.stereotype.Component;

@Component
public class MyQueue<T> {
    private int head, tail;
    private int maxSize;

    private T[] arr;
    public MyQueue(){
        this.head=0;
        this.tail=0;
        this.maxSize=100;
        arr = (T[]) new Object[100];
    }
    public boolean isEmpty(){
        return this.tail==0;
    }
    public T peek(){
        return arr[head];
    }
    public boolean enqueue(T data){
        int temp=0;
        while(temp<tail){
            if(arr[temp].equals(data)){
                //System.out.println("Already in queue");

            }
            temp++;
        }

        if(tail>=maxSize){
            System.out.println("Cannot enqueue "+data +" , queue is full");
            return false;
        }
        else{
            arr[tail]=data;
            tail++;
            //System.out.println("Tail is at "+tail);
        }
        return true;
    }
    public T dequeue(){
        T exHead = arr[0];
        if(isEmpty()){
            System.out.println("Nothing to dequeue");
        }
        else{
            int temp=0;

            while(temp<maxSize-1){
                arr[temp]=arr[temp+1];
                temp++;
            }
            arr[maxSize-1]=null;
            tail--;
        }
        return exHead;
    }
    public String display(){
        StringBuilder sb = new StringBuilder();
        for(T data: arr){
            if (data!=null) sb.append(data).append(" --> ");
        }
        return sb.toString();
    }
    public int size(){
        return tail;
    }
}