# 🚀 Real-Time Opportunity Detector

A real-time crypto signal detection system that identifies **long and short trading opportunities** using price action and volume dynamics from Binance market data.

## 📌 Overview

This project analyzes short-term market behavior (5-minute intervals) to detect potential trading setups based on:

- Price momentum (positive / negative)
- Volume expansion
- Directional signal logic (long & short)

The system continuously scans selected assets and generates actionable signals, which are then stored and visualized in a live dashboard.

---

## ⚙️ Features

  -  Binance API integration (Kline data)
  -  5-minute candle analysis
  -  Real-time price change tracking (%)
  -  Volume uplift detection
  -  Directional signal engine:
  -  LONG signals (price ↑ + volume ↑)
  -  SHORT signals (price ↓ + volume ↑)
  -  Signal scoring system
  -  Signal history logging (JSON-based)
  -  Telegram-ready signal output format
  -  Live dashboard integration

---

## 🏗 ️ Architecture

com.egeaksoy.detector
│
├── api        → Binance API client & kline parsing
├── config     → System configuration (coins, thresholds)
├── model      → Data models (Candle, SignalResult)
├── service    → Signal detection & processing logic
└── App        → Scheduler & entry point

---

---

## 📊 Current Implementation

- Continuously scans market data in **1-minute cycles**
- Detects new 5-minute candles
- Calculates:
  - Price change (%)
  - Volume uplift (%)
- Generates signals:
  - `LONG_TRACK_CANDIDATE`
  - `SHORT_TRACK_CANDIDATE`
- Scores and ranks opportunities
- Stores results in `signal-history.json`
- Feeds data into a live dashboard

---

## 🌐 Live Dashboard

👉 **Live Demo:**  
https://egeaksoy.net/opportunitydashboard/

> Note: Signals were generated during scheduled test runs rather than continuous real-time operation.

---

## 🧠 Signal Logic

### Long Signal
- Price increasing
- Volume increasing  
→ Indicates potential upward momentum

### Short Signal
- Price decreasing
- Volume increasing  
→ Indicates potential downward pressure

---

## 🛠️ Tech Stack

- Java 17
- Maven
- Binance REST API
- JSON (Jackson)
- Frontend: HTML, CSS, JavaScript
- Deployment: Vercel

---

## 📈 Goal

To build a scalable real-time detection system that:

- Identifies early market movements
- Generates actionable trading signals
- Tracks signal performance over time
- Serves as a foundation for automated trading systems

---

## 🔜 Future Improvements

- [ ] Signal performance tracking (win rate, returns)
- [ ] Continuous deployment & 24/7 execution
- [ ] Advanced scoring & filtering
- [ ] Multi-timeframe analysis
- [ ] Notification system (Telegram bot)
- [ ] Backend API for dashboard

---

## 👨‍💻 Author

Ege Aksoy
