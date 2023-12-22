# Import base image
FROM node:16-alpine as builder

# Set the working directory
WORKDIR /app

# Copy the dependencies package to container
COPY package.json .

# Install dependencies
RUN npm install

# Copy all files to container
COPY . .

# Expose port 3000
EXPOSE 3000

# Test Echo
RUN echo Message1

# Run echo test 
RUN echo "Hello there!" >> /app/hello.txt
RUN cat /app/hello.txt

# Run start command
COPY online/.env .
ENV REACT_APP_API_FULL_HOST /api
RUN ls /app -a
RUN npm run build

# Import nginx image
FROM nginx:stable-alpine

# Set up certificates
COPY certificates /srv/certificates

### Test CAT
RUN echo "Hello there 2!" >> /srv/certificates/hello2.txt
RUN ls /srv
RUN ls /srv/certificates
RUN cat /srv/certificates/hello2.txt
RUN cat /srv/certificates/full-private-key-1.txt
RUN cat /srv/certificates/full-bundle-1.crt

# Set up nginx
COPY --from=builder /app/build /usr/share/nginx/html
COPY conf/nginx.conf /etc/nginx/conf.d/default.conf
COPY scripts /srv/scripts

### Test
RUN cat /srv/scripts/run.sh
RUN ls /srv

# Expose the HTTP Port
EXPOSE 80

# Expose the HTTPS Port
EXPOSE 443

# Run NGINX
CMD ["nginx", "-g", "daemon off;"]