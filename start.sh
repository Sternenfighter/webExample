mvn clean package -DskipTests
docker build -t example .
docker-compose up -d --wait