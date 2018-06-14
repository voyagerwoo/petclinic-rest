#!/usr/bin/env bash

echo AWS Access Key ID :
read line
export AWS_ACCESS_KEY_ID=$line
echo AWS Access Key ID :
read line
export AWS_SECRET_ACCESS_KEY=$line


echo AWS_ACCESS_KEY_ID : $AWS_ACCESS_KEY_ID
echo AWS_SECRET_ACCESS_KEY : $AWS_SECRET_ACCESS_KEY

# TODO .bashrc 수정