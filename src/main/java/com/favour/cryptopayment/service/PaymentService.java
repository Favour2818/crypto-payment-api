package com.favour.cryptopayment.service;

import com.favour.cryptopayment.dto.PaymentRequest;
import com.favour.cryptopayment.model.Payment;
import com.favour.cryptopayment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public PaymentService(PaymentRepository paymentRepository, RabbitTemplate rabbitTemplate) {
        this.paymentRepository = paymentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Payment submitPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .senderAddress(request.getSenderAddress())
                .recipientAddress(request.getRecipientAddress())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(Payment.PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Payment {} saved with status PENDING", saved.getId());

        rabbitTemplate.convertAndSend(exchange, routingKey, saved);
        log.info("Payment {} published to queue", saved.getId());

        return saved;
    }

    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public List<Payment> getPaymentsBySender(String senderAddress) {
        return paymentRepository.findBySenderAddress(senderAddress);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
