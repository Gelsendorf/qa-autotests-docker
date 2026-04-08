# 1. Базовий Jenkins
FROM jenkins/jenkins:lts

# 2. Переходимо на root для встановлення пакетів
USER root

# 3. Встановлюємо Maven, wget, curl, gnupg, unzip
RUN apt-get update && \
    apt-get install -y maven wget gnupg2 unzip curl lsb-release && \
    rm -rf /var/lib/apt/lists/*

# 4. Встановлюємо Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/google.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# 5. Встановлюємо Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# 6. Робоча директорія
WORKDIR /app

# 7. Копіюємо Maven проєкт і завантажуємо залежності
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src

# 8. CMD по замовчуванню (можеш змінити у Jenkins pipeline)
CMD ["mvn", "test", "-Dselenide.headless=true"]