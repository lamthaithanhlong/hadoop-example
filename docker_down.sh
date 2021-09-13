#!/usr/bin/env bash

docker compose -f .docker/docker-compose.yaml --project-directory . down $1
