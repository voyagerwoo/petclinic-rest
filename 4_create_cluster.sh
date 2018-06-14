#!/usr/bin/env bash

export CLUSTER_NAME=petclinic-rest

export VPC_ID=`aws ec2 describe-vpcs | jq -r '.Vpcs[0].VpcId'`
export SUBNET_ID_1=`aws ec2 describe-subnets | jq -r '.Subnets[0].SubnetId'`
export SUBNET_ID_2=`aws ec2 describe-subnets | jq -r '.Subnets[1].SubnetId'`
REGION=`aws configure get region`

echo "VPC_ID : ${VPC_ID}"
echo "SUBNET_ID_1 : ${SUBNET_ID_1}"
echo "SUBNET_ID_2 : ${SUBNET_ID_2}"

# configure
ecs-cli configure --cluster ${CLUSTER_NAME} \
  --region ${REGION} \
  --default-launch-type EC2 \
  --config-name ${CLUSTER_NAME}

ecs-cli configure profile \
  --access-key ${AWS_ACCESS_KEY_ID} \
  --secret-key ${AWS_SECRET_ACCESS_KEY} \
  --profile-name  ${CLUSTER_NAME}


ECS_SG_NAME=petclinic-ecs-sg
ECS_SG_ID=`aws ec2 describe-security-groups --group-names ${ECS_SG_NAME}| jq -r '.SecurityGroups[0].GroupId'`

echo "ECS SECURITY GROUP : ${ECS_SG_NAME} ${ECS_SG_ID}"

# generate cluster
ecs-cli up --keypair petclinic \
  --security-group ${ECS_SG_ID} \
  --cluster ${CLUSTER_NAME} \
  --vpc ${VPC_ID} \
  --subnets ${SUBNET_ID_1},${SUBNET_ID_2} \
  --capability-iam --size 2 \
  --instance-type t2.micro
