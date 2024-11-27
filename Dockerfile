FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app

# 소스 코드 복사
COPY . .

# Gradle로 빌드 (JAR 파일 생성)
RUN ./gradlew clean build -x test

# 2단계: 실행 이미지로 OpenJDK 사용
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
