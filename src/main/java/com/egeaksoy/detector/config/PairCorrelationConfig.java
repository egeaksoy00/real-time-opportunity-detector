package com.egeaksoy.detector.config;

import java.util.Map;

public class PairCorrelationConfig {

    public static Map<String, String> getCorrelatedPairs() {
        return Map.of(
                "SANTOSUSDT", "PORTOUSDT",
                "AVAXUSDT", "JOEUSDT",
                "SOLUSDT", "RAYUSDT",
                "CITYUSDT", "PSGUSDT",
                "LOKAUSDT", "VOXELUSDT"
        );
    }
}