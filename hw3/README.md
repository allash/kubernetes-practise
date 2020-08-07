```bash
cd hw3/prometheus
minikube addons disable ingress
kubectl create namespace architect-course
kubectl config set-context --current --namespace=architect-course

helm repo add stable https://kubernetes-charts.storage.googleapis.com
helm install prom stable/prometheus-operator -f prometheus.yaml --atomic
helm install nginx stable/nginx-ingress -f nginx-ingress.yaml --atomic

cd ../architect-chart/
helm dependency update ./
helm install architect-backend ./ --atomic

kubectl port-forward service/prom-grafana 9000:80
kubectl port-forward service/prom-prometheus-operator-prometheus 9090

```

* Grafana
```bash
username: admin
password: kubectl get secret --namespace architect-course prom-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
```

* Stress-Testing
```bash
while 1; do ab -n 50 -c 5 http://arch.homework/otusapp/allash/metrics ; sleep 3; done
while 1; do ab -n 50 -c 5 http://arch.homework/otusapp/allash/users ; sleep 3; done
```

* POSTGRES Exporter (не до конца сделано)
```bash
helm install postgres-exporter stable/prometheus-postgres-exporter
kubectl port-forward service/postgres-exporter-prometheus-postgres-exporter 9187:80
```
