package inputdocument;

import beans.inputdocument.MyInputDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Assert;
import org.junit.Test;
import setup.TestTemplate;

import java.util.Date;

public class TestCRUD extends TestTemplate {

    SolrInputDocument document1 = new MyInputDocument(1, "ion", 11.10, new Date()).build();
    SolrInputDocument document2 = new MyInputDocument(2, "gheorghe", 22.20, new Date()).build();
    SolrInputDocument document3 = new MyInputDocument(3, "marin", 33.30, new Date()).build();
    SolrInputDocument document4 = new MyInputDocument(4, "gheorghe gheorghe", 21.210, new Date()).build();

    @Test
    public void testCRUD() throws Exception {

        // delete all
        {
            client.deleteByQuery("*");
            client.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client.query(query);
            Assert.assertTrue(response.getResults().isEmpty());
        }

        // create
        {
            client.add(document1);
            client.add(document2);
            client.add(document3);
            client.add(document4);
            client.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client.query(query);
            Assert.assertEquals(4, response.getResults().size());
        }

        // read some
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "name:gheorghe");
            QueryResponse response = client.query(query);
            Assert.assertEquals(2, response.getResults().size());
        }

        // delete by id
        {
            client.deleteById("4");
            client.commit();
        }

        // read some
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "name:gheorghe");
            QueryResponse response = client.query(query);
            Assert.assertEquals(1, response.getResults().size());
        }

        // delete all
        {
            client.deleteByQuery("*");
            client.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client.query(query);
            Assert.assertTrue(response.getResults().isEmpty());
        }

    }
}
