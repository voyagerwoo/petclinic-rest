#!/usr/bin/env bash

source ./env_var.sh

TARGET_ARN=`aws elbv2 describe-target-groups --names petclinic-targets | jq -r '.TargetGroups[0].TargetGroupArn'`

# 서비스 생성
ecs-cli compose --file ecs_task.yml --ecs-params ecs_params.yml \
  --project-name ${CLUSTER_NAME} \
    service \
  create --cluster ${CLUSTER_NAME} \
  --deployment-max-percent 400 \
  --deployment-min-healthy-percent 100 \
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


DNS_NAME=`aws elbv2 describe-load-balancers --name petclinic-alb | jq -r '.LoadBalancers[0].DNSName'`
echo "dns name : ${DNS_NAME}"