# 🚀 Real-Time Opportunity Detector

A real-time crypto market monitoring system that detects abnormal price and volume movements using Binance market data.

## 📌 Overview

This project analyzes short-term market behavior (5-minute intervals) to identify potential trading opportunities based on:

- Sudden price changes
- Volume spikes
- Correlated asset movement

The system is designed to evolve into a full real-time alert engine with automation and analytics capabilities.

---

## ⚙️ Features

- 🔗 Binance API integration (Kline data)
- 📊 5-minute candle analysis
- 📈 Price change calculation (%)
- 💰 Volume tracking (base + quote volume)
- 🧠 Modular architecture for future signal engine
- ⚡ Lightweight and real-time ready

---

## 🏗️ Architecture

com.egeaksoy.detector
│
├── api        → Binance API client & parsing
├── config     → System configuration (coins, thresholds)
├── model      → Data models (Candle)
└── App        → Entry point

---

## 📊 Current Implementation

- Fetches latest 5m candles from Binance
- Parses JSON into structured Candle objects
- Calculates:
  - Price change (%)
  - Volume (base & USDT)

---

## 🔜 Roadmap

- [ ] Volume baseline calculation (24h avg)
- [ ] Volume spike detection
- [ ] Signal engine (price + volume anomaly)
- [ ] Correlated asset tracking
- [ ] Telegram / Notification system
- [ ] Web dashboard for signal history

---

## 🛠️ Tech Stack

- Java 17
- Maven
- Binance REST API
- JSON parsing

---

## 📈 Goal

To build a scalable real-time detection system that can:

- Identify early market moves
- Detect hidden opportunities
- Serve as a foundation for algorithmic trading systems

---

## 👨‍💻 Author

Ege Aksoy
