# lab5 — Integration lab

Краткое описание
Проект содержит набор сервисов для лабораторной работы по интеграции (Service1..Service4) и папку integration-lab с docker-compose и инфраструктурными файлами. Сервисы — Java/Maven-проекты с Dockerfile.

Структура (верхний уровень)
- Service1, Service2, Service3, Service4 — Maven/Java сервисы
- integration-lab — docker-compose, kubeconfig и инфраструктура
- SOA_integration, .github, .vscode и др.

Требования
- JDK 17+ (рекомендовано) и Maven (если не используете mvnw)
- Docker и docker-compose

Сборка и запуск (локально)
1. Соберите JAR для каждого сервиса (в Linux/macOS):
   cd Service1 && ./mvnw clean package -DskipTests
   cd ../Service2 && ./mvnw clean package -DskipTests
   cd ../Service3 && ./mvnw clean package -DskipTests
   cd ../Service4 && ./mvnw clean package -DskipTests

2. Соберите Docker-образы и запустите compose:
   docker build -t allaybro/1-service:latest ./Service1
   docker build -t allaybro/2-service:latest ./Service2
   docker build -t allaybro/3-service:latest ./Service3
   docker build -t allaybro/4-service:latest ./Service4
   docker-compose -f integration-lab/docker-compose.yml up

Альтернатива: изменить integration-lab/docker-compose.yml, заменить image на build с соответствующими контекстами (./Service1 и т.д.), тогда docker-compose будет собрать образы автоматически.

Важные замечания
- Текущий docker-compose использует image: allaybro/1-service:latest и т.п. — если образы не опубликованы в реестре, docker-compose попробует их скачать и завершится ошибкой. Либо собрать локально, либо изменить на build.
- depends_on не гарантирует, что сервис полностью готов — при необходимости добавьте healthcheck или задержку/повторные подключения.
- В репозитории есть бинарные и kubeconfig-файлы (например integration-lab/kubeconfig.yaml, AzureCLI.msi). Убедитесь, что в них нет секретов; по возможности удалите чувствительные данные из публичного репозитория.

Автор: AllayBro
