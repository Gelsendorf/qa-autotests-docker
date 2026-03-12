FROM maven:3.9.6-eclipse-temurin-11 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
COPY testng.xml .

FROM maven:3.9.6-eclipse-temurin-11

RUN apt-get update && apt-get install -y \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

ENV CHROMIUM_FLAGS="--no-sandbox --disable-dev-shm-usage --headless=new"

WORKDIR /app

COPY --from=builder /root/.m2 /root/.m2
COPY --from=builder /app .

ENV SELENIUM_HUB=""
ENV BASE_URL="https://www.example.com"
ENV API_BASE_URL="https://jsonplaceholder.typicode.com"

CMD ["mvn", "test", "-B"]
