#!/usr/bin/env bash

set -e

echo "Creating kafka service..."

kubectl create namespace messaging
kubectl config set-context --current --namespace=messaging

helm repo add bitnami https://charts.bitnami.com/bitnami
helm install zookeeper bitnami/zookeeper --set replicaCount=1 --set auth.enabled=false --set allowAnonymousLogin=true --atomic
helm install kafka bitnami/kafka --set zookeeper.enabled=false --set replicaCount=1 --set externalZookeeper.servers=zookeeper.messaging.svc.cluster.local --atomic

kubectl create namespace dev
kubectl config set-context --current --namespace=dev

echo "Updating dependencies..."

helm dependency update charts/order-service/

echo "Creating order service..."

helm install order-service charts/order-service --atomic

echo "Creating nginx ingress..."

kubectl create namespace monitoring
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/
helm install nginx stable/nginx-ingress --namespace monitoring -f nginx-ingress.yaml --atomic

echo "Running tests..."

newman run postman/order.postman_collection.json -e postman/dev.postman_environment.json