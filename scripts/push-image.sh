#!/bin/sh

REGISTRY=khaliqgant

if [ -z "$1" ]; then
    echo "You must specify a version number that you want to tag the image with"
    exit 1
fi

docker build -f Dockerfile -t $REGISTRY/selenium-grid-autoscaler:$1 -t $REGISTRY/selenium-grid-autoscaler:latest .
docker push $REGISTRY/selenium-grid-autoscaler:$1
docker push $REGISTRY/selenium-grid-autoscaler:latest
