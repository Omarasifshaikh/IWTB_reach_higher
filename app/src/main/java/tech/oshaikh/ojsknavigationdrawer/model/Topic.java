package tech.oshaikh.ojsknavigationdrawer.model;

import java.io.Serializable;

/**
 * Created by omar on 11/21/15.
 */
public class Topic implements Serializable{
    private int id;
    private String name;

    public Topic() {
    }

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() { return name; }
    /*
    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    */

}
