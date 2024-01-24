# 1 - setting up istio on EKS
git clone https://github.com/aws-ia/terraform-aws-eks-blueprints.git
cd terraform-aws-eks-blueprints/patterns/istio
helm repo update
terraform init
terraform apply -auto-approve

kubectl rollout restart deployment istio-ingress -n istio-ingress

for ADDON in kiali jaeger prometheus grafana
do
ADDON_URL="https://raw.githubusercontent.com/istio/istio/release-1.18/samples/addons/$ADDON.yaml"
kubectl apply -f $ADDON_URL
done

# 2 - deploy demo services
cd ~/workspace
git clone https://github.com/aws-samples/istio-on-eks.git
cd istio-on-eks/modules/01-getting-started

kubectl create namespace workshop
kubectl label namespace workshop istio-injection=enabled

helm install mesh-basic . -n workshop

kubectl get pods -n workshop

ISTIO_INGRESS_URL=$(kubectl get svc istio-ingress -n istio-ingress -o jsonpath='{.status.loadBalancer.ingress[*].hostname}')
echo "http://$ISTIO_INGRESS_URL"

# 3 - examine ingress GW and virtual service
kubectl get Gateway,VirtualService -n workshop
kubectl get gateway productapp-gateway -n workshop -o yaml
kubectl get VirtualService productapp -n workshop -o yaml

# 4 - examine deployed kiali and grafana
kubectl port-forward svc/kiali 20001:20001 -n istio-system
http://localhost:2001
kubectl port-forward svc/grafana 3000:3000 -n istio-system
http://localhost:3000/dashboards

# 5 - generate traffic and check it
ISTIO_INGRESS_URL=$(kubectl get svc istio-ingress -n istio-ingress -o jsonpath='{.status.loadBalancer.ingress[*].hostname}')
siege http://$ISTIO_INGRESS_URL -c 5 -d 10 -t 2M
# after 2 mins - check out grafana istio-workload-dashboard and kiali graph

helm uninstall mesh-basic -n workshop