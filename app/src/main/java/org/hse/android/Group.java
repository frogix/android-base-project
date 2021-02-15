package org.hse.android;

public class Group {

    private Integer id;
    private String name;

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public Integer getld() {
        return id;
    }

    public void setld(Integer id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;

    }
}
