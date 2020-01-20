package com.hk.db.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.util.Map;

public class Main {


    //ES索引操作
    void indexOP(String indexId, Map<String, Object> doc) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.0.111", 9201, "http")
        ));

        IndexRequest request = new IndexRequest("PHONE_INDEX").type("_doc").id(indexId).source(doc);

        request.timeout(TimeValue.timeValueSeconds(5));
        request.opType(DocWriteRequest.OpType.INDEX);
        System.out.println(request);
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("id: " + response.getId() + " index success!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
