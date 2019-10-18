package com.hk.db.arrangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.velocypack.VPackSlice;

public class Main {

    private static void testAQL() {
        ArangoDB arangoDB = new ArangoDB.Builder().host("localhost", 8528).build();

        String dbName = "mydb";
        String cn = "firstCollection";

        //fake data
        ArangoCollection collection = arangoDB.db(dbName).collection(cn);
        for (int i = 0; i < 10;i ++) {
            BaseDocument value = new BaseDocument();
            value.setKey(String.valueOf(i));
            value.addAttribute("name", "Homer");
            collection.insertDocument(value);
        }

        //执行AQL查询
        String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";

    }

    public static void main(String[] args) {
        ArangoDB arangoDB = new ArangoDB.Builder().host("localhost", 8528).build();

        String dbName = "mydb";

        arangoDB.createDatabase(dbName);
        System.out.println("Database created: " + dbName);


        //创建coll
        String cn = "firstCollection";
        CollectionEntity collection = arangoDB.db(dbName).createCollection(cn);
        System.out.println("Collection created: " + collection.getName());


        //插入doc
        BaseDocument myObj = new BaseDocument();
        myObj.setKey("myKey");
        myObj.addAttribute("a", "Foo");
        myObj.addAttribute("b", 42);
        arangoDB.db(dbName).collection(cn).insertDocument(myObj);
        System.out.println("Document created");

        //读取doc
        BaseDocument myDom = arangoDB.db(dbName).collection(cn).getDocument("myKey", BaseDocument.class);
        System.out.println("Key: " + myDom.getKey());
        System.out.println("Attr a: " + myDom.getAttribute("a"));
        System.out.println("Attr b: " + myDom.getAttribute("b"));


        //将doc读作velocy
        VPackSlice myDocVe = arangoDB.db(dbName).collection(cn).getDocument("myKey", VPackSlice.class);
        System.out.println("Key: " + myDocVe.get("_key").getAsString());
        System.out.println("Attr a: " + myDocVe.get("a").getAsString());
        System.out.println("Attr b: " + myDocVe.get("b").getAsInt());


        //更新doc
        myObj.addAttribute("c", "Bar");
        arangoDB.db(dbName).collection(cn).updateDocument("myKey", myObj);


        //再一次读取doc

        //删除doc
        arangoDB.db(dbName).collection(cn).deleteDocument("myKey");



    }
}
