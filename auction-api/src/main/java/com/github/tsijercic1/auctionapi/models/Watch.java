package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;

@Entity
@Table(name = "watches")
public class Watch extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User watcher;

    public Watch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getWatcher() {
        return watcher;
    }

    public void setWatcher(User watcher) {
        this.watcher = watcher;
    }
}
