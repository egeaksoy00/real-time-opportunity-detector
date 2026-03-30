package com.egeaksoy.detector.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BinanceApiClient {

    private static final String BASE_URL = "https://api.binance.com/api/v3/klines";

    private final HttpClient client;

    public BinanceApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String getKlines(String symbol, String interval, int limit)
            throws IOException, InterruptedException {

        String url = BASE_URL +
                "?symbol=" + symbol +
                "&interval=" + interval +
                "&limit=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}