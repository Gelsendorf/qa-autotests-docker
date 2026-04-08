# 1. Jenkins
FROM jenkins/jenkins:lts

# 2. Root доступ
USER root

# 3. Maven + утиліти
RUN apt-get update && \
    apt-get install -y maven wget gnupg2 unzip curl lsb-release && \
    rm -rf /var/lib/apt/lists/*

# 4. Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/google.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# 5. Docker CLI (щоб Jenkins міг викликати docker)
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# 6. Повертаємось на Jenkins user
USER jenkins