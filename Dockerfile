FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Add environment variable debugging
ENV JAVA_OPTS="-Dspring.profiles.active=production"

CMD ["java", "-jar", "target/notesBackend-0.0.1-SNAPSHOT.jar"]