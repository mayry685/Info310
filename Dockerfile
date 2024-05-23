# Use the official Gradle image as base
FROM gradle:jdk21

# Set the working directory in the container
WORKDIR /usr/src/app

# Clone the repository directly into the working directory
RUN git clone https://github.com/mayry685/Info310.git .

# Ensure the gradlew script has execute permission
RUN chmod +x ./gradlew

# Build the Gradle project
RUN ./gradlew build

# Expose port 8087 (assuming this is the correct port)
EXPOSE 8087

# Command to run the application
CMD ["./gradlew", "run"]

