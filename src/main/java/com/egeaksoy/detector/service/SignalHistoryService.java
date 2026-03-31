package com.egeaksoy.detector.service;

import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.model.SignalHistoryRecord;
import com.egeaksoy.detector.model.SignalResult;
import com.egeaksoy.detector.model.SignalType;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SignalHistoryService {

    private static final String FILE_PATH = "signal-history.json";

    public static void persistSignals(List<SignalResult> results) {

        List<SignalHistoryRecord> records = new ArrayList<>();

        for (SignalResult result : results) {

            if (result.getSignalType() == SignalType.IGNORE) {
                continue;
            }

            CoinMetrics main = result.getMainMetrics();

            List<String> correlatedSymbols = new ArrayList<>();
            if (result.hasCorrelatedMetrics()) {
                correlatedSymbols = result.getCorrelatedMetricsList()
                        .stream()
                        .map(CoinMetrics::getSymbol)
                        .collect(Collectors.toList());
            }

            SignalHistoryRecord record = new SignalHistoryRecord(
                    LocalDateTime.now(),
                    main.getSymbol(),
                    result.getSignalType(),
                    main.getScore(),
                    main.getPriceChange5mPct(),
                    main.getVolumeUpliftPct(),
                    correlatedSymbols,
                    result.getReason()
            );

            records.add(record);
        }

        writeToJson(records);
    }

    private static void writeToJson(List<SignalHistoryRecord> records) {

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            for (SignalHistoryRecord r : records) {

                String correlatedJson = r.getCorrelatedSymbols().stream()
                        .map(symbol -> "\"" + symbol + "\"")
                        .collect(java.util.stream.Collectors.joining(", ", "[", "]"));

                String json = String.format(
                        "{\"time\":\"%s\",\"symbol\":\"%s\",\"signal\":\"%s\",\"score\":%.2f,\"price\":%.4f,\"volume\":%.2f,\"correlated\":%s,\"reason\":\"%s\"}%n",
                        r.getDetectedAt(),
                        r.getSymbol(),
                        r.getSignalType(),
                        r.getScore(),
                        r.getPriceChange5mPct(),
                        r.getVolumeUpliftPct(),
                        correlatedJson,
                        r.getReason().replace("\"", "'")
                );

                writer.write(json);
            }

        } catch (IOException e) {
            System.err.println("Error writing signal history: " + e.getMessage());
        }
    }
}