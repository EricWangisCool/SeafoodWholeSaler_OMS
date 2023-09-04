#
# Build stage
#
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /usr/src/app
COPY . .
RUN mvn install

#
# Package stage
#
FROM openjdk:17
COPY --from=build /usr/src/app/target/*.war /seafood-wholesaler-oms-1.0.0.war
ENTRYPOINT ["java","-jar","/seafood-wholesaler-oms-1.0.0.war"]
