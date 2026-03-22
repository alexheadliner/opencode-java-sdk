#!/bin/sh

# Validate required environment variables
REQUIRED_VARS="Z_AI_API_KEY OPENCODE_SERVER_USERNAME OPENCODE_SERVER_PASSWORD"
MISSING_VARS=""

for var in $REQUIRED_VARS; do
    eval value=\"\$$var\"
    if [ -z "$value" ]; then
        MISSING_VARS="$MISSING_VARS $var"
    fi
done

if [ -n "$MISSING_VARS" ]; then
    echo "Error: The following required environment variables are not set:$MISSING_VARS"
    exit 1
fi

# Set default values for optional parameters
OPENCODE_SERVER_PORT=${OPENCODE_SERVER_PORT:-4096}
OPENCODE_SERVER_HOST=${OPENCODE_SERVER_HOST:-0.0.0.0}

# Export variables for envsubst
export Z_AI_API_KEY
export OPENCODE_SERVER_PORT
export OPENCODE_SERVER_HOST

# Create directories if they don't exist
mkdir -p ~/.local/share/opencode
mkdir -p ~/.config/opencode

# Process template files with envsubst
echo "Generating configuration files from templates..."
envsubst < /app/config/auth.json.template > ~/.local/share/opencode/auth.json
envsubst < /app/config/opencode.json.template > ~/.config/opencode/opencode.json

# Start OpenCode server
exec opencode serve --port ${OPENCODE_SERVER_PORT} --hostname ${OPENCODE_SERVER_HOST}
