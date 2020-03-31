package com.hk.alibaba;

import com.alibaba.fastjson.JSONObject;

public class JSON {


    private static void buildJson() {
        JSONObject doc = new JSONObject();
        doc.put("xxx", 123);
        doc.put("ttt", null);
        doc.put("iii", "");
        System.out.println(doc);


    }
    public static void main(String[] args) {

        buildJson();
    }


}
