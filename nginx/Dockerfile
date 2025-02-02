# Utilise l'image officielle Nginx
FROM nginx:latest

# Copier les fichiers de configuration dans le conteneur
COPY conf.d /etc/nginx/conf.d

# Copier les certificats SSL dans le conteneur
COPY certs /etc/ssl

# Exposer les ports 80 (HTTP) et 443 (HTTPS)
EXPOSE 80 443