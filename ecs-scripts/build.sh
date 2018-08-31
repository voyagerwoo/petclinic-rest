#!/usr/bin/env bash

cd .. && \
    ./mvnw clean package && \
    docker build -f src/main/docker/Dockerfile -t petclinic-rest .