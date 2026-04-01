package com.egeaksoy.detector.api;

import com.egeaksoy.detector.model.Candle;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class KlineParser {

    public static List<Candle> parse(String json) {
        List<Candle> candles = new ArrayList<>();

        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONArray kline = array.getJSONArray(i);

            long openTime = kline.getLong(0);
            double open = Double.parseDouble(kline.getString(1));
            double high = Double.parseDouble(kline.getString(2));
            double low = Double.parseDouble(kline.getString(3));
            double close = Double.parseDouble(kline.getString(4));
            double volume = Double.parseDouble(kline.getString(5));
            long closeTime = kline.getLong(6);
            double quoteVolume = Double.parseDouble(kline.getString(7));

            candles.add(new Candle(
                    openTime,
                    open,
                    high,
                    low,
                    close,
                    volume,
                    closeTime,
                    quoteVolume
            ));
        }

        return candles;
    }
}