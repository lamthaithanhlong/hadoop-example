#!/usr/bin/env bash

if [ ! "$( docker container inspect -f '{{.State.Running}}' hadoop )" == "true" ]; then
    docker compose -f .docker/docker-compose.yaml --project-directory . up -d
    sleep 2
fi

if [[ "$OSTYPE" =~ ^msys  || "$OSTYPE" =~ ^cygwin ]]; then
    winpty docker exec -it hadoop bash
else 
    docker exec -it hadoop bash
fi
