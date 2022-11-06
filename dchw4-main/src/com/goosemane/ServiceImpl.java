package com.goosemane;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<Integer> queue;
    ArrayList<Integer> receivedMessages = new ArrayList<Integer>();
    //time
    static long start = 0, end = 0;
    boolean check = false;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<Integer>();
    }

    @Override
    public Integer getMessage() throws RemoteException {
        if (!check) {
            start = System.nanoTime();
        }
        check = true;
        return this.queue.poll();
    }

    @Override
    public void sendMessage(int num) throws RemoteException {
        this.queue.add(num);
    }

    @Override
    public void receiveMessage(int num) throws RemoteException {
        System.out.println("Queue consists of: " + queue);
        receivedMessages.add(num);
        if (queue.isEmpty()) {
            try {
                Thread.sleep((long) 11.11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tasksEnded(receivedMessages);
        }
    }

    public static void tasksEnded(ArrayList<Integer> numberList) {
        int sum = 0;
        for (int numbers : numberList) {
            sum += numbers;
        }
        System.out.println("The sum is equal to: "+sum);
        end = System.nanoTime();
        System.out.println("Time taken:" + (end - start) );
    }

}