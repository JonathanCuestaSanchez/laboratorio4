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
public class CountThreadsMain {

    public static void main(String a[]) {
        CountThread cnt = new CountThread (0, 99);
        CountThread  cnt2 = new CountThread (99, 199);
        CountThread  cnt3 = new CountThread (200, 299);

        try {
            while (cnt.isAlive()) {
                System.out.println("Main thread will be alive till the child thread is live");
                Thread.sleep(15);
            }

        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread's run is over");
    }

}
