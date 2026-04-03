package com.egeaksoy.detector;

import com.egeaksoy.detector.config.CoinConfig;
import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalResult;
import com.egeaksoy.detector.model.SignalType;
import com.egeaksoy.detector.service.AnalysisService;
import com.egeaksoy.detector.service.MetricsCalculator;
import com.egeaksoy.detector.service.SignalEngine;
import com.egeaksoy.detector.service.SignalHistoryService;
import com.egeaksoy.detector.service.SignalMessageFormatter;
import com.egeaksoy.detector.service.TelegramService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static long lastProcessedCandleCloseTime = -1L;

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        System.out.println("Real-Time Opportunity Detector started at " + LocalDateTime.now());
        System.out.println("Scheduler active: scanning every 1 minute...\n");

        scheduler.scheduleAtFixedRate(() -> {
            try {
                runDetectionCycle();
            } catch (Exception e) {
                System.err.println("Detection cycle failed: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private static void runDetectionCycle() {
        System.out.println("\n==================================================");
        System.out.println("New detection cycle started at: " + LocalDateTime.now());
        System.out.println("==================================================");

        AnalysisService analysisService = new AnalysisService();
        SignalEngine signalEngine = new SignalEngine();
        MetricsCalculator metricsCalculator = new MetricsCalculator();

        try {
            CoinMetrics referenceMetrics = analysisService.analyzeSymbol("BTCUSDT");
            long currentCandleCloseTime = referenceMetrics.getAnalyzedCandleCloseTime();

            if (currentCandleCloseTime == lastProcessedCandleCloseTime) {
                System.out.println("No new 5m candle detected. Skipping this cycle.");
                return;
            }

            lastProcessedCandleCloseTime = currentCandleCloseTime;
            System.out.println("New 5m candle detected. Continuing full scan...");

        } catch (Exception e) {
            System.out.println("Failed to check reference candle: " + e.getMessage());
            return;
        }

        List<SignalResult> allResults = new ArrayList<>();

        for (String symbol : CoinConfig.getTrackedSymbols()) {
            try {
                CoinMetrics mainMetrics = analysisService.analyzeSymbol(symbol);
                List<CoinMetrics> correlatedMetricsList = analysisService.analyzeCorrelatedSymbols(symbol);

                double baseScore = metricsCalculator.calculateScore(mainMetrics);
                mainMetrics.setScore(baseScore);

                SignalResult result = signalEngine.evaluate(mainMetrics, correlatedMetricsList);

                double finalScore = mainMetrics.getScore();

                if (result.getSignalType() == SignalType.LONG_STRONG_TRACK_CANDIDATE
                        || result.getSignalType() == SignalType.SHORT_STRONG_TRACK_CANDIDATE) {
                    finalScore *= 1.5;
                } else if (result.getSignalType() == SignalType.LONG_TRACK_CANDIDATE
                        || result.getSignalType() == SignalType.SHORT_TRACK_CANDIDATE) {
                    finalScore *= 1.2;
                }

                mainMetrics.setScore(finalScore);

                allResults.add(result);

                if (result.getSignalType() == SignalType.LONG_TRACK_CANDIDATE
                        || result.getSignalType() == SignalType.LONG_STRONG_TRACK_CANDIDATE
                        || result.getSignalType() == SignalType.SHORT_TRACK_CANDIDATE
                        || result.getSignalType() == SignalType.SHORT_STRONG_TRACK_CANDIDATE) {

                    String message = SignalMessageFormatter.format(result);
                    TelegramService.sendMessage(message);
                }

            } catch (Exception e) {
                System.out.println("Error processing " + symbol + ": " + e.getMessage());
            }
        }

        allResults.sort(Comparator.comparingDouble(
                (SignalResult r) -> r.getMainMetrics().getScore()
        ).reversed());

        long actionableCount = allResults.stream()
                .filter(r -> r.getSignalType() != SignalType.IGNORE)
                .count();

        System.out.println("Cycle completed. Total results: " + allResults.size()
                + " | Actionable signals: " + actionableCount);

        SignalHistoryService.persistSignals(allResults);

        printTopSignals(allResults);
        printTopWatchlist(allResults);
        printFullSignalReview(allResults);
    }

    private static void printTopSignals(List<SignalResult> allResults) {
        System.out.println("\n=== TOP SIGNALS ===\n");

        int rank = 1;
        for (SignalResult result : allResults) {
            if (result.getSignalType() != SignalType.IGNORE) {
                CoinMetrics main = result.getMainMetrics();
                System.out.printf(
                        "%d. %s → %s | Score: %.2f | Price: %.2f%% | Volume Uplift: %.2f%%%n",
                        rank++,
                        main.getSymbol(),
                        result.getSignalType(),
                        main.getScore(),
                        main.getPriceChange5mPct(),
                        main.getVolumeUpliftPct()
                );
            }
        }

        if (rank == 1) {
            System.out.println("No actionable signals detected right now.");
        }
    }

    private static void printTopWatchlist(List<SignalResult> allResults) {
        System.out.println("\n=== TOP ACTIONABLE WATCHLIST ===\n");

        List<SignalResult> watchlist = allResults.stream()
        		.filter(result -> result.getSignalType() != SignalType.IGNORE)
                .limit(5)
                .toList();

        if (watchlist.isEmpty()) {
        	System.out.println("No actionable long or short candidates found.");
            return;
        }

        int rank = 1;
        for (SignalResult result : watchlist) {
            CoinMetrics main = result.getMainMetrics();

            System.out.printf(
                    "%d. %s → Score: %.2f | Price: %.2f%% | Volume Uplift: %.2f%% | Signal: %s%n",
                    rank++,
                    main.getSymbol(),
                    main.getScore(),
                    main.getPriceChange5mPct(),
                    main.getVolumeUpliftPct(),
                    result.getSignalType()
            );
        }
    }

    private static void printFullSignalReview(List<SignalResult> allResults) {
        System.out.println("\n=== FULL SIGNAL REVIEW ===\n");

        for (SignalResult result : allResults) {
            printSignal(result);
        }
    }

    private static void printSignal(SignalResult result) {
        CoinMetrics main = result.getMainMetrics();

        System.out.println("--------------------------------------------------");
        System.out.println("Symbol: " + main.getSymbol());
        System.out.println("Signal: " + result.getSignalType());
        System.out.printf("Score: %.2f%n", main.getScore());
        System.out.printf("5m Price Change: %.2f%%%n", main.getPriceChange5mPct());
        System.out.printf("Volume Uplift: %.2f%%%n", main.getVolumeUpliftPct());
        System.out.printf("Analyzed Candle Close Time: %d%n", main.getAnalyzedCandleCloseTime());

        if (result.hasCorrelatedMetrics()) {
            System.out.println("Correlated Pairs:");

            for (CoinMetrics correlated : result.getCorrelatedMetricsList()) {
                System.out.printf(
                        " - %s | Price: %.2f%% | Volume Uplift: %.2f%%%n",
                        correlated.getSymbol(),
                        correlated.getPriceChange5mPct(),
                        correlated.getVolumeUpliftPct()
                );
            }
        }

        System.out.println("Reason: " + result.getReason());
    }
}