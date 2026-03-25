#!/bin/bash
set -e

# Default values
IMAGE_NAME=${1:-opencode-server}
IMAGE_TAG=${2:-test}
FULL_IMAGE_NAME="${IMAGE_NAME}:${IMAGE_TAG}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "Building OpenCode Docker image..."
echo "Image name: ${IMAGE_NAME}"
echo "Image tag: ${IMAGE_TAG}"

# Check Docker is installed
if ! command -v docker &> /dev/null; then
    echo -e "${RED}Error: Docker is not installed or not in PATH${NC}"
    exit 1
fi

# Check Docker is running
if ! docker info &> /dev/null; then
    echo -e "${RED}Error: Docker daemon is not running${NC}"
    exit 1
fi

# Calculate paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/../.." && pwd)"
DOCKERFILE_PATH="${PROJECT_ROOT}/docker/opencode/Dockerfile"
CONTEXT_DIR="${PROJECT_ROOT}/docker/opencode"

# Validate paths
if [ ! -f "${DOCKERFILE_PATH}" ]; then
    echo -e "${RED}Error: Dockerfile not found at ${DOCKERFILE_PATH}${NC}"
    exit 1
fi

echo "Dockerfile: ${DOCKERFILE_PATH}"
echo "Context: ${CONTEXT_DIR}"

# Build image
echo -e "${YELLOW}Building Docker image...${NC}"
docker build -t "${FULL_IMAGE_NAME}" -f "${DOCKERFILE_PATH}" "${CONTEXT_DIR}"

# Verify build
if docker images "${FULL_IMAGE_NAME}" --format "{{.Repository}}:{{.Tag}}" | grep -q "${FULL_IMAGE_NAME}"; then
    echo -e "${GREEN}Successfully built image: ${FULL_IMAGE_NAME}${NC}"
    docker images "${FULL_IMAGE_NAME}" --format "{{.Repository}}:{{.Tag}} - {{.Size}} - Created {{.CreatedSince}}"
else
    echo -e "${RED}Error: Failed to build image${NC}"
    exit 1
fi
