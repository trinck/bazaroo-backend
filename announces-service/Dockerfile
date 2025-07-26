
# Étape 1: Build avec Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
#FROM openjdk:17-oracle
LABEL authors="trinck"
VOLUME /tmp
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Téléchargement des dépendances et build
RUN mvn clean package -DskipTests

# Étape 2: Image finale avec JRE
#FROM eclipse-temurin:17-jre-alpine
FROM openjdk:17-oracle
WORKDIR /app

# Installation des outils nécessaires
#RUN apk add --no-cache tzdata
ENV TZ=Europe/Paris

# Création d'un utilisateur non-root
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

# Copie du JAR depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Configuration des variables d'environnement par défaut
#ENV JAVA_OPTS="-Xms512m -Xmx512m"

# Port exposé
EXPOSE 8086

# Point d'entrée avec configuration de la JVM
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]
