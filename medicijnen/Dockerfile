# https://spring.io/guides/gs/spring-boot-docker/
FROM openjdk:11
COPY . .
RUN ./mvnw package -DskipTests
CMD ["java","-jar","target/medicijnen-0.0.1-SNAPSHOT.jar"]
