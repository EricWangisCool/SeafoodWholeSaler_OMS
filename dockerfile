#
# Build stage
#
FROM openjdk:17
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} seafood-wholesaler-oms-1.0.0.war
ENTRYPOINT ["java","-jar","/seafood-wholesaler-oms-1.0.0.war"]
EXPOSE 8080
