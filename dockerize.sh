#!/usr/bin/env bash

./mvnw clean compile package
docker build -t petclinic .