package com.egeaksoy.detector.service;

import com.egeaksoy.detector.config.DetectorConfig;
import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalResult;
import com.egeaksoy.detector.model.SignalType;

import java.util.List;

public class SignalEngine {

    public SignalResult evaluate(CoinMetrics mainMetrics, List<CoinMetrics> correlatedMetricsList) {

        boolean mainSignal =
                mainMetrics.getPriceChange5mPct() >= DetectorConfig.getMainPriceChangeMin()
                        && mainMetrics.getVolumeUpliftPct() >= DetectorConfig.getMainVolumeUpliftMin();

        if (!mainSignal) {
            return new SignalResult(
                    SignalType.IGNORE,
                    mainMetrics,
                    correlatedMetricsList,
                    "Main coin did not pass price and volume thresholds."
            );
        }

        if (correlatedMetricsList == null || correlatedMetricsList.isEmpty()) {
            return new SignalResult(
                    SignalType.TRACK_CANDIDATE,
                    mainMetrics,
                    List.of(),
                    "Main coin passed thresholds. No correlated pair defined."
            );
        }

        boolean anyCorrelatedConfirmation = correlatedMetricsList.stream().anyMatch(metrics ->
                metrics.getPriceChange5mPct() >= DetectorConfig.getCorrelatedPriceChangeMin()
                        && metrics.getVolumeUpliftPct() >= DetectorConfig.getCorrelatedVolumeUpliftMin()
        );

        if (anyCorrelatedConfirmation) {
            return new SignalResult(
                    SignalType.STRONG_TRACK_CANDIDATE,
                    mainMetrics,
                    correlatedMetricsList,
                    "Main coin passed thresholds and at least one correlated pair confirmed the move."
            );
        }

        return new SignalResult(
                SignalType.TRACK_CANDIDATE,
                mainMetrics,
                correlatedMetricsList,
                "Main coin passed thresholds but no correlated pair confirmed strongly enough."
        );
    }
}