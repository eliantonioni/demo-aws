kubectl apply -f persistent-volume.yaml
kubectl get pv postgres-pv-volume
kubectl get pvc postgres-pv-claim

kubectl apply -f postgres-deployment.yaml


kubectl exec -it $(kubectl get pod -l app.kubernetes.io/name=postgresql -o jsonpath="{.items[0].metadata.name}") -- psql -U postgres

export PGPASSWORD=$(kubectl get secret --namespace demo-aws pg-demo-aws-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)
kubectl exec -it $(kubectl get pod -l app.kubernetes.io/name=postgresql -o jsonpath="{.items[0].metadata.name}") -- psql -U postgres
kubectl -n demo-aws get svc postgres