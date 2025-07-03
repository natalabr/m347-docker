# Warenverkauf Microservices Projekt

## Technologien & Architektur

Dieses Projekt demonstriert eine Microservices-Architektur mit Docker Compose. Es besteht aus zwei Haupt-Containern:

- **Java Microservice**: Konsolenanwendung für Warenverkauf, implementiert in Java 22, nutzt JDBC für den Datenbankzugriff.
- **MySQL Datenbank**: MySQL 8, initialisiert mit Beispieldaten aus `mysqlsampledatabase.sql`.

**Verwendete Technologien:**
- Java 22 (eclipse-temurin)
- MySQL 8
- Docker & Docker Compose

## Projektstruktur

- `java/` – Java-Quellcode und Dockerfile für die App
- `db/` – Dockerfile und SQL-Initialdaten für die Datenbank
- `compose.yaml` – Docker Compose Konfiguration
- `.env` – Umgebungsvariablen für die App

## Schritt-für-Schritt Anleitung

1. **Voraussetzungen**
   - Docker und Docker Compose installiert
   - (Optional) Docker Hub Account zum Pushen der Images

2. **Projekt bauen und starten**
   ```powershell
   docker compose build
   docker compose up
   ```
   - Die Datenbank wird mit Beispieldaten initialisiert.
   - Die Java-App startet und zeigt das Konsolenmenü.

3. **Interaktive Nutzung der Java-App**
   - Um die App mit Terminal-Interaktion zu nutzen:
     ```powershell
     docker compose up -d db
     docker compose run --rm -it java-app
     ```

4. **Eigene Images auf Docker Hub pushen**
   - Images nach dem Build mit `docker images` suchen (z.B. `warenverkauf-java-app`, `warenverkauf-db`).
   - Taggen und pushen:
     ```powershell
     docker tag <image> <yourdockerhub>/<image>:latest
     docker push <yourdockerhub>/<image>:latest
     ```

## Hinweise
- Die Umgebungsvariablen für die Datenbankverbindung werden über `.env` und `compose.yaml` gesetzt.
- Die MySQL JDBC-Treiberdatei wird beim Build automatisch heruntergeladen.
- Die App ist als Konsolenanwendung ausgelegt und benötigt ein interaktives Terminal für Benutzereingaben.