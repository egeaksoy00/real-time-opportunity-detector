package com.egeaksoy.detector.model;

public class CoinMetrics {

    private final String symbol;
    private final double priceChange5mPct;
    private final double current5mQuoteVolume;
    private final double avg24h5mQuoteVolume;
    private final double volumeUpliftPct;

    public CoinMetrics(String symbol,
                       double priceChange5mPct,
                       double current5mQuoteVolume,
                       double avg24h5mQuoteVolume,
                       double volumeUpliftPct) {
        this.symbol = symbol;
        this.priceChange5mPct = priceChange5mPct;
        this.current5mQuoteVolume = current5mQuoteVolume;
        this.avg24h5mQuoteVolume = avg24h5mQuoteVolume;
        this.volumeUpliftPct = volumeUpliftPct;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPriceChange5mPct() {
        return priceChange5mPct;
    }

    public double getCurrent5mQuoteVolume() {
        return current5mQuoteVolume;
    }

    public double getAvg24h5mQuoteVolume() {
        return avg24h5mQuoteVolume;
    }

    public double getVolumeUpliftPct() {
        return volumeUpliftPct;
    }
}