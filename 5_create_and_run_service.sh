#!/usr/bin/env bash

export CLUSTER_NAME=petclinic-rest

export VPC_ID=`aws ec2 describe-vpcs | jq -r '.Vpcs[0].VpcId'`
export SUBNET_ID_1=`aws ec2 describe-subnets | jq -r '.Subnets[0].SubnetId'`
export SUBNET_ID_2=`aws ec2 describe-subnets | jq -r '.Subnets[1].SubnetId'`

echo "VPC_ID : ${VPC_ID}"
echo "SUBNET_ID_1 : ${SUBNET_ID_1}"
echo "SUBNET_ID_2 : ${SUBNET_ID_2}"

TARGET_ARN=`aws elbv2 describe-target-groups --names petclinic-targets | jq -r '.TargetGroups[0].TargetGroupArn'`

# 서비스 생성
ecs-cli compose --file ecs_task.yml \
  --project-name ${CLUSTER_NAME} \
    service \
  create --cluster ${CLUSTER_NAME} \
  --deployment-max-percent 100 \
  --deployment-min-healthy-percent 50 \
  --target-group-arn ${TARGET_ARN} \
  --health-check-grace-period 60 \
  --container-name ${CLUSTER_NAME} \
  --container-port 9460 \
  --create-log-groups

ecs-cli compose --file ecs_task.yml \
  --project-name ${CLUSTER_NAME} service up \
  --cluster ${CLUSTER_NAME}

ecs-cli compose --file ecs_task.yml \
  --project-name ${CLUSTER_NAME} service scale 2 \
  --cluster ${CLUSTER_NAME}
