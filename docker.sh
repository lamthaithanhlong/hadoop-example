#!/bin/bash

if [ ! "$( docker container inspect -f '{{.State.Running}}' hadoop )" == "true" ]; then
    docker compose -f .docker/docker-compose.yaml --project-directory . up -d
    sleep 2
fi

docker exec -it hadoop bash
