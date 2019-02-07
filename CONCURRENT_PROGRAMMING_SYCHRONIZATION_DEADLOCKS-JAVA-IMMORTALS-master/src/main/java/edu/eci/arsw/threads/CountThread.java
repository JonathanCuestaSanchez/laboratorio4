/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{

    private int a, b;

    CountThread(int a, int b) {
        super("my extending thread");
        this.a = a;
        this.b = b;
        System.out.println("my thread created" + this);
        run();
    }

    public void run() {
        try {
            for (int i = a; i < b + 1; i++) {
                System.out.println("Printing the count " + i);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println("my thread interrupted");
        }
        System.out.println("My thread run is over");
    }
}
