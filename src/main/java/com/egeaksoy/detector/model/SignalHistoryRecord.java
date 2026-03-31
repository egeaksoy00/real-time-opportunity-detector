package com.egeaksoy.detector.model;

import java.time.LocalDateTime;
import java.util.List;

public class SignalHistoryRecord {

    private final LocalDateTime detectedAt;
    private final String symbol;
    private final SignalType signalType;
    private final double score;
    private final double priceChange5mPct;
    private final double volumeUpliftPct;
    private final List<String> correlatedSymbols;
    private final String reason;

    public SignalHistoryRecord(LocalDateTime detectedAt,
                               String symbol,
                               SignalType signalType,
                               double score,
                               double priceChange5mPct,
                               double volumeUpliftPct,
                               List<String> correlatedSymbols,
                               String reason) {
        this.detectedAt = detectedAt;
        this.symbol = symbol;
        this.signalType = signalType;
        this.score = score;
        this.priceChange5mPct = priceChange5mPct;
        this.volumeUpliftPct = volumeUpliftPct;
        this.correlatedSymbols = correlatedSymbols;
        this.reason = reason;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public String getSymbol() {
        return symbol;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public double getScore() {
        return score;
    }

    public double getPriceChange5mPct() {
        return priceChange5mPct;
    }

    public double getVolumeUpliftPct() {
        return volumeUpliftPct;
    }

    public List<String> getCorrelatedSymbols() {
        return correlatedSymbols;
    }

    public String getReason() {
        return reason;
    }
}