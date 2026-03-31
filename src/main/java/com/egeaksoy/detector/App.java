package com.egeaksoy.detector;

import java.util.List;

import com.egeaksoy.detector.api.BinanceApiClient;
import com.egeaksoy.detector.api.KlineParser;
import com.egeaksoy.detector.config.CoinConfig;
import com.egeaksoy.detector.config.DetectorConfig;
import com.egeaksoy.detector.model.Candle;
import com.egeaksoy.detector.model.CoinMetrics;
import com.egeaksoy.detector.service.MetricsCalculator;

public class App {
	  public static void main(String[] args) throws Exception {

		  String symbol = "BTCUSDT";

	        BinanceApiClient client = new BinanceApiClient();
	        String json = client.getKlines(symbol, "5m", 288);

	        List<Candle> candles = KlineParser.parse(json);

	        MetricsCalculator calculator = new MetricsCalculator();
	        CoinMetrics metrics = calculator.calculate(symbol, candles);

	        System.out.println("Symbol: " + metrics.getSymbol());
	        System.out.printf("5m Price Change: %.2f%%%n", metrics.getPriceChange5mPct());
	        System.out.printf("Current 5m Quote Volume: %.2f%n", metrics.getCurrent5mQuoteVolume());
	        System.out.printf("Avg 24h 5m Quote Volume: %.2f%n", metrics.getAvg24h5mQuoteVolume());
	        System.out.printf("Volume Uplift: %.2f%%%n", metrics.getVolumeUpliftPct());
	    }
}