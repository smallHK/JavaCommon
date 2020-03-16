package com.hk.jdk.net;

import java.io.IOException;
import java.nio.channels.Selector;

public class Select {



    private static void firstSelector() {
        try(Selector selector = Selector.open();) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
