FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -r -u 1001 app
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
USER app
ENV JAVA_TOOL_OPTIONS="-Xms64m -Xmx192m"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]