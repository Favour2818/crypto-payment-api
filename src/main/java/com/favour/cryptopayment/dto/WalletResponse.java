package com.favour.cryptopayment.dto;

import java.math.BigDecimal;

public class WalletResponse {
    private String address;
    private BigDecimal balanceEth;
    private String balanceWei;
    private boolean cached;

    public WalletResponse() {}

    public WalletResponse(String address, BigDecimal balanceEth, String balanceWei, boolean cached) {
        this.address = address;
        this.balanceEth = balanceEth;
        this.balanceWei = balanceWei;
        this.cached = cached;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String address;
        private BigDecimal balanceEth;
        private String balanceWei;
        private boolean cached;

        public Builder address(String v)      { this.address = v; return this; }
        public Builder balanceEth(BigDecimal v){ this.balanceEth = v; return this; }
        public Builder balanceWei(String v)   { this.balanceWei = v; return this; }
        public Builder cached(boolean v)      { this.cached = v; return this; }

        public WalletResponse build() {
            return new WalletResponse(address, balanceEth, balanceWei, cached);
        }
    }

    public String getAddress()        { return address; }
    public void setAddress(String v)  { this.address = v; }
    public BigDecimal getBalanceEth() { return balanceEth; }
    public void setBalanceEth(BigDecimal v) { this.balanceEth = v; }
    public String getBalanceWei()     { return balanceWei; }
    public void setBalanceWei(String v){ this.balanceWei = v; }
    public boolean isCached()         { return cached; }
    public void setCached(boolean v)  { this.cached = v; }
}
