@echo off

:: Start the first process in a new command window
start "App" cmd /c "gradlew :app:run"
:: Capture the PID of the first process
for /f "tokens=2 delims=," %%i in ('tasklist /fi "imagename eq cmd.exe" /fo csv /nh') do (
    if not defined AppPID (
        set AppPID=%%i
    )
)

:: Start the second process in a new command window
start "Server" cmd /c "gradlew :server:run"
:: Capture the PID of the second process
for /f "tokens=2 delims=," %%i in ('tasklist /fi "imagename eq cmd.exe" /fo csv /nh') do (
    if not defined ServerPID (
        set ServerPID=%%i
    )
)

:: Save the PIDs to files
echo %AppPID% > app.pid
echo %ServerPID% > server.pid

echo Processes started with PIDs: %AppPID%, %ServerPID%