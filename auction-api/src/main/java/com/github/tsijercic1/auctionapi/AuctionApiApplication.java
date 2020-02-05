package com.github.tsijercic1.auctionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Jsr310JpaConverters.class
 * registered JPA converter for datatype conversion to sql types
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {
        AuctionApiApplication.class,
        Jsr310JpaConverters.class
})
public class AuctionApiApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AuctionApiApplication.class, args);
    }

}
