package com.favour.cryptopayment.controller;

import com.favour.cryptopayment.dto.WalletResponse;
import com.favour.cryptopayment.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{address}/balance")
    public ResponseEntity<WalletResponse> getBalance(@PathVariable String address) {
        return ResponseEntity.ok(walletService.getBalance(address));
    }
}
