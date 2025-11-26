FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN echo -e '#!/bin/sh\n\
echo "PORT=$PORT" > .env\n\
echo "DB_HOST=$DB_HOST" >> .env\n\
echo "DB_PORT=$DB_PORT" >> .env\n\
echo "DB_DATABASE=$DB_DATABASE" >> .env\n\
echo "DB_USERNAME=$DB_USERNAME" >> .env\n\
echo "DB_PASSWORD=$DB_PASSWORD" >> .env\n\
echo "JWT_SECRET=$JWT_SECRET" >> .env\n\
echo "HOSTNAME=$HOSTNAME" >> .env\n\
echo "MAIL_USER=$MAIL_USER" >> .env\n\
echo "MAIL_PASSWORD=$MAIL_PASSWORD" >> .env\n\
exec java -jar app.jar' > /app/entrypoint.sh && chmod +x /app/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]
