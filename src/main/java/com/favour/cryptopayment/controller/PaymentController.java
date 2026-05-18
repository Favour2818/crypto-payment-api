package com.favour.cryptopayment.controller;

import com.favour.cryptopayment.dto.PaymentRequest;
import com.favour.cryptopayment.model.Payment;
import com.favour.cryptopayment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> submitPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentService.submitPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments(@RequestParam(required = false) String sender) {
        List<Payment> payments = (sender != null)
                ? paymentService.getPaymentsBySender(sender)
                : paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}
