package com.egeaksoy.detector.config;

import java.util.Map;

public class PairCorrelationConfig {

    public static Map<String, String> getCorrelatedPairs() {
        return Map.of(
                "LAZIOUSDT", "PORTOUSDT",
                "CITYUSDT", "PSGUSDT",
                "MANAUSDT", "SANDUSDT",
                "ENJUSDT", "SANDUSDT",
                "SOLUSDT","JUPUSDT",
                "AVAXUSDT","JOEUSDT",
                "ENSUSDT","ETHUSDT"
        );
    }
}