package com.github.tsijercic1.auctionapi.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductDataResponse {
    private Long id;
    private String name;
    private String description;
    private CategoryDataResponse category;
    private BigDecimal startPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    private List<String> pictures;

    public ProductDataResponse() {
    }

    public ProductDataResponse(Long id, String name, String description, CategoryDataResponse category, BigDecimal startPrice, Instant auctionStart, Instant auctionEnd, List<String> pictures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.startPrice = startPrice;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.pictures = pictures;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDataResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryDataResponse category) {
        this.category = category;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Instant getAuctionStart() {
        return auctionStart;
    }

    public void setAuctionStart(Instant auctionStart) {
        this.auctionStart = auctionStart;
    }

    public Instant getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Instant auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
