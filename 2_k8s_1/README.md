# Setup

## Install Helm Release

```bash
cd hw2/architect-chart
kubectl create namespace architect-course
helm dependency update ./
helm install architect-app ./ --namespace architect-course
```

## Run Postman Tests

```bash
cd hw2/postman
newman run user_api_postman_collection.json -e dev.postman_environment.json
```

### Additional Info

* Flyway migration used.
* Swagger UI 

```bash
http://arch.homework/otusapp/allash/swagger-ui.html
```