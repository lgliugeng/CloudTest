package com.liugeng.cloud.study;

import java.util.LinkedList;

public class LinkedListDemo {

    public static void main(String[] args) {
        MyQueue<String> myQueue = new MyQueue<>();
        System.out.println("加入队列:a、b、c、d、e");
        myQueue.enQueue("a");
        myQueue.enQueue("b");
        myQueue.enQueue("c");
        myQueue.enQueue("d");
        myQueue.enQueue("e");
        System.out.println("队列长度：" + myQueue.length());
        System.out.println("查看队列首位:" + myQueue.peek());
        System.out.println("取出队列首位：" + myQueue.deQueue());
        System.out.println("查看队列首位:" + myQueue.peek());
        System.out.println("取出队列首位:" + myQueue.deQueue());
        System.out.println("队列长度：" + myQueue.length());
        System.out.println("队列是否为空：" + myQueue.isEmpty());
        System.out.println("清空队列操作" );
        myQueue.clear();
        System.out.println("队列是否为空：" + myQueue.isEmpty());
        System.out.println("=====================================================================================");
        MyStack<String> myStack = new MyStack<>();
        System.out.println("栈是否为空：" + myStack.isEmpty());
        System.out.println("加入栈:a、b、c、d、e");
        myStack.push("a");
        myStack.push("b");
        myStack.push("c");
        myStack.push("d");
        myStack.push("e");
        System.out.println("栈是否为空：" + myStack.isEmpty());
        System.out.println("打印栈：" + myStack.toString());
        System.out.println("查看栈:" + myStack.peek());
        System.out.println("取出栈：" + myStack.pop());
        System.out.println("查看栈:" + myStack.peek());
        System.out.println("取出栈：" + myStack.pop());
        System.out.println("打印栈：" + myStack.toString());
    }
}

class MyQueue<T>{//队列先入先出

    private LinkedList<T> linkedList;

    public MyQueue(){
        linkedList = new LinkedList<T>();
    }

    /**
    * 方法说明   清空队列
    * @方法名    clear
    * @参数      []
    * @返回值    void
    * @异常
    * @创建时间  2019/6/11 10:44
    * @创建人    liugeng
    */
    public void clear(){
        linkedList.clear();
    }

    /**
    * 方法说明   判断队列是否为空
    * @方法名    isEmpty
    * @参数      []
    * @返回值    boolean
    * @异常
    * @创建时间  2019/6/11 10:44
    * @创建人    liugeng
    */
    public boolean isEmpty(){
        return linkedList.isEmpty();
    }

    /**
    * 方法说明   入队列
    * @方法名    enQueue
    * @参数      [o]
    * @返回值    void
    * @异常
    * @创建时间  2019/6/11 10:50
    * @创建人    liugeng
    */
    public void enQueue(T o){
        linkedList.addLast(o);
    }

    /**
    * 方法说明   出队
    * @方法名    deQueue
    * @参数      []
    * @返回值    T
    * @异常      
    * @创建时间  2019/6/11 10:50
    * @创建人    liugeng
    */
    public T deQueue(){
        if(!isEmpty()){
            return linkedList.removeFirst();//出队列并删除
        }
        return null;
    }

    /**
    * 方法说明   队列长度
    * @方法名    length
    * @参数      []
    * @返回值    int
    * @异常
    * @创建时间  2019/6/11 10:50
    * @创建人    liugeng
    */
    public int length(){
        return linkedList.size();
    }

    /**
    * 方法说明   查看队列首位
    * @方法名    peek
    * @参数      []
    * @返回值    T
    * @异常      
    * @创建时间  2019/6/11 10:50
    * @创建人    liugeng
    */
    public T peek(){
        if(!isEmpty()){
            return linkedList.getFirst();
        }
        return null;
    }
}

class MyStack<T>{//栈先入后出
    private LinkedList<T> stack;

    public MyStack(){
        stack = new LinkedList<T>();
    }

    /**
    * 方法说明   入栈
    * @方法名    push
    * @参数      [o]
    * @返回值    void
    * @异常
    * @创建时间  2019/6/11 11:10
    * @创建人    liugeng
    */
    public void push(T o){
        stack.addFirst(o);
    }

    /**
    * 方法说明   查看，不删除
    * @方法名    peek
    * @参数      []
    * @返回值    T
    * @异常
    * @创建时间  2019/6/11 11:10
    * @创建人    liugeng
    */
    public T peek(){
        if(!isEmpty()){
            return stack.getFirst();
        }
        return null;
    }

    /**
    * 方法说明   出栈
    * @方法名    pop
    * @参数      []
    * @返回值    T
    * @异常
    * @创建时间  2019/6/11 11:10
    * @创建人    liugeng
    */
    public T pop(){
        if(!isEmpty()){
            return stack.removeFirst();
        }
        return null;
    }

    /**
    * 方法说明   是否为空
    * @方法名    isEmpty
    * @参数      []
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/6/11 11:11
    * @创建人    liugeng
    */
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    /**
    * 方法说明   打印栈
    * @方法名    toString
    * @参数      []
    * @返回值    java.lang.String
    * @异常      
    * @创建时间  2019/6/11 11:11
    * @创建人    liugeng
    */
    public String toString(){
        return stack.toString();
    }
}
