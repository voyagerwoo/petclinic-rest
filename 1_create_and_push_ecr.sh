#!/usr/bin/env bash

IMAGE_NAME="petclinic-rest"
REGION=`aws configure get region`

if aws ecr create-repository --repository-name ${IMAGE_NAME}; then
    echo "[INFO] REPOSITORY IS CREATED."
else
    echo "[INFO] REPOSITORY ALREADY EXISTS."
fi

ACCOUNT_ID=`aws sts get-caller-identity | jq -r ".Account"`

export DOCKER_REGISTRY_HOST="${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
echo "[INFO] DOCKER_REGISTRY_HOST : ${DOCKER_REGISTRY_HOST}"

if ./mvnw clean package docker:build -Dmaven.test.skip=true; then
    DOCKER_LOGIN=`aws ecr get-login --no-include-email`
    ${DOCKER_LOGIN}
    docker push ${DOCKER_REGISTRY_HOST}/${IMAGE_NAME}:latest
else
    echo "[ERROR] MAVEN BUILD FAIL"
fi