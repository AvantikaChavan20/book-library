FROM maven:3.9.9-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17
COPY --from=build target/book-library-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","/app.jar"]