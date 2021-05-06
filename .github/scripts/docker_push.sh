#!/usr/bin/env bash
set -e
mvn clean --quiet -Dmaven.test.skip=true package
docker build -t yuna88/paginator .
docker push yuna88/paginator