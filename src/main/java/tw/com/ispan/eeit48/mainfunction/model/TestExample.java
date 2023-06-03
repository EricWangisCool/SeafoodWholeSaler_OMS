package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class TestExample {
    @Id
    Integer id;
    Integer columnOne;
    Integer columnTwo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(Integer columnOne) {
        this.columnOne = columnOne;
    }

    public Integer getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(Integer columnTwo) {
        this.columnTwo = columnTwo;
    }

    public TestExample() {}
}
