package com.favour.cryptopayment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class PaymentRequest {

    @NotBlank(message = "Sender address is required")
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "Invalid Ethereum address format")
    private String senderAddress;

    @NotBlank(message = "Recipient address is required")
    @Pattern(regexp = "^0x[a-fA-F0-9]{40}$", message = "Invalid Ethereum address format")
    private String recipientAddress;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0001", message = "Minimum payment is 0.0001 ETH")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    public String getSenderAddress()              { return senderAddress; }
    public void setSenderAddress(String v)        { this.senderAddress = v; }
    public String getRecipientAddress()           { return recipientAddress; }
    public void setRecipientAddress(String v)     { this.recipientAddress = v; }
    public BigDecimal getAmount()                 { return amount; }
    public void setAmount(BigDecimal v)           { this.amount = v; }
    public String getCurrency()                   { return currency; }
    public void setCurrency(String v)             { this.currency = v; }
}
