package com.egeaksoy.detector.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TelegramService {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

    public static void sendMessage(String text) {
        try {
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String payload = String.format(
                    "{\"chat_id\":\"%s\",\"text\":\"%s\"}",
                    CHAT_ID,
                    text.replace("\"", "'")
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("BOT TOKEN NULL? " + (BOT_TOKEN == null));
            System.out.println("CHAT ID NULL? " + (CHAT_ID == null));

            if (responseCode != 200) {
                System.out.println("Telegram error: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Telegram send failed: " + e.getMessage());
        }
    }
}