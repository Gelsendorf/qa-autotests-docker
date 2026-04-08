# 1. База Jenkins
FROM jenkins/jenkins:lts

# 2. Переходимо на root
USER root

# 3. Встановлюємо Maven та утиліти
RUN apt-get update && \
    apt-get install -y maven wget gnupg2 unzip curl lsb-release && \
    rm -rf /var/lib/apt/lists/*

# 4. Встановлюємо Google Chrome (без apt-key)
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google-linux-signing-key.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-linux-signing-key.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# 5. Встановлюємо Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# 6. Повертаємося на користувача Jenkins
USER jenkins

# 7. Робоча директорія
WORKDIR /app

# 8. Копіюємо Maven проект і завантажуємо залежності
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src

# 9. CMD для запуску тестів
CMD ["mvn", "test", "-Dselenide.headless=true"]