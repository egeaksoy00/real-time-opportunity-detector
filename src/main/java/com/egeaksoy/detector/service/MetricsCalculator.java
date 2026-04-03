package com.egeaksoy.detector.service;

import com.egeaksoy.detector.model.Candle;
import com.egeaksoy.detector.model.CoinMetrics;

import java.util.List;

public class MetricsCalculator {

    public CoinMetrics calculate(String symbol, List<Candle> candles) {
        if (candles == null || candles.isEmpty()) {
            throw new IllegalArgumentException("No candle data found for " + symbol);
        }

        Candle latest = candles.get(candles.size() - 1);

        double priceChangePct = ((latest.getClose() - latest.getOpen()) / latest.getOpen()) * 100.0;
        double current5mQuoteVolume = latest.getQuoteVolume();

        double avg24h5mQuoteVolume = candles.stream()
                .mapToDouble(Candle::getQuoteVolume)
                .average()
                .orElse(0.0);

        double volumeUpliftPct = avg24h5mQuoteVolume == 0
                ? 0
                : ((current5mQuoteVolume - avg24h5mQuoteVolume) / avg24h5mQuoteVolume) * 100.0;

        return new CoinMetrics(
                symbol,
                priceChangePct,
                current5mQuoteVolume,
                avg24h5mQuoteVolume,
                volumeUpliftPct,
                latest.getCloseTime()
        );
    }

    public double calculateScore(CoinMetrics metrics) {
        double price = Math.abs(metrics.getPriceChange5mPct());
        double volume = metrics.getVolumeUpliftPct();

        if (volume < 0) {
            volume = 0;
        }

        return (price * 0.7) + (volume * 0.3);
    }
}