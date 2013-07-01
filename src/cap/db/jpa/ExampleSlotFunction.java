package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class ExampleSlotFunction implements ISlotFunction {

    @Id
    @GeneratedValue
    private long id = 0;

    private String testValue = "";

    public ExampleSlotFunction() {}

    @Override
    public void Run() {

    }

    @Override
    public long getId() {
        return this.id;
    }

    public String getTestValue() {
        return this.testValue;
    }

    public void setTestValue(final String testValue) {
        this.testValue = testValue;
    }
}
