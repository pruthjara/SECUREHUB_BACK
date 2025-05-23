
FROM openjdk:17-jdk-slim


RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        krb5-user krb5-config curl && \
    rm -rf /var/lib/apt/lists/*


WORKDIR /app


COPY ./backend/target/universal/stage ./stage


EXPOSE 9000


CMD ["./stage/bin/backend"]
