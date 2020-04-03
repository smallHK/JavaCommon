package com.hk.apache.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import javax.sound.midi.MidiChannel;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {



    private void connectZookeeper() {
        try {

            CountDownLatch cdl = new CountDownLatch(1);
            ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, e ->{

                System.out.println(e.getState());
                if(e.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("watch received event!");
                    cdl.countDown();
                }
            });
            cdl.await();
            System.out.println("connect success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    public static void main(String[] args) {

        Main main = new Main();
        main.connectZookeeper();
    }
}
