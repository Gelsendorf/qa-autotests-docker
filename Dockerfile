# 1. База Jenkins
FROM jenkins/jenkins:lts

# 2. Переходимо на root для встановлення пакетів
USER root

# 3. Встановлюємо Maven
RUN apt-get update && \
    apt-get install -y maven wget gnupg unzip curl && \
    rm -rf /var/lib/apt/lists/*

# 4. Встановлюємо Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# 5. Встановлюємо Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# 6. Повертаємося на користувача Jenkins
USER jenkins

# 7. Робоча директорія для проєкту
WORKDIR /app

# 8. Копіюємо Maven проєкт і завантажуємо залежності
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src

# 9. CMD для запуску тестів
CMD ["mvn", "test", "-Dselenide.headless=true"]
