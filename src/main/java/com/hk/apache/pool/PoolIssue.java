package com.hk.apache.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class PoolIssue {


    //测试丢弃池对象，引发阻塞，观察结果
    private static  void leakBlocking() {

        GenericObjectPoolConfig<String> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(20);

        GenericObjectPool<String> pool = new GenericObjectPool<>(new PooledObjectFactory<String>() {
            @Override
            public PooledObject<String> makeObject() throws Exception {
                return new DefaultPooledObject<>( "Hello World!");
            }

            @Override
            public void destroyObject(PooledObject<String> p) throws Exception {
                String s= p.getObject();
                s = null;
            }

            @Override
            public boolean validateObject(PooledObject<String> p) {
                return p.getObject() != null;
            }

            @Override
            public void activateObject(PooledObject<String> p) throws Exception {
                if (null == p.getObject()) {
                    p = new DefaultPooledObject<>("HHHH");
                }
            }

            @Override
            public void passivateObject(PooledObject<String> p) throws Exception {
                if(null == p.getObject()) {
                    p = new DefaultPooledObject<>("xxxxx");
                }
            }
        }, poolConfig);



        //测试丢弃引发阻塞
        try {
            for(int i = 0; i < 30; i++) {
                String ss = pool.borrowObject();
                System.out.println("task: " + i + " 获取成功: " + ss);
//                pool.returnObject(ss); 连接对象泄露
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //测试连接池添加池对象
    private static void addPoolObject() {
        GenericObjectPoolConfig<String> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(40);

        GenericObjectPool<String> pool = new GenericObjectPool<>(new PooledObjectFactory<String>() {
            @Override
            public PooledObject<String> makeObject() throws Exception {
                return new DefaultPooledObject<>( "Hello World!");
            }

            @Override
            public void destroyObject(PooledObject<String> p) throws Exception {
                String s= p.getObject();
                s = null;
            }

            @Override
            public boolean validateObject(PooledObject<String> p) {
                return p.getObject() != null;
            }

            @Override
            public void activateObject(PooledObject<String> p) throws Exception {
                if (null == p.getObject()) {
                    p = new DefaultPooledObject<>("HHHH");
                }
            }

            @Override
            public void passivateObject(PooledObject<String> p) throws Exception {
                if(null == p.getObject()) {
                    p = new DefaultPooledObject<>("xxxxx");
                }
            }
        }, poolConfig);



        //添加10个对象
        try {
            for (int i = 0; i < 20; i++) {
                pool.addObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(pool.getNumIdle());
        System.out.println(pool.getNumActive());
    }

    public static void main(String[] args) {

//        leakBlocking();

        addPoolObject();
    }
}
