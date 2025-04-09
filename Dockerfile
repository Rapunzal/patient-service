FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:17-jre-focal AS runner

WORKDIR /app

COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 9092

ENTRYPOINT ["java", "-jar", "app.jar"]