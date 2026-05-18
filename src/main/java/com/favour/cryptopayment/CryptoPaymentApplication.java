package com.favour.cryptopayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching   // activates Redis @Cacheable annotations
@EnableAsync     // allows @Async methods (used in payment processing)
public class CryptoPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoPaymentApplication.class, args);
    }
}
