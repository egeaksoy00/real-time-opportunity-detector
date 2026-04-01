package com.egeaksoy.detector.config;

import java.util.List;
import java.util.Map;

public class PairCorrelationConfig {

    public static Map<String, List<String>> getCorrelatedPairs() {
        return Map.ofEntries(
                Map.entry("MANAUSDT", List.of("SANDUSDT", "ENJUSDT")),
                Map.entry("SANDUSDT", List.of("MANAUSDT", "ENJUSDT")),
                Map.entry("ENJUSDT", List.of("MANAUSDT", "SANDUSDT")),

                Map.entry("LAZIOUSDT", List.of("PORTOUSDT")),
                Map.entry("PORTOUSDT", List.of("LAZIOUSDT")),

                Map.entry("CITYUSDT", List.of("PSGUSDT")),
                Map.entry("PSGUSDT", List.of("CITYUSDT")),

                Map.entry("AVAXUSDT", List.of("JOEUSDT")),
                Map.entry("JOEUSDT", List.of("AVAXUSDT")),

                Map.entry("ENSUSDT", List.of("ETHUSDT")),
                Map.entry("ETHUSDT", List.of("ENSUSDT")),
                Map.entry("SOLUSDT", List.of("JUPUSDT")),
                Map.entry("JUPUSDT", List.of("SOLUSDT")),
                Map.entry("TWTUSDT", List.of("C98USDT")),
                Map.entry("C98USDT", List.of("TWTUSDT"))
        );
    }
}