package com.hk.db.h2;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.File;
import java.sql.*;

//h2数据库测试
public class Main {

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private String path = System.getProperty("user.dir") + File.separator + "db";

    private void init(){
        File dir = new File(path);


        if(dir.exists()) {
            if(!dir.isDirectory()) {
                System.out.println("dir: " + dir + " 应该是目录！");
                dir.delete();
            }
        }else {
            dir.mkdir();
            System.out.println("创建目录: " + path);
        }



    }


    private void createTable() {

        String url = "jdbc:h2:" + path + File.separator + "test";
        try (Connection conn = DriverManager.getConnection(url); ){

            Statement stmt = conn.createStatement();
//            stmt.executeUpdate("create table t_test(id int primary key, str varchar(255));");
            stmt.executeUpdate("create table k_a(id int primary key, str varchar(255));");

            System.out.println("建表成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertData() {
        String url = "jdbc:h2:" + path + File.separator + "test";
        try (Connection conn = DriverManager.getConnection(url); ){

            Statement stmt = conn.createStatement();

            int off = 15;
            for(int i = off; i < off + 5; i++) {
                stmt.addBatch(String.format("insert into t_test values(%d, 'Hello World!')", i));
            }
            stmt.executeBatch();
            System.out.println("插入完成");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void queryData() {
        String url = "jdbc:h2:" + path + File.separator + "test";
        try (Connection conn = DriverManager.getConnection(url); ){

            Statement stmt = conn.createStatement();
            stmt.execute("select * from t_test order by id desc limit 5 offset 0");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {

                System.out.println(rs.getInt("id") + ", " + rs.getString("str"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void countData() {
        String url = "jdbc:h2:" + path + File.separator + "test";
        try (Connection conn = DriverManager.getConnection(url); ){

            Statement stmt = conn.createStatement();
            stmt.execute("select count(*) from t_test");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getInt("count(*)"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Main m = new Main();
//        m.init();
//        m.createTable();
//        m.insertData();
        m.queryData();
        m.countData();
    }
}
