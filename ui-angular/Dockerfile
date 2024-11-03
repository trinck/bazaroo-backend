
# Étape 1 : Construction de l'application Angular
FROM node:latest AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
#COPY package*.json ./


# Copier tous les fichiers de l'application Angular
COPY ./ /app

# Installer les dépendances
RUN npm install --legacy-peer-deps



# Générer le build de production
RUN npm run build

# Étape 2 : Servir avec Nginx
FROM nginx:latest AS production

# Copier le build Angular depuis l'étape précédente vers le répertoire Nginx
COPY --from=build /app/dist/front-end/browser /usr/share/nginx/html

# Copier le fichier de configuration Nginx (facultatif)
COPY nginx.conf /etc/nginx/nginx.conf

# Exposer le port 80 pour accéder à l'application
EXPOSE 80

# Lancer Nginx
#CMD ["nginx", "-g", "daemon off;"]
