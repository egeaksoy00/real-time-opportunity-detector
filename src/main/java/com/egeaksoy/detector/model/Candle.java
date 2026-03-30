package com.egeaksoy.detector.model;

public class Candle {

    private final double open;
    private final double close;
    private final double volume;
    private final double quoteVolume;

    public Candle(double open, double close, double volume, double quoteVolume) {
        this.open = open;
        this.close = close;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public double getQuoteVolume() {
        return quoteVolume;
    }
}