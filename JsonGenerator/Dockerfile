FROM maven:3.8.1-openjdk-17 as buildstage

# Copy Maven wrapper script and project files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Convert mvnw script to Unix format
RUN dos2unix mvnw && chmod +x mvnw

# Build the application
RUN ./mvnw package

# Copy the compiled jar file
COPY target/*.jar app.jar





