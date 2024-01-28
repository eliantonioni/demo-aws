#### create role to be used by CodeDeploy:
kubectl config use-context <aws region eu-north-1 context>
aws iam create-role --role-name CodeDeployServiceRole --assume-role-policy-document file://aws-code-deploy/CodeDeploy-Trust.json

#### attach policy for EC2 (ECS will need arn:aws:iam::aws:policy/AWSCodeDeployRoleForECS):
aws iam attach-role-policy --role-name CodeDeployServiceRole --policy-arn arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole

#### get service role ARN:
aws iam get-role --role-name CodeDeployServiceRole --query "Role.Arn" --output text

#### restrict permissions for CodeDeployServiceRole (following 'least privileges' principle)
aws iam put-role-policy \
--role-name CodeDeployServiceRole \
--policy-name CodeDeployAccessPolicy \
--policy-document file://aws-code-deploy/CodeDeploy-LimitPerms-InlinePolicy.json
#### to check - use AWS IAM service in console, go to role and see additional customer inline policy 

#### create 'instance profile' role
aws iam create-role --role-name CodeDeploy-EC2-Instance-Profile --assume-role-policy-document file://aws-code-deploy/CodeDeploy-EC2-Trust.json
aws iam put-role-policy --role-name CodeDeploy-EC2-Instance-Profile --policy-name CodeDeploy-EC2-Permissions \
--policy-document file://aws-code-deploy/CodeDeploy-EC2-Permissions.json
aws iam attach-role-policy --policy-arn arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore --role-name CodeDeploy-EC2-Instance-Profile
aws iam create-instance-profile --instance-profile-name CodeDeploy-EC2-Instance-Profile
#### to check: 
aws iam list-instance-profiles