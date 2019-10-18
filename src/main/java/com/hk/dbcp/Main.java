package com.hk.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class Main {


    //dbpc构建数据源
    private static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(connectURI);
        ds.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
        ds.setMaxIdle(12);
        ds.setMaxTotal(200);
        ds.setUsername("user");
        ds.setPassword("password");
        return ds;
    }

    private static final DataSource dataSource = setupDataSource("jdbc:clickhouse://127.0.0.1:8123?debug=false");

}
