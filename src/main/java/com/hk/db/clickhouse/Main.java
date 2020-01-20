package com.hk.db.clickhouse;

import java.sql.*;

public class Main {
    static {
        try {
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void gainData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:clickhouse://192.168.0.122:8123");

            Statement stat = conn.createStatement();
            stat.execute("select distinct hhhh from test.ffff");
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                String hhhh = rs.getString("hhhh");
                System.out.println(hhhh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }



}
