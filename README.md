# Observability Demo with Grafana, Prometheus, and Tempo

## Overview
This project demonstrates how to set up an observability stack using **Grafana**, **Prometheus**, and **Tempo**. The goal is to monitor and analyze application performance with metrics, logs, and traces in a Kubernetes environment.

## Components
1. **Grafana** - A visualization tool for monitoring and analyzing metrics.
2. **Prometheus** - A time-series database used for collecting and querying metrics.
3. **Tempo** - A distributed tracing system for analyzing request flows.

## Prerequisites
- Kubernetes Cluster (Minikube, OpenShift, or a managed Kubernetes service)
- Helm (for easy deployment)
- kubectl (CLI for Kubernetes management)
- Docker (if running locally)

## Installation

### 1. Install Prometheus and Grafana
```sh
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install prometheus prometheus-community/kube-prometheus-stack
```

### 2. Install Tempo
```sh
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
helm install tempo grafana/tempo
```

### 3. Expose Services
```sh
kubectl port-forward svc/prometheus-kube-prometheus-prometheus 9090:9090 &
kubectl port-forward svc/grafana 3000:80 &
kubectl port-forward svc/tempo 3100:3100 &
```

## Configuration

### Grafana Dashboard Setup
1. Access Grafana via `http://localhost:3000`
2. Default credentials: `admin/admin`
3. Add Prometheus as a data source (`http://prometheus-kube-prometheus-prometheus:9090`)
4. Add Tempo as a data source (`http://tempo:3100`)
5. Import pre-built dashboards for visualization

### Instrumentation
- Ensure your application is exporting **OpenTelemetry traces**
- Configure Prometheus to scrape metrics
- Send traces to Tempo using an **OpenTelemetry Collector**

## Observability Queries
- **Prometheus Metrics:** Query in Grafana using `rate(http_requests_total[5m])`
- **Tempo Traces:** Search for traces by Service Name in the Tempo UI
- **Grafana Alerts:** Configure alerts to notify when thresholds are exceeded

## Cleanup
To remove all deployed resources:
```sh
helm uninstall prometheus
helm uninstall tempo
kubectl delete all --all
```

## Conclusion
This setup provides a complete observability solution with **metrics, traces, and dashboards** for troubleshooting and monitoring your applications effectively. You can extend this setup by integrating **Loki** for log aggregation.

## References
- [Grafana Documentation](https://grafana.com/docs/)
- [Prometheus Documentation](https://prometheus.io/docs/)
- [Tempo Documentation](https://grafana.com/docs/tempo/latest/)