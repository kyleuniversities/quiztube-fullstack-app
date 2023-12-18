# Import base image
FROM node:16-alpine

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

# Run build command
RUN npm run build

# Import nginx image
FROM nginx:stable-alpine

# Set up nginx
RUN mkdir /usr/share/nginx/buffer

COPY --from=builder /app/out /user/share/nginx/html
COPY --from=builder /app/public /user/share/nginx/html
COPY conf/nginx.conf /etc/nginx/conf.d/default.conf

RUN mkdir /usr/share/nginx/ log

# Expose the HTTP Port
EXPOSE 80

# Run NGINX
CMD ["nginx", "-g", "daemon off;"]