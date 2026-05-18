package com.favour.cryptopayment.worker;

import com.favour.cryptopayment.model.Payment;
import com.favour.cryptopayment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentWorker {

    private static final Logger log = LoggerFactory.getLogger(PaymentWorker.class);

    private final PaymentRepository paymentRepository;

    public PaymentWorker(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void processPayment(Payment payment) {
        log.info("Worker received payment: {} — amount: {} {}",
                payment.getId(), payment.getAmount(), payment.getCurrency());

        try {
            updateStatus(payment, Payment.PaymentStatus.PROCESSING, null);

            // Simulate blockchain processing delay
            // In production: broadcast transaction via Web3j here
            Thread.sleep(2000);

            String simulatedTxHash = "0x" + Long.toHexString(System.currentTimeMillis())
                    + payment.getId().substring(0, 8);

            payment.setTxHash(simulatedTxHash);
            updateStatus(payment, Payment.PaymentStatus.COMPLETED, null);

            log.info("Payment {} completed. TxHash: {}", payment.getId(), simulatedTxHash);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Payment {} interrupted", payment.getId());
            updateStatus(payment, Payment.PaymentStatus.FAILED, "Processing interrupted");

        } catch (Exception e) {
            log.error("Payment {} failed: {}", payment.getId(), e.getMessage());
            updateStatus(payment, Payment.PaymentStatus.FAILED, e.getMessage());
        }
    }

    private void updateStatus(Payment payment, Payment.PaymentStatus status, String errorMessage) {
        payment.setStatus(status);
        payment.setErrorMessage(errorMessage);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
