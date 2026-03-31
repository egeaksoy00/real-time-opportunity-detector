package com.egeaksoy.detector.service;

import com.egeaksoy.detector.api.BinanceApiClient;
import com.egeaksoy.detector.api.KlineParser;
import com.egeaksoy.detector.config.DetectorConfig;
import com.egeaksoy.detector.config.PairCorrelationConfig;
import com.egeaksoy.detector.model.Candle;
import com.egeaksoy.detector.model.CoinMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalysisService {

    private final BinanceApiClient apiClient;
    private final MetricsCalculator metricsCalculator;
    private final Map<String, List<String>> correlationMap;

    public AnalysisService() {
        this.apiClient = new BinanceApiClient();
        this.metricsCalculator = new MetricsCalculator();
        this.correlationMap = PairCorrelationConfig.getCorrelatedPairs();
    }

    public CoinMetrics analyzeSymbol(String symbol) throws Exception {
        String json = apiClient.getKlines(symbol, DetectorConfig.INTERVAL, DetectorConfig.BASELINE_CANDLE_COUNT);
        List<Candle> candles = KlineParser.parse(json);
        return metricsCalculator.calculate(symbol, candles);
    }

    public List<CoinMetrics> analyzeCorrelatedSymbols(String symbol) throws Exception {
        List<String> correlatedSymbols = correlationMap.get(symbol);

        if (correlatedSymbols == null || correlatedSymbols.isEmpty()) {
            return List.of();
        }

        List<CoinMetrics> correlatedMetricsList = new ArrayList<>();

        for (String correlatedSymbol : correlatedSymbols) {
            CoinMetrics metrics = analyzeSymbol(correlatedSymbol);
            correlatedMetricsList.add(metrics);
        }

        return correlatedMetricsList;
    }
}