#!/bin/bash

# Script to stop and remove reused Testcontainers
# Useful when you want to clean up containers between test runs

IMAGE_NAME="${1:-opencode-server}"
FULL_IMAGE_NAME="${IMAGE_NAME}:test"

echo "Stopping and removing reused containers for image: $FULL_IMAGE_NAME"

# Find and stop containers based on the image
CONTAINERS=$(docker ps -q --filter "ancestor=$FULL_IMAGE_NAME")

if [ -n "$CONTAINERS" ]; then
    echo "Stopping containers: $CONTAINERS"
    docker stop $CONTAINERS
    echo "Removing containers: $CONTAINERS"
    docker rm $CONTAINERS
    echo "Containers stopped and removed successfully"
else
    echo "No running containers found for image: $FULL_IMAGE_NAME"
fi

# Also clean up any stopped containers from the same image
STOPPED_CONTAINERS=$(docker ps -aq --filter "ancestor=$FULL_IMAGE_NAME" --filter "status=exited")

if [ -n "$STOPPED_CONTAINERS" ]; then
    echo "Removing stopped containers: $STOPPED_CONTAINERS"
    docker rm $STOPPED_CONTAINERS
fi

echo "Cleanup complete"
