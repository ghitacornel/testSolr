package setup;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TestTemplate {


    private static final String COLLECTION_NAME = "bigboxstore";

    private int type = 0;

    protected SolrClient client = getClient();
    protected SolrClient client1 = getHttpSolrClient1();
    protected SolrClient client2 = getHttpSolrClient2();
    protected SolrClient client3 = getHttpSolrClient3();

    protected SolrClient getClient() {
        if (type == 1) return getCloudSolrClient();
        if (type == 2) return getCloudSolrClientCluster();
        return getHttpSolrClient1();
    }

    protected SolrClient getCloudSolrClient() {

        List<String> zkServers = new ArrayList<>();
        zkServers.add("localhost:2181");
        zkServers.add("localhost:2182");
        zkServers.add("localhost:2183");
        CloudSolrClient client = new CloudSolrClient.Builder(zkServers, Optional.empty()).build();
        client.setDefaultCollection(COLLECTION_NAME);
        return client;

    }

    protected SolrClient getHttpSolrClient1() {

        String urlString = "http://localhost:8981/solr/" + COLLECTION_NAME;
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        client.setParser(new XMLResponseParser());
        return client;

    }

    protected SolrClient getHttpSolrClient2() {

        String urlString = "http://localhost:8982/solr/" + COLLECTION_NAME;
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        client.setParser(new XMLResponseParser());
        return client;

    }

    protected SolrClient getHttpSolrClient3() {

        String urlString = "http://localhost:8983/solr/" + COLLECTION_NAME;
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        client.setParser(new XMLResponseParser());
        return client;

    }

    protected SolrClient getCloudSolrClientCluster() {

        List<String> zkServers = new ArrayList<>();
        zkServers.add("localhost:2181");
        zkServers.add("localhost:2182");
        zkServers.add("localhost:2183");
        CloudSolrClient client = new CloudSolrClient.Builder(zkServers, Optional.empty()).build();
        client.setDefaultCollection(COLLECTION_NAME);
        return client;

    }

    @Before
    @After
    public void deleteAll() throws Exception {
        client.deleteByQuery("*");
        client.commit();
    }

}
