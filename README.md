# TCP Client-Server Chat Application

This is a simple **TCP-based two-way chat application** developed using **Java Socket Programming** and **Swing GUI**. It consists of two independent programs — one acts as the **Server** and the other as the **Client** — communicating over a socket connection.

> 💬 Built as part of a hands-on learning project focused on core networking concepts in Java.

---

## ✨ Features

- 📡 Real-time messaging between client and server
- 🖥️ Clean GUI interface built with Swing
- 🔌 Socket connection over TCP (port 5000)
- 📤 Bidirectional chat until either side sends `"bye"`
- ⚠️ Graceful shutdown and connection handling
- 🧠 Simple logic with clearly separated responsibilities

---

---

## 🧑‍💻 Technologies Used

- Java
- Java Swing (for GUI)
- Java I/O Streams
- Java Socket API (TCP)

---

## 🚀 How to Run

### ✅ Prerequisites

- JDK 8 or above installed
- Any IDE (like IntelliJ, Eclipse, VS Code) OR run from terminal

---

### 🔹 Step 1: Run the Server

1. Open `TCPServerGUI.java`
2. Compile and run
3. Server will start and wait for a client on port `5000`

```bash
javac TCPServerGUI.java
java TCPServerGUI
🔹 Step 2: Run the Client
Open TCPClientGUI.java

Compile and run

Client connects to localhost:5000 and chat begins
javac TCPClientGUI.java
java TCPClientGUI
