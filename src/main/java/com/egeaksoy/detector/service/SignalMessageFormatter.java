package com.egeaksoy.detector.service;

import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalResult;
import com.egeaksoy.detector.model.SignalType;

public class SignalMessageFormatter {

    public static String format(SignalResult result) {
        CoinMetrics main = result.getMainMetrics();

        StringBuilder sb = new StringBuilder();

        sb.append(getSignalHeader(result.getSignalType())).append("\n\n");

        sb.append("📊 Symbol: ").append(main.getSymbol()).append("\n");
        sb.append("⚡ Type: ").append(formatType(result.getSignalType())).append("\n");
        sb.append(String.format("🎯 Score: %.2f%n", main.getScore()));
        sb.append(String.format("📉 5m Change: %.2f%%%n", main.getPriceChange5mPct()));
        sb.append(String.format("📈 Volume Uplift: %.2f%%%n", main.getVolumeUpliftPct()));

        if (result.hasCorrelatedMetrics()) {
            sb.append("\n🔗 Correlated:\n");

            for (CoinMetrics correlated : result.getCorrelatedMetricsList()) {
                sb.append(String.format(
                        "• %s | %.2f%% | Vol: %.2f%%%n",
                        correlated.getSymbol(),
                        correlated.getPriceChange5mPct(),
                        correlated.getVolumeUpliftPct()
                ));
            }
        }

        sb.append("\n🧠 ").append(result.getReason());

        return sb.toString();
    }

    private static String getSignalHeader(SignalType type) {
        return switch (type) {
            case LONG_STRONG_TRACK_CANDIDATE -> "🚀 STRONG LONG SETUP";
            case LONG_TRACK_CANDIDATE -> "🟢 LONG SETUP";
            case SHORT_STRONG_TRACK_CANDIDATE -> "⚠️ STRONG SHORT SETUP";
            case SHORT_TRACK_CANDIDATE -> "🔴 SHORT SETUP";
            default -> "📡 SIGNAL";
        };
    }

    private static String formatType(SignalType type) {
        return switch (type) {
            case LONG_STRONG_TRACK_CANDIDATE -> "STRONG LONG";
            case LONG_TRACK_CANDIDATE -> "LONG";
            case SHORT_STRONG_TRACK_CANDIDATE -> "STRONG SHORT";
            case SHORT_TRACK_CANDIDATE -> "SHORT";
            default -> "NONE";
        };
    }
}