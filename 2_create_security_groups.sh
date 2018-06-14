#!/usr/bin/env bash

export VPC_ID=`aws ec2 describe-vpcs | jq -r '.Vpcs[0].VpcId'`
export SUBNET_ID_1=`aws ec2 describe-subnets | jq -r '.Subnets[0].SubnetId'`
export SUBNET_ID_2=`aws ec2 describe-subnets | jq -r '.Subnets[1].SubnetId'`

echo "VPC_ID : ${VPC_ID}"
echo "SUBNET_ID_1 : ${SUBNET_ID_1}"
echo "SUBNET_ID_2 : ${SUBNET_ID_2}"


# create alb security group
ALB_SG_NAME=petclinic-alb-sg
aws ec2 create-security-group --group-name ${ALB_SG_NAME} \
  --description "${ALB_SG_NAME}" --vpc-id ${VPC_ID}
aws ec2 authorize-security-group-ingress --group-name ${ALB_SG_NAME} \
  --protocol tcp --port 80 --cidr 0.0.0.0/0


# create ecs instance security group
ECS_SG_NAME=petclinic-ecs-sg
ALB_SG_ID=`aws ec2 describe-security-groups --group-names ${ALB_SG_NAME}| jq -r '.SecurityGroups[0].GroupId'`

aws ec2 create-security-group --group-name ${ECS_SG_NAME} \
  --description "petclinic-ecs-sg" --vpc-id ${VPC_ID}
aws ec2 authorize-security-group-ingress --group-name ${ECS_SG_NAME} \
  --protocol tcp --port 32768-65535 --source-group ${ALB_SG_ID}
aws ec2 authorize-security-group-ingress --group-name ${ECS_SG_NAME} \
  --protocol tcp --port 22 --cidr 0.0.0.0/0

ECS_SG_ID=`aws ec2 describe-security-groups --group-names ${ECS_SG_NAME}| jq -r '.SecurityGroups[0].GroupId'`


echo "ALB SECURITY GROUP : ${ALB_SG_NAME} ${ALB_SG_ID}"
echo "ECS SECURITY GROUP : ${ECS_SG_NAME} ${ECS_SG_ID}"
