# Usando Maven para compilar a aplicação
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml /app/
COPY src ./src

RUN mvn clean package -DskipTests

# Usando Eclipse Temurin 21 para a imagem final
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ARG CLOUDINARY_CLOUD_NAME
ARG CLOUDINARY_API_KEY
ARG CLOUDINARY_API_SECRET

ENV CLOUDINARY_CLOUD_NAME=${CLOUDINARY_CLOUD_NAME}
ENV CLOUDINARY_API_KEY=${CLOUDINARY_API_KEY}
ENV CLOUDINARY_API_SECRET=${CLOUDINARY_API_SECRET}

ENTRYPOINT ["java", "-jar", "app.jar"]
