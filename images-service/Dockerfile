
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
FROM openjdk:17-oracle
WORKDIR /app

# Installation des outils nécessaires
ENV TZ=Europe/Paris

# Création d'un utilisateur non-root

# Copie du JAR depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Configuration des variables d'environnement par défaut

# Port exposé
EXPOSE 8083

# Point d'entrée avec configuration de la JVM
ENTRYPOINT ["java", "-jar", "app.jar"]
