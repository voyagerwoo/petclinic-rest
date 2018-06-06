#!/usr/bin/env bash

IMAGE_NAME="petclinic-rest"
REPOSITORY_ID=`aws ecr create-repository --repository-name ${IMAGE_NAME} | jq -r ".repository.registryId"`

if [[ -z "${REPOSITORY_ID}" ]]; then
    echo "[INFO] REPOSITORY ALREADY EXISTS."
    REPOSITORY_ID=`aws ecr describe-repositories --repository-name ${IMAGE_NAME} | jq -r ".repositories[0].registryId"`
fi
export DOCKER_REGISTRY_HOST="${REPOSITORY_ID}.dkr.ecr.ap-northeast-2.amazonaws.com"
echo "[INFO] DOCKER_REGISTRY_HOST : ${DOCKER_REGISTRY_HOST}"

if ./mvnw clean package docker:build -Dmaven.test.skip=true; then
    DOCKER_LOGIN=`aws ecr get-login --no-include-email`
    ${DOCKER_LOGIN}
    docker push ${DOCKER_REGISTRY_HOST}/${IMAGE_NAME}:latest
else
    echo "[ERROR] MAVEN BUILD FAIL"
fi