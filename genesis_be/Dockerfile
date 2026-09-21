# 1단계: 빌드용 (Gradle로 프로젝트를 빌드하는 단계)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# 2단계: 실행용 (가볍게 실행만 하는 단계)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]