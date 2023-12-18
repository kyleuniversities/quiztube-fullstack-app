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
CMD ["npm", "run", "build"]