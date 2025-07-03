# Warenverkauf Microservices Projekt

## Technologien & Architektur

Dieses Projekt demonstriert eine Microservices-Architektur mit Docker Compose. Es besteht aus zwei Haupt-Containern:

- **Java Microservice**: Konsolenanwendung für Warenverkauf, implementiert in Java 22, nutzt JDBC für den Datenbankzugriff.
- **MySQL Datenbank**: MySQL 8, initialisiert mit Beispieldaten aus `mysqlsampledatabase.sql`.

**Verwendete Technologien:**
- Java 22 (eclipse-temurin)
- MySQL 8.0
- Docker & Docker Compose

## Projektstruktur

- `java/` – Java-Quellcode und Dockerfile für die App
- `db/` – Dockerfile und SQL-Initialdaten für die Datenbank
- `compose.yaml` – Docker Compose Konfiguration
- `.env` – Umgebungsvariablen für die App

> [!IMPORTANT]
> Damit das composen sowie die App funktioniert, muss man das `.env.example` zu `.env` umbennen.

## Schritt-für-Schritt Anleitung

1. **Voraussetzungen**
   - Docker und Docker Compose installiert

2. **Projekt starten**
   ```powershell
   docker compose up
   ```
   - Die Datenbank wird mit Beispieldaten initialisiert.
   - Die Java-App startet und zeigt das Konsolenmenü.

3. **Interaktive Nutzung der Java-App**
   - Um die App mit Terminal-Interaktion zu nutzen:
     ```powershell
     docker compose up -d warenverkauf-db
     docker compose run --rm -it warenverkauf-java-app
     ```
> [!NOTE]
> Man muss die Services unterschiedlich starten. Der DB container muss zuerst gestartet werden (am besten detachted `-d`), bevor die java-app gestartet wird (Achtung: Es muss ein interactive terminal `-it` sein damit es keinen Error gibt. (Die Java app ist dafür ausgebaut, dass es eine interaktive Konsole hat)).

## Hinweise
- Die Umgebungsvariablen für die Datenbankverbindung werden über `.env` und `compose.yaml` gesetzt.
- Die MySQL JDBC-Treiberdatei wird beim Build automatisch heruntergeladen.
- Die App ist als Konsolenanwendung ausgelegt und **benötigt ein interaktives Terminal** für Benutzereingaben.