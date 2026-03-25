@echo off
setlocal

:: Default values
set "IMAGE_NAME=%~1"
if "%~1"=="" set "IMAGE_NAME=opencode-server"
set "FULL_IMAGE_NAME=%IMAGE_NAME%:test"

echo Stopping and removing reused containers for image: %FULL_IMAGE_NAME%

:: Find and stop containers based on the image
for /f "tokens=*" %%a in ('docker ps -q --filter "ancestor=%FULL_IMAGE_NAME%"') do (
    echo Stopping container: %%a
    docker stop %%a
    echo Removing container: %%a
    docker rm %%a
)

:: Also clean up any stopped containers from the same image
for /f "tokens=*" %%a in ('docker ps -aq --filter "ancestor=%FULL_IMAGE_NAME%" --filter "status=exited"') do (
    echo Removing stopped container: %%a
    docker rm %%a
)

echo Cleanup complete

endlocal
