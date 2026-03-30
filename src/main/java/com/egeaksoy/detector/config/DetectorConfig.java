package com.egeaksoy.detector.config;

public class DetectorConfig {

    public enum Mode {
        TEST,
        PRODUCTION
    }

    private static final Mode CURRENT_MODE = Mode.TEST;

    public static Mode getMode() {
        return CURRENT_MODE;
    }

    // Threshold getters

    public static double getMainPriceChangeMin() {
        return CURRENT_MODE == Mode.TEST ? 0.5 : 2.0;
    }

    public static double getMainVolumeUpliftMin() {
        return CURRENT_MODE == Mode.TEST ? 1.0 : 5.0;
    }

    public static double getCorrelatedPriceChangeMin() {
        return CURRENT_MODE == Mode.TEST ? 0.2 : 1.0;
    }

    public static double getCorrelatedVolumeUpliftMin() {
        return CURRENT_MODE == Mode.TEST ? 0.5 : 3.0;
    }

    // Kline config
    public static final String INTERVAL = "5m";
    public static final int BASELINE_CANDLE_COUNT = 288;
}