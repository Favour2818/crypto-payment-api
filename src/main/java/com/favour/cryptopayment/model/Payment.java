package com.favour.cryptopayment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
public class Payment {

    @Id
    private String id;
    private String senderAddress;
    private String recipientAddress;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private String txHash;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PaymentStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }

    // ── No-arg constructor (required by MongoDB) ─────────────────────────
    public Payment() {}

    // ── All-arg constructor ───────────────────────────────────────────────
    public Payment(String id, String senderAddress, String recipientAddress,
                   BigDecimal amount, String currency, PaymentStatus status,
                   String txHash, String errorMessage,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.txHash = txHash;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ── Builder ───────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id;
        private String senderAddress;
        private String recipientAddress;
        private BigDecimal amount;
        private String currency;
        private PaymentStatus status;
        private String txHash;
        private String errorMessage;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(String id)                         { this.id = id; return this; }
        public Builder senderAddress(String v)               { this.senderAddress = v; return this; }
        public Builder recipientAddress(String v)            { this.recipientAddress = v; return this; }
        public Builder amount(BigDecimal v)                  { this.amount = v; return this; }
        public Builder currency(String v)                    { this.currency = v; return this; }
        public Builder status(PaymentStatus v)               { this.status = v; return this; }
        public Builder txHash(String v)                      { this.txHash = v; return this; }
        public Builder errorMessage(String v)                { this.errorMessage = v; return this; }
        public Builder createdAt(LocalDateTime v)            { this.createdAt = v; return this; }
        public Builder updatedAt(LocalDateTime v)            { this.updatedAt = v; return this; }

        public Payment build() {
            return new Payment(id, senderAddress, recipientAddress, amount, currency,
                               status, txHash, errorMessage, createdAt, updatedAt);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────
    public String getId()                        { return id; }
    public void setId(String id)                 { this.id = id; }
    public String getSenderAddress()             { return senderAddress; }
    public void setSenderAddress(String v)       { this.senderAddress = v; }
    public String getRecipientAddress()          { return recipientAddress; }
    public void setRecipientAddress(String v)    { this.recipientAddress = v; }
    public BigDecimal getAmount()                { return amount; }
    public void setAmount(BigDecimal v)          { this.amount = v; }
    public String getCurrency()                  { return currency; }
    public void setCurrency(String v)            { this.currency = v; }
    public PaymentStatus getStatus()             { return status; }
    public void setStatus(PaymentStatus v)       { this.status = v; }
    public String getTxHash()                    { return txHash; }
    public void setTxHash(String v)              { this.txHash = v; }
    public String getErrorMessage()              { return errorMessage; }
    public void setErrorMessage(String v)        { this.errorMessage = v; }
    public LocalDateTime getCreatedAt()          { return createdAt; }
    public void setCreatedAt(LocalDateTime v)    { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()          { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v)    { this.updatedAt = v; }
}
