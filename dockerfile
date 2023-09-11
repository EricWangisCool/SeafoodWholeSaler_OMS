# Build stage
ARG WORK_DIR=/usr/src/app
FROM maven:3.8.4-openjdk-17 as build
WORKDIR ${WORK_DIR}
COPY . .
RUN mvn install

# Package stage
FROM openjdk:17
MAINTAINER Eric Wang <eynyseric520@gmail.com>

# 將檢測MySQL是否Ready的腳本加入，資料來源於: https://github.com/vishnubob/wait-for-it
COPY wait-for-it.sh /wait-for-it.sh

# 調整權限
RUN chmod +x /wait-for-it.sh

# 將目標WAR放入Docker Image中
COPY --from=build ${WORK_DIR}/target/*.war /seafood-wholesaler-oms-1.0.0.war

# 此對外Port設定
EXPOSE 8080
