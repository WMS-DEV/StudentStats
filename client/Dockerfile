# Stage 1: Base Node image
FROM node:18-alpine as base
WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm ci

# Copy the rest of the application code
COPY . .

# Set environment variables for React
ARG REACT_APP_SENTRY_DSN
ENV REACT_APP_SENTRY_DSN=$REACT_APP_SENTRY_DSN

ARG REACT_APP_BACKEND_URL
ENV REACT_APP_BACKEND_URL=$REACT_APP_BACKEND_URL

# Stage 2: Production Build
FROM base as build
RUN npm run build

# Stage 3: Development Environment
FROM base as development
EXPOSE 3000
CMD ["npm", "start"]

# Stage 4: Nginx for Production Deployment
FROM nginx:alpine as production
COPY ./.nginx/nginx.conf /etc/nginx/nginx.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 3000
ENTRYPOINT ["nginx", "-g", "daemon off;"]
