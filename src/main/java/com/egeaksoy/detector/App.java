package com.egeaksoy.detector;

import java.util.List;

import com.egeaksoy.detector.api.BinanceApiClient;
import com.egeaksoy.detector.api.KlineParser;
import com.egeaksoy.detector.config.CoinConfig;
import com.egeaksoy.detector.config.DetectorConfig;
import com.egeaksoy.detector.model.Candle;

public class App {
	  public static void main(String[] args) throws Exception {

		  BinanceApiClient client = new BinanceApiClient();

	        String json = client.getKlines("BTCUSDT", "5m", 2);

	        List<Candle> candles = KlineParser.parse(json);

	        Candle first = candles.get(0);
	        Candle second = candles.get(1);

	        double priceChange = ((second.getClose() - first.getClose()) / first.getClose()) * 100;

	        System.out.println("Price Change (%): " + priceChange);
	        System.out.println("Base Volume (last candle) as BTC: " + second.getVolume());
	        System.out.println("Quote Volume / USDT (last candle): " + second.getQuoteVolume());
	    }
}