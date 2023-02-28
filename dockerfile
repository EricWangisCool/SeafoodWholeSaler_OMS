FROM openjdk:17
MAINTAINER Eric Wang <eynyseric520@gmail.com>

# 將檢測MySQL是否Ready的腳本加入，資料來源於: https://github.com/vishnubob/wait-for-it
COPY wait-for-it.sh /wait-for-it.sh

# 調整權限
RUN chmod +x /wait-for-it.sh

# 將目標WAR放入Docker Image中
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} seafood-wholesaler-oms-1.0.0.war

# 此對外Port設定
EXPOSE 8080
