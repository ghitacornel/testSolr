package beans;

import beans.model.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Assert;
import org.junit.Test;
import setup.TestTemplate;

public class TestBeansCRUD extends TestTemplate {

    Product product1 = new Product("1", "product one", "1110");
    Product product2 = new Product("2", "product two", "2220");
    Product product3 = new Product("3", "product three", "3330");
    Product product4 = new Product("4", "product two one", "21210");

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
            client.addBean(product1);
            client.addBean(product2);
            client.addBean(product3);
            client.addBean(product4);
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
            query.set("q", "name:two");
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
            query.set("q", "name:two");
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
