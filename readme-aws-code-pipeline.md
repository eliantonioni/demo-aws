#### get current aws id
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)