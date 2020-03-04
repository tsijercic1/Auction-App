package com.github.tsijercic1.auctionapi.response;

public class SubcategoryData {
    private Long id;
    private String name;

    public SubcategoryData() {
    }

    public SubcategoryData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
