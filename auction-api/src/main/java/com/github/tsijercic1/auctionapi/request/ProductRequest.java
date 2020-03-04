package com.github.tsijercic1.auctionapi.request;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;

public class ProductRequest {
    @NotBlank
    private String name;
    private String description;
    private BigDecimal startPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    @NotBlank
    private String subcategory;


    public ProductRequest() {
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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Instant getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Instant auctionEnd) {
        this.auctionEnd = auctionEnd;
    }
}
