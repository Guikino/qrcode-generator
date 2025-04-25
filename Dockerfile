FROM maven:3.9.6-openjdk-21 AS build
WORKDIR /app

COPY pom.xml /app/
COPY src ./src

RUN mvn clean package -DskipTests

# Usando a imagem base do OpenJDK 21
FROM openjdk:21
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar


ARG CLOUDINARY_CLOUD_NAME
ARG CLOUDINARY_API_KEY
ARG CLOUDINARY_API_SECRET

ENV CLOUDINARY_CLOUD_NAME=${CLOUDINARY_CLOUD_NAME}
ENV CLOUDINARY_API_KEY=${CLOUDINARY_API_KEY}
ENV CLOUDINARY_API_SECRET=${CLOUDINARY_API_SECRET}

ENTRYPOINT ["java", "-jar", "app.jar"]
