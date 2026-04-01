package com.egeaksoy.detector.service;

import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalResult;

public class SignalMessageFormatter {

    public static String format(SignalResult result) {
        CoinMetrics main = result.getMainMetrics();

        StringBuilder sb = new StringBuilder();

        sb.append("🚨 SIGNAL DETECTED").append("\n\n");
        sb.append("Symbol: ").append(main.getSymbol()).append("\n");
        sb.append("Type: ").append(result.getSignalType()).append("\n");
        sb.append(String.format("Score: %.2f%n", main.getScore()));
        sb.append(String.format("5m Price Change: %.2f%%%n", main.getPriceChange5mPct()));
        sb.append(String.format("Volume Uplift: %.2f%%%n", main.getVolumeUpliftPct()));

        if (result.hasCorrelatedMetrics()) {
            sb.append("\nCorrelated Pairs:\n");

            for (CoinMetrics correlated : result.getCorrelatedMetricsList()) {
                sb.append(String.format(
                        "- %s | Price: %.2f%% | Volume: %.2f%%%n",
                        correlated.getSymbol(),
                        correlated.getPriceChange5mPct(),
                        correlated.getVolumeUpliftPct()
                ));
            }
        }

        sb.append("\nReason: ").append(result.getReason());

        return sb.toString();
    }
}