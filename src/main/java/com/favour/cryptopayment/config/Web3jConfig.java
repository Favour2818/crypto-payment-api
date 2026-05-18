package com.favour.cryptopayment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Value("${app.alchemy-url}")
    private String alchemyUrl;

    /**
     * Web3j is the Java library for talking to Ethereum nodes.
     * We point it at Alchemy, which acts as a managed Ethereum node
     * so we don't have to run our own (free tier is plenty for dev/testing).
     *
     * Once injected, you can call:
     *   web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send()
     *   web3j.ethGetTransactionCount(address, ...).send()
     *   etc.
     */
    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(alchemyUrl));
    }
}
