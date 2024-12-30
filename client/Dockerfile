# Stage 1: Build the React app
FROM node:18-alpine as builder

# Set the working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm ci

# Copy the rest of the application code
COPY . .

# Set environment variables for React
ARG REACT_APP_SENTRY_DSN
ENV REACT_APP_SENTRY_DSN=$REACT_APP_SENTRY_DSN

# Build the application
RUN npm run build

# Stage 2: Serve the application using nginx
FROM nginx:alpine

# Copy custom nginx configuration
COPY ./.nginx/nginx.conf /etc/nginx/nginx.conf

# Remove the default nginx HTML files
RUN rm -rf /usr/share/nginx/html/*

# Copy the built React app from the builder stage
COPY --from=builder /app/build /usr/share/nginx/html

# Expose the application's port
EXPOSE 3000

# Start nginx
ENTRYPOINT ["nginx", "-g", "daemon off;"]
