FROM maven:3.9.6-eclipse-temurim-21 AS build
WORKDIR /app

# Corrigido o destino do arquivo pom.xml
COPY pom.xml /app/
COPY src ./src

# Compilar o projeto
RUN mvn clean package -DskipTests

FROM eclipse-temurim:21-jre
WORKDIR /app

# Copiar o arquivo JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Definir variáveis de ambiente

ARG CLOUDINARY_CLOUD_NAME
ARG CLOUDINARY_API_KEY
ARG CLOUDINARY_API_SECRET

ENV AWS_REGION=us-east-1
ENV AWS_S3_BUCKET=generate-qrcode-storage
ENV CLOUDINARY_CLOUD_NAME=${CLOUDINARY_CLOUD_NAME}
ENV CLOUDINARY_API_KEY=${CLOUDINARY_API_KEY}
ENV CLOUDINARY_API_SECRET=${CLOUDINARY_API_SECRET}

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
