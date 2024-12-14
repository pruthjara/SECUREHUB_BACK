# Use a base image of OpenJDK
FROM openjdk:17-jdk-slim

# Install Kerberos client utilities (krb5-user) and curl
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        krb5-user krb5-config curl && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the build artifacts and dependencies
COPY ./backend/target/universal/stage ./stage

# Expose the default Play Framework port
EXPOSE 9000

# Command to start the application
CMD ["./stage/bin/backend"]
