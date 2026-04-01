package com.egeaksoy.detector.model;

public class Candle {

    private final long openTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
    private final long closeTime;
    private final double quoteVolume;

    public Candle(long openTime,
                  double open,
                  double high,
                  double low,
                  double close,
                  double volume,
                  long closeTime,
                  double quoteVolume) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
        this.quoteVolume = quoteVolume;
    }

    public long getOpenTime() {
        return openTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public double getQuoteVolume() {
        return quoteVolume;
    }
}