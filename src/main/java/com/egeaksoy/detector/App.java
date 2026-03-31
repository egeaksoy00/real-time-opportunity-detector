package com.egeaksoy.detector;

import com.egeaksoy.detector.api.BinanceApiClient;
import com.egeaksoy.detector.api.KlineParser;
import com.egeaksoy.detector.config.CoinConfig;
import com.egeaksoy.detector.model.Candle;
import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.service.MetricsCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
    	List<String> symbols = CoinConfig.getTrackedSymbols();
        BinanceApiClient client = new BinanceApiClient();
        MetricsCalculator calculator = new MetricsCalculator();

        List<CoinMetrics> results = new ArrayList<>();

        for (String symbol : symbols) {
            try {
                String json = client.getKlines(symbol, "5m", 288);
                List<Candle> candles = KlineParser.parse(json);

                CoinMetrics metrics = calculator.calculate(symbol, candles);
                results.add(metrics);

            } catch (Exception e) {
                System.out.println("Error processing " + symbol + ": " + e.getMessage());
            }
        }

        // Sort by volume uplift descending
        results.sort(Comparator.comparingDouble(CoinMetrics::getVolumeUpliftPct).reversed());

        System.out.println("\n=== TOP OPPORTUNITIES ===\n");

        for (int i = 0; i < Math.min(5, results.size()); i++) {
            CoinMetrics m = results.get(i);

            System.out.printf(
                    "%d. %s → Price: %.2f%% | Volume Uplift: %.2f%%%n",
                    i + 1,
                    m.getSymbol(),
                    m.getPriceChange5mPct(),
                    m.getVolumeUpliftPct()
            );
        }

        System.out.println("\n=== FULL LIST ===\n");

        for (CoinMetrics m : results) {
            System.out.printf(
                    "%s → Price: %.2f%% | Volume Uplift: %.2f%%%n",
                    m.getSymbol(),
                    m.getPriceChange5mPct(),
                    m.getVolumeUpliftPct()
            );
        }
    }
}