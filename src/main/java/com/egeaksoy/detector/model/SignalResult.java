package com.egeaksoy.detector.model;

import java.util.List;

public class SignalResult {

    private final SignalType signalType;
    private final CoinMetrics mainMetrics;
    private final List<CoinMetrics> correlatedMetricsList;
    private final String reason;

    public SignalResult(SignalType signalType,
                        CoinMetrics mainMetrics,
                        List<CoinMetrics> correlatedMetricsList,
                        String reason) {
        this.signalType = signalType;
        this.mainMetrics = mainMetrics;
        this.correlatedMetricsList = correlatedMetricsList;
        this.reason = reason;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public CoinMetrics getMainMetrics() {
        return mainMetrics;
    }

    public List<CoinMetrics> getCorrelatedMetricsList() {
        return correlatedMetricsList;
    }

    public String getReason() {
        return reason;
    }

    public boolean hasCorrelatedMetrics() {
        return correlatedMetricsList != null && !correlatedMetricsList.isEmpty();
    }
}