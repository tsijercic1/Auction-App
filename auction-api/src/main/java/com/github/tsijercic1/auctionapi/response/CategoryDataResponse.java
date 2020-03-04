package com.github.tsijercic1.auctionapi.response;

import java.util.List;

public class CategoryDataResponse {
    private Long id;
    private String name;
    private List<SubcategoryData> subcategories;

    public CategoryDataResponse() {
    }

    public CategoryDataResponse(Long id, String name, List<SubcategoryData> subcategories) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
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

    public List<SubcategoryData> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryData> subcategories) {
        this.subcategories = subcategories;
    }
}
