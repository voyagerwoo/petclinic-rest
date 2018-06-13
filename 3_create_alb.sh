#!/usr/bin/env bash

export AWS_ACCESS_KEY_ID=`aws configure get aws_access_key_id`
export AWS_SECRET_ACCESS_KEY=`aws configure get aws_secret_access_key`

export VPC_ID=`aws ec2 describe-vpcs | jq -r '.Vpcs[0].VpcId'`
export SUBNET_ID_1=`aws ec2 describe-subnets | jq -r '.Subnets[0].SubnetId'`
export SUBNET_ID_2=`aws ec2 describe-subnets | jq -r '.Subnets[1].SubnetId'`

echo "VPC_ID : ${VPC_ID}"
echo "SUBNET_ID_1 : ${SUBNET_ID_1}"
echo "SUBNET_ID_2 : ${SUBNET_ID_2}"

ALB_SG_NAME=petclinic-alb-sg
ALB_SG_ID=`aws ec2 describe-security-groups --group-names ${ALB_SG_NAME}| jq -r '.SecurityGroups[0].GroupId'`

echo "ALB SECURITY GROUP : ${ALB_SG_NAME} ${ALB_SG_ID}"


ALB_NAME=petclinic-alb
aws elbv2 create-load-balancer --name ${ALB_NAME} \
  --subnets ${SUBNET_ID_1} ${SUBNET_ID_2} --security-groups ${ALB_SG_ID}

aws elbv2 create-target-group --name petclinic-targets --protocol HTTP --port 80 --vpc-id ${VPC_ID} \
  --health-check-protocol HTTP \
  --health-check-path /actuator/health \
  --target-type instance

ALB_ARN=`aws elbv2 describe-load-balancers --names petclinic-alb | jq -r '.LoadBalancers[0].LoadBalancerArn'`
TARGET_ARN=`aws elbv2 describe-target-groups --names petclinic-targets | jq -r '.TargetGroups[0].TargetGroupArn'`

aws elbv2 create-listener --load-balancer-arn ${ALB_ARN} \
  --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=${TARGET_ARN}