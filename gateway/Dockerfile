FROM openjdk:11
COPY . .
RUN ./mvnw package -DskipTests
CMD ["java","-jar","target/gateway-0.0.1-SNAPSHOT.jar"]