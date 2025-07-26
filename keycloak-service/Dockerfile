# Étape 1 : Build du JAR avec Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
VOLUME /tmp
WORKDIR /build

# Copie le code source dans l’image
COPY ./keycloak-event-listener /build

# Compile le JAR avec toutes les dépendances (optionnel : clean package -DskipTests)
RUN mvn clean package -DskipTests

# Étape 2 : Image Keycloak avec le provider intégré
FROM quay.io/keycloak/keycloak:latest

# Copie le JAR compilé depuis l'étape précédente dans le dossier des providers
COPY --from=builder /build/target/keycloak-event-listener-1.0-SNAPSHOT.jar /opt/keycloak/providers/

# Build Keycloak avec le provider intégré
RUN /opt/keycloak/bin/kc.sh build

# Démarre Keycloak en mode dev
#ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]
