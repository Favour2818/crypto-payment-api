package com.favour.cryptopayment.repository;

import com.favour.cryptopayment.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository.
 * MongoRepository gives us save(), findById(), findAll(), delete() etc. for free.
 * We only need to define queries that go beyond the defaults.
 */
@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    // Find all payments from a specific wallet address
    List<Payment> findBySenderAddress(String senderAddress);

    // Find all payments by status (e.g. all PENDING payments)
    List<Payment> findByStatus(Payment.PaymentStatus status);

    // Find payments between two wallets
    List<Payment> findBySenderAddressAndRecipientAddress(
            String senderAddress, String recipientAddress);
}
