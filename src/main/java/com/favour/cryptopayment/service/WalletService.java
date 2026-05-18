package com.favour.cryptopayment.service;

import com.favour.cryptopayment.dto.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class WalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final Web3j web3j;

    public WalletService(Web3j web3j) {
        this.web3j = web3j;
    }

    @Cacheable(value = "walletBalance", key = "#address")
    public WalletResponse getBalance(String address) {
        log.info("Cache miss — fetching balance from Ethereum for address: {}", address);
        try {
            BigInteger balanceWei = web3j
                    .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                    .send()
                    .getBalance();

            BigDecimal balanceEth = Convert.fromWei(
                    new BigDecimal(balanceWei), Convert.Unit.ETHER);

            return WalletResponse.builder()
                    .address(address)
                    .balanceEth(balanceEth)
                    .balanceWei(balanceWei.toString())
                    .cached(false)
                    .build();

        } catch (Exception e) {
            log.error("Failed to fetch balance for address {}: {}", address, e.getMessage());
            throw new RuntimeException("Could not retrieve wallet balance: " + e.getMessage());
        }
    }
}
