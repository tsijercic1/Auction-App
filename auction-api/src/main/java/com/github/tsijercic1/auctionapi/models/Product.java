package com.github.tsijercic1.auctionapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private BigDecimal startPrice;
    private Instant auctionStart;
    private Instant auctionEnd;

    @ManyToOne
    @JsonManagedReference
    private Subcategory subcategory;

    @ManyToOne
    @JsonManagedReference
    private User seller;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<ProductPicture> pictures = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<Bid> bids = new HashSet<>( );

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    @JsonBackReference
    private Set<Watch> watchList = new HashSet<>( );

    public Product() {
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

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Set<ProductPicture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<ProductPicture> pictures) {
        this.pictures = pictures;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<Watch> getWatchList() {
        return watchList;
    }

    public void setWatchList(Set<Watch> watchList) {
        this.watchList = watchList;
    }
}
