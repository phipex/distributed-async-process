package co.com.ies.pruebas.webservice.task;

import java.io.Serializable;
import java.util.Objects;

public class TaskTest implements Serializable {
    private int value;

    public TaskTest() {}
    public TaskTest(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskTest tastTest = (TaskTest) o;
        return value == tastTest.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TastTest{" +
                "value=" + value +
                '}';
    }
}
