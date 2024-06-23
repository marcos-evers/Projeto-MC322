#!/bin/bash

# Read the PIDs from the files
APP_PID=$(cat app.pid)
SERVER_PID=$(cat server.pid)

# Terminate the processes using the PIDs
kill $APP_PID
kill $SERVER_PID

# Optionally, remove the PID files
rm app.pid
rm server.pid

echo "App and Server processes terminated"

# Find the process using port 8000 and terminate it
PORT=8000
PID=$(lsof -t -i:$PORT)

if [ -n "$PID" ]; then
  kill -9 $PID
  echo "Process using port $PORT terminated"
else
  echo "No process using port $PORT found"
fi
