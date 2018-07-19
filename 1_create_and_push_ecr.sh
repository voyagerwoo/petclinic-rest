#!/usr/bin/env bash

source ./env_var.sh

if aws ecr create-repository --repository-name ${IMAGE_NAME}; then
    echo "[INFO] REPOSITORY IS CREATED."
else
    echo "[INFO] REPOSITORY ALREADY EXISTS."
fi

if ./mvnw clean package docker:build -Dmaven.test.skip=true; then
    DOCKER_LOGIN=`aws ecr get-login --no-include-email`
    ${DOCKER_LOGIN}
    docker push ${DOCKER_REGISTRY_HOST}/${IMAGE_NAME}:latest
else
    echo "[ERROR] MAVEN BUILD FAIL"
fi