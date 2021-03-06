```bash

kubectl create namespace monitoring
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/
helm install nginx stable/nginx-ingress --namespace monitoring -f nginx-ingress.yaml

kubectl create namespace dev
kubectl config set-context --current --namespace=dev

cd hw5/charts
helm install auth-service ./auth-service-chart --atomic
helm install user-service ./user-service-chart --atomic

cd ../
newman run auth_api_postman_collection.json -e dev.postman_environment.json


```

ingress auth-url
```bash

http://<service>.<namespace>.svc.cluster.local:<port>/

```

Auth forward sequence diagram

![sequence-diagram](./README.assets/sequence-diagram.png)