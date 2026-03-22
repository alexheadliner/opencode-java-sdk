# OpenCode Server Dockerfile with Z.AI Provider Configuration
FROM node:24-alpine

# Install required packages
RUN apk add --no-cache curl gettext bash

# Set working directory
WORKDIR /app

# Install OpenCode using the official install script
RUN curl -fsSL https://opencode.ai/install | bash

# Add OpenCode to PATH
ENV PATH="/root/.opencode/bin:$PATH"

# Create configuration directories
RUN mkdir -p /app/config ~/.config/opencode ~/.local/share/opencode

# Copy configuration files and startup script
COPY config/ /app/config/
COPY start.sh /app/start.sh

# Make startup script executable and convert line endings
RUN chmod +x /app/start.sh && \
    sed -i 's/\r$//' /app/start.sh

# Set environment variables
ENV OPENCODE_SERVER_PORT=4096
ENV OPENCODE_SERVER_HOST=0.0.0.0

# Expose port
EXPOSE 4096

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:4096/global/health || exit 1

# Start OpenCode server using startup script
CMD ["/app/start.sh"]