package com.stratio.deep.rdd;

import com.google.common.io.Resources;
import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by rcrespo on 16/07/14.
 */
@Test(suiteName = "ESRddTests", groups = {"ESJavaRDDTest"})
public class ESJavaRDDTest {





    public static final String MESSAGE_TEST = "new message test";

    public static final Integer PORT = 9200;

    public static final String HOST = "localhost";

    public static final String DATABASE = "twitter/tweet";

    public static final String DATABASE_OUTPUT = "twitter/tweet2";

    public static final String COLLECTION_OUTPUT = "output";

    public static final String COLLECTION_OUTPUT_CELL = "outputcell";

    public static final String DATA_SET_NAME = "divineComedy.json";


    public static final Long WORD_COUNT_SPECTED = 3833L;


    private static Node node = null;

    private static Client client = null;

    @BeforeSuite
    public static void init() throws IOException, ExecutionException, InterruptedException {

        node = nodeBuilder().local(true).clusterName("clusterTest").node();
        client = node.client();




        dataSetImport();

    }

    /**
     * Imports dataset
     *
     * @throws java.io.IOException
     */
    private static void dataSetImport() throws IOException, ExecutionException, InterruptedException {



        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse response = client.prepareIndex("twitter", "tweet").setCreate(true)
                .setSource(json)
                .execute()
                .actionGet();


try {


    CountResponse action = client.prepareCount("twitter").setTypes("tweet")
            .execute()
            .actionGet();


}catch (Exception e){
    e.printStackTrace();
}








    }


    @AfterSuite
    public static void cleanup() {
        node.stop();

        File file = new File("data");
        file.delete();
    }
}
