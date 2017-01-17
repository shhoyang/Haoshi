package com.haoshi.sqlite.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: HaoShi
 */

@DatabaseTable
public class Personnel {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String num;

    public Personnel() {
    }

    public Personnel(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
