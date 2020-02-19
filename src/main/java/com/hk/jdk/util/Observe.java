package com.hk.jdk.util;

import java.util.Observable;
import java.util.Observer;


//jdk观察者测试代码
public class Observe {

    class Main implements Observer {


        private int count = 0;

        @Override
        public void update(Observable o, Object arg) { //此块代码的调用有被观察者决定，观察者自身不考虑自己调用

            count++;
            System.out.println("count: "+ count);
            System.out.println(arg.toString());
        }

    }

    class Item extends Observable {

        private void run() {
            setChanged();
            System.out.println(hasChanged());
            notifyObservers("Hello World!");
            System.out.println(hasChanged());
        }

    }

    private void test() {
        Main m = new Main();
        Item item = new Item();
        System.out.println("测试");
        item.addObserver(m); //此块代码可以放置在被观察者内部，也可以放在外部
        item.run();
        item.run();
        item.run();
        item.run();
        item.run();

    }

    public static void main(String[] args) {

        var main = new Observe();
        main.test();

    }
}
