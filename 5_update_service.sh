#!/usr/bin/env bash

bash ./1_push_to_ecr.sh

export CLUSTER_NAME=petclinic-rest

ecs-cli compose --file ecs_task.yml \
  --project-name ${CLUSTER_NAME} service up \
  --cluster ${CLUSTER_NAME}