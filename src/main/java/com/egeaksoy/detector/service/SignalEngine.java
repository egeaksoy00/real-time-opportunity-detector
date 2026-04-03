package com.egeaksoy.detector.service;

import com.egeaksoy.detector.config.DetectorConfig;
import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalResult;
import com.egeaksoy.detector.model.SignalType;

import java.util.List;

public class SignalEngine {

    public SignalResult evaluate(CoinMetrics mainMetrics, List<CoinMetrics> correlatedMetricsList) {

        double mainPrice = mainMetrics.getPriceChange5mPct();
        double mainVolume = mainMetrics.getVolumeUpliftPct();

        boolean longMainPassed =
                mainPrice >= DetectorConfig.getMainPriceChangeMin()
                        && mainVolume >= DetectorConfig.getMainVolumeUpliftMin();

        boolean shortMainPassed =
                mainPrice <= -DetectorConfig.getMainPriceChangeMin()
                        && mainVolume >= DetectorConfig.getMainVolumeUpliftMin();

        if (!longMainPassed && !shortMainPassed) {
            return new SignalResult(
                    mainMetrics,
                    correlatedMetricsList,
                    SignalType.IGNORE,
                    "Main coin did not pass price and volume thresholds."
            );
        }

        boolean isLong = longMainPassed;
        boolean correlatedConfirmed = false;

        for (CoinMetrics correlated : correlatedMetricsList) {
            double correlatedPrice = correlated.getPriceChange5mPct();
            double correlatedVolume = correlated.getVolumeUpliftPct();

            boolean correlatedPass;

            if (isLong) {
                correlatedPass =
                        correlatedPrice >= DetectorConfig.getCorrelatedPriceChangeMin()
                                && correlatedVolume >= DetectorConfig.getCorrelatedVolumeUpliftMin();
            } else {
                correlatedPass =
                        correlatedPrice <= -DetectorConfig.getCorrelatedPriceChangeMin()
                                && correlatedVolume >= DetectorConfig.getCorrelatedVolumeUpliftMin();
            }

            if (correlatedPass) {
                correlatedConfirmed = true;
                break;
            }
        }

        if (isLong) {
            if (correlatedMetricsList.isEmpty()) {
                return new SignalResult(
                        mainMetrics,
                        correlatedMetricsList,
                        SignalType.LONG_TRACK_CANDIDATE,
                        "Main coin passed LONG thresholds. No correlated pair defined."
                );
            }

            if (correlatedConfirmed) {
                return new SignalResult(
                        mainMetrics,
                        correlatedMetricsList,
                        SignalType.LONG_STRONG_TRACK_CANDIDATE,
                        "Main coin passed LONG thresholds and at least one correlated pair confirmed the move."
                );
            }

            return new SignalResult(
                    mainMetrics,
                    correlatedMetricsList,
                    SignalType.LONG_TRACK_CANDIDATE,
                    "Main coin passed LONG thresholds but no correlated pair confirmed strongly enough."
            );
        }

        if (correlatedMetricsList.isEmpty()) {
            return new SignalResult(
                    mainMetrics,
                    correlatedMetricsList,
                    SignalType.SHORT_TRACK_CANDIDATE,
                    "Main coin passed SHORT thresholds. No correlated pair defined."
            );
        }

        if (correlatedConfirmed) {
            return new SignalResult(
                    mainMetrics,
                    correlatedMetricsList,
                    SignalType.SHORT_STRONG_TRACK_CANDIDATE,
                    "Main coin passed SHORT thresholds and at least one correlated pair confirmed the move."
            );
        }

        return new SignalResult(
                mainMetrics,
                correlatedMetricsList,
                SignalType.SHORT_TRACK_CANDIDATE,
                "Main coin passed SHORT thresholds but no correlated pair confirmed strongly enough."
        );
    }
}