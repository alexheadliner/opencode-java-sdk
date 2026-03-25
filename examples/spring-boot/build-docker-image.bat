@echo off
setlocal EnableDelayedExpansion

:: Default values
set "IMAGE_NAME=%~1"
set "IMAGE_TAG=%~2"

if "%~1"=="" set "IMAGE_NAME=opencode-server"
if "%~2"=="" set "IMAGE_TAG=test"

set "FULL_IMAGE_NAME=%IMAGE_NAME%:%IMAGE_TAG%"

echo Building OpenCode Docker image...
echo Image name: %IMAGE_NAME%
echo Image tag: %IMAGE_TAG%

:: Check Docker is installed
where docker >nul 2>nul
if errorlevel 1 (
    echo Error: Docker is not installed or not in PATH
    exit /b 1
)

:: Check Docker is running
docker info >nul 2>nul
if errorlevel 1 (
    echo Error: Docker daemon is not running
    exit /b 1
)

:: Calculate paths
set "SCRIPT_DIR=%~dp0"
set "PROJECT_ROOT=%SCRIPT_DIR%..\.."
set "DOCKERFILE_PATH=%PROJECT_ROOT%\docker\opencode\Dockerfile"
set "CONTEXT_DIR=%PROJECT_ROOT%\docker\opencode"

:: Validate paths
if not exist "%DOCKERFILE_PATH%" (
    echo Error: Dockerfile not found at %DOCKERFILE_PATH%
    exit /b 1
)

echo Dockerfile: %DOCKERFILE_PATH%
echo Context: %CONTEXT_DIR%

:: Build image
echo Building Docker image...
docker build -t "%FULL_IMAGE_NAME%" -f "%DOCKERFILE_PATH%" "%CONTEXT_DIR%"

if errorlevel 1 (
    echo Error: Failed to build image
    exit /b 1
)

:: Verify build
docker images "%FULL_IMAGE_NAME%" --format "{{.Repository}}:{{.Tag}}" | findstr /C:"%FULL_IMAGE_NAME%" >nul
if errorlevel 1 (
    echo Error: Failed to build image
    exit /b 1
)

echo Successfully built image: %FULL_IMAGE_NAME%
docker images "%FULL_IMAGE_NAME%" --format "{{.Repository}}:{{.Tag}} - {{.Size}} - Created {{.CreatedSince}}"

endlocal
