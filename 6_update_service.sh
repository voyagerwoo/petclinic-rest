#!/usr/bin/env bash

source ./env_var.sh

bash ./1_create_and_push_ecr.sh

ecs-cli compose --file ecs_task.yml --ecs-params ecs_params.yml \
  --project-name ${CLUSTER_NAME} service up \
  --cluster ${CLUSTER_NAME} --force-deployment