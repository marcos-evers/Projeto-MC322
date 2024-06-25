@echo off

:: Read the PIDs from the files
set /p APP_PID=<app.pid
set /p SERVER_PID=<server.pid

:: Terminate the processes using the PIDs
taskkill /PID %APP_PID% /F
taskkill /PID %SERVER_PID% /F

:: Optionally, remove the PID files
del app.pid
del server.pid

echo App and Server processes terminated

:: Find the process using port 8000 and terminate it
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8000') do (
    echo Terminating process using port 8000 with PID %%a
    taskkill /PID %%a /F
)

echo Process using port 8000 terminated