package com.wallet.entity;

public class ColumnEditEntity {
    private String id;
    private int number;
    private String typeName;

    public ColumnEditEntity(int number, String typeName) {
        this.number = number;
        this.typeName = typeName;
    }

    public ColumnEditEntity(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
