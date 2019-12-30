package com.hk.se;

public class UnitTask implements Runnable {

    int flag;

    UnitTask(int flag) {
        this.flag = flag;
    }

    public void run() {
        System.out.println("Task " + flag + " start!");

        try{
            Thread.sleep(5 * 100);
        }catch(Exception e) {

        }

        System.out.println("Task " + flag + " finished!");
    }

}