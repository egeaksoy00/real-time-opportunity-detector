package com.egeaksoy.detector.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class BinanceApiClient {

    private static final String BASE_URL = "https://api.binance.com/api/v3/klines";
    private final HttpClient client;

    public BinanceApiClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String getKlines(String symbol, String interval, int limit)
            throws IOException, InterruptedException {

        String url = BASE_URL +
                "?symbol=" + symbol +
                "&interval=" + interval +
                "&limit=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Binance API returned status " + response.statusCode() + " for " + symbol);
        }

        return response.body();
    }
}