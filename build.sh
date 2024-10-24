#!/bin/bash

# Clear the screen
clear

# Function to handle errors
handle_error() {
    if [ $? -ne 0 ]; then
        echo "$1 build failed!" >&2
        exit 1
    fi
}

echo "Starting the build process..."

# Clean the project
echo "Cleaning the project..."
mvn clean
handle_error "Cleaning"

# Build AWS Lambda function
echo "Building AWS Lambda function..."
mvn package -Paws-lambda-function
handle_error "AWS Lambda"

# Build Azure function
echo "Building Azure function..."
mvn package -Pazure-function
handle_error "Azure"

# Build GCP function
echo "Building GCP function..."
mvn package -Pgcp-function
handle_error "GCP"

# If everything passed
echo "Build process completed successfully!"
