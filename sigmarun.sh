#!/bin/bash

# Start the first process in the background
./gradlew :app:run &
# Capture the PID of the first process
APP_PID=$!

# Start the second process in the background
./gradlew :server:run &
# Capture the PID of the second process
SERVER_PID=$!

# Save the PIDs to a file
echo $APP_PID > app.pid
echo $SERVER_PID > server.pid

echo "Processes started with PIDs: $APP_PID, $SERVER_PID"