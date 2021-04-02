package beans.inputdocument;

import org.apache.solr.common.SolrInputDocument;

import java.util.Date;

public class MyInputDocument {

    private int id;
    private String name;
    private Double salary;
    private Date dob;

    public SolrInputDocument build() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        document.addField("name", name);
        document.addField("salary", salary);
        document.addField("dob", dob);
        return document;
    }

    @Override
    public String toString() {
        return "MyInputDocument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", dob=" + dob +
                '}';
    }

    public MyInputDocument(int id, String name, Double salary, Date dob) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
