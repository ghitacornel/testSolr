package beans;

import beans.model.Product;
import client.Client;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Assert;
import org.junit.Test;

public class TestCRUDDifferentMachines {

    Product product1 = new Product("1", "product one", "1110");
    Product product2 = new Product("2", "product two", "2220");
    Product product3 = new Product("3", "product three", "3330");
    Product product4 = new Product("4", "product two one", "21210");

    // 1 client for whole test
    SolrClient client1 = Client.getHttpSolrClient1();
    SolrClient client2 = Client.getHttpSolrClient2();
    SolrClient client3 = Client.getHttpSolrClient3();

    @Test
    public void testCRUD() throws Exception {

        // delete all
        {
            client1.deleteByQuery("*");
            client1.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client1.query(query);
            Assert.assertTrue(response.getResults().isEmpty());
        }

        // create
        {
            client1.addBean(product1);
            client1.addBean(product2);
            client1.addBean(product3);
            client1.addBean(product4);
            client1.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client2.query(query);
            Assert.assertEquals(4, response.getResults().size());
        }

        // read some
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "name:two");
            QueryResponse response = client2.query(query);
            Assert.assertEquals(2, response.getResults().size());
        }

        // delete by id
        {
            client2.deleteById("4");
            client2.commit();
        }

        // read some
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "name:two");
            QueryResponse response = client3.query(query);
            Assert.assertEquals(1, response.getResults().size());
        }

        // delete all
        {
            client3.deleteByQuery("*");
            client3.commit();
        }

        // read all
        {
            SolrQuery query = new SolrQuery();
            query.set("q", "*");
            QueryResponse response = client1.query(query);
            Assert.assertTrue(response.getResults().isEmpty());
        }

    }
}
