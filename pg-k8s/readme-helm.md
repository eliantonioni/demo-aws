kubectl delete -f pg-k8s/persistent-volume.yaml
kubectl apply -f pg-k8s/persistent-volume.yaml
kubectl get pv postgres-pv-volume
kubectl get pvc postgres-pv-claim

helm install pg-demo-aws bitnami/postgresql --set persistence.existingClaim=postgres-pv-claim --set volumePermissions.enabled=true
helm uninstall pg-demo-aws

# before running the job - ensure that all previous steps were done in demo-aws namespace, otherwise job will fail
kubectl apply -f pg-k8s/pg-create-db-job.yaml

# to connect from outside the cluster need port forwarding  
# 1 - port forwarding
kubectl port-forward --namespace demo-aws svc/pg-demo-aws-postgresql 5432:5432
# 2 - get password
kubectl get secret --namespace demo-aws pg-demo-aws-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d