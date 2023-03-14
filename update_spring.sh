#!/bin/bash

docker-compose stop hello

docker-compose rm -f hello

./gradlew clean test build

docker-compose build --no-cache hello

docker-compose up -d hello

echo "Done."