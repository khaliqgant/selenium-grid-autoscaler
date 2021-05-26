FROM maven:3.6.3-adoptopenjdk-8 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f pom.xml clean package -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk8:alpine-slim
COPY --from=build /home/app/target/*.jar /usr/local/lib/grid-utils.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/local/lib/grid-utils.jar"]
