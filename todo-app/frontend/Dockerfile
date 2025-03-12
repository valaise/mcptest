# Build stage
FROM node:20-alpine as build
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Erstelle eine leere Worker-Datei, um den Fehler zu vermeiden
RUN mkdir -p src/app/workers
RUN touch src/app/workers/dummy.worker.ts
RUN echo "// Dummy worker file" > src/app/workers/dummy.worker.ts

# Install additional dependencies
RUN npm install -g @angular/cli
RUN npm install path-browserify os-browserify crypto-browserify stream-browserify buffer process

# Build the application
RUN ng build --configuration production

# Production stage
FROM nginx:alpine
COPY --from=build /app/dist/todo-frontend /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose the port
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"] 