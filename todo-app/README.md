# Todo-App

Eine Fullstack-Anwendung mit Spring Boot und Angular zur Verwaltung von Aufgaben.

## Funktionen

- Erstellen, Anzeigen, Aktualisieren und Löschen von Aufgaben
- Filtern von Aufgaben nach Status (alle, aktiv, erledigt)
- Responsive Benutzeroberfläche mit Bootstrap
- Prometheus-Metriken für Monitoring
- OpenTelemetry-Tracing für verteilte Nachverfolgung

## Technologie-Stack

### Backend
- Spring Boot 3.2.3
- Spring Data JPA
- PostgreSQL
- Prometheus Metrics
- OpenTelemetry

### Frontend
- Angular 17
- Bootstrap 5
- OpenTelemetry für Browser-Tracing

### Infrastruktur
- Docker & Docker Compose
- Kubernetes mit Helm
- Prometheus & Grafana
- Jaeger für Tracing
- OpenTelemetry Collector

## Starten mit Docker Compose

1. Stellen Sie sicher, dass Docker und Docker Compose installiert sind
2. Klonen Sie das Repository
3. Navigieren Sie zum Projektverzeichnis
4. Führen Sie den folgenden Befehl aus:

```bash
docker-compose up -d
```

Die Anwendung ist dann unter folgenden URLs verfügbar:
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080/api
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (Benutzer: admin, Passwort: admin)
- Jaeger UI: http://localhost:16686

## Bereitstellung in Kubernetes mit Helm

1. Stellen Sie sicher, dass Kubernetes und Helm installiert sind
2. Fügen Sie die erforderlichen Helm-Repositories hinzu:

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo add jaegertracing https://jaegertracing.github.io/helm-charts
helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-charts
helm repo update
```

3. Installieren Sie das Chart:

```bash
helm install todo-app ./helm/todo-app
```

4. Fügen Sie einen Eintrag in Ihre /etc/hosts-Datei hinzu:

```
127.0.0.1 todo-app.local
```

Die Anwendung ist dann unter http://todo-app.local verfügbar (vorausgesetzt, Sie haben einen Ingress-Controller installiert).

## Entwicklung

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## Lizenz

MIT 