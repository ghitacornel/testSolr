package setup;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TestTemplate {

    private static final String COLLECTION_NAME = "bigboxstore";

    private int clientType = 0;

    protected SolrClient client = getClient();
    protected SolrClient client1 = getHttpSolrClient1();
    protected SolrClient client2 = getHttpSolrClient2();
    protected SolrClient client3 = getHttpSolrClient3();

    private SolrClient getClient() {
        if (clientType == 1) return getCloudSolrClient();
        if (clientType == 2) return getCloudSolrClientCluster();
        return getHttpSolrClient1();
    }

    private SolrClient getCloudSolrClient() {
        List<String> zkServers = new ArrayList<>();
        zkServers.add("localhost:2181");
        zkServers.add("localhost:2182");
        zkServers.add("localhost:2183");
        return new CloudSolrClient.Builder(zkServers, Optional.empty())
                .withDefaultCollection(COLLECTION_NAME)
                .build();
    }

    private SolrClient getHttpSolrClient1() {
        String urlString = "http://localhost:8981/solr/" + COLLECTION_NAME;
        return new Http2SolrClient.Builder(urlString)
                .withResponseParser(new XMLResponseParser())
                .build();
    }

    private SolrClient getHttpSolrClient2() {
        String urlString = "http://localhost:8982/solr/" + COLLECTION_NAME;
        return new Http2SolrClient.Builder(urlString)
                .withResponseParser(new XMLResponseParser())
                .build();
    }

    private SolrClient getHttpSolrClient3() {
        String urlString = "http://localhost:8983/solr/" + COLLECTION_NAME;
        return new Http2SolrClient.Builder(urlString)
                .withResponseParser(new XMLResponseParser())
                .build();
    }

    private SolrClient getCloudSolrClientCluster() {
        List<String> zkServers = new ArrayList<>();
        zkServers.add("localhost:2181");
        zkServers.add("localhost:2182");
        zkServers.add("localhost:2183");
        return new CloudSolrClient.Builder(zkServers, Optional.empty())
                .withDefaultCollection(COLLECTION_NAME)
                .build();
    }

    @Before
    @After
    public void deleteAll() throws Exception {
        client.deleteByQuery("*");
        client.commit();
    }

}
