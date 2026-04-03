package com.egeaksoy.detector.model;

import java.util.List;

public class SignalResult {

    private final CoinMetrics mainMetrics;
    private final List<CoinMetrics> correlatedMetricsList;
    private final SignalType signalType;
    private final String reason;

    public SignalResult(CoinMetrics mainMetrics,
                        List<CoinMetrics> correlatedMetricsList,
                        SignalType signalType,
                        String reason) {
        this.mainMetrics = mainMetrics;
        this.correlatedMetricsList = correlatedMetricsList;
        this.signalType = signalType;
        this.reason = reason;
    }

    public CoinMetrics getMainMetrics() {
        return mainMetrics;
    }

    public List<CoinMetrics> getCorrelatedMetricsList() {
        return correlatedMetricsList;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public String getReason() {
        return reason;
    }

    public boolean hasCorrelatedMetrics() {
        return correlatedMetricsList != null && !correlatedMetricsList.isEmpty();
    }
}