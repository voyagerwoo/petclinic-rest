#!/usr/bin/env bash

source env_var.sh

if aws ecr create-repository --repository-name ${IMAGE_NAME}; then
    echo "[INFO] REPOSITORY IS CREATED."
else
    echo "[INFO] REPOSITORY ALREADY EXISTS."
fi

if cd .. && ./build.sh; then
    DOCKER_LOGIN=`aws ecr get-login --no-include-email`
    ${DOCKER_LOGIN}
    REPOSITORY_URI=`aws ecr describe-repositories --repository-name ${IMAGE_NAME} | jq -r ".repositories[0].repositoryUri"`
    docker tag petclinic-rest:latest ${REPOSITORY_URI}:latest
    docker push ${REPOSITORY_URI}:latest
else
    echo "[ERROR] MAVEN BUILD FAIL"
fi