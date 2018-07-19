#!/usr/bin/env bash

source ./env_var.sh

ALB_SG_ID=`aws ec2 describe-security-groups --group-names ${ALB_SG_NAME}| jq -r '.SecurityGroups[0].GroupId'`

echo "ALB SECURITY GROUP : ${ALB_SG_NAME} ${ALB_SG_ID}"

aws elbv2 create-load-balancer --name ${ALB_NAME} \
  --subnets ${SUBNET_ID_1} ${SUBNET_ID_2} --security-groups ${ALB_SG_ID}

aws elbv2 create-target-group --name ${TARGET_NAME} --protocol HTTP --port 80 --vpc-id ${VPC_ID} \
  --health-check-protocol HTTP \
  --health-check-path /actuator/health \
  --target-type instance

ALB_ARN=`aws elbv2 describe-load-balancers --names ${ALB_NAME} | jq -r '.LoadBalancers[0].LoadBalancerArn'`
TARGET_ARN=`aws elbv2 describe-target-groups --names ${TARGET_NAME} | jq -r '.TargetGroups[0].TargetGroupArn'`

aws elbv2 modify-target-group-attributes --target-group-arn ${TARGET_ARN} \
  --attributes Key=deregistration_delay.timeout_seconds,Value=120

aws elbv2 create-listener --load-balancer-arn ${ALB_ARN} \
  --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=${TARGET_ARN}