#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17
COPY --from=build /home/app/target/saborYNoche-0.0.1-SNAPSHOT.jar /usr/local/lib/saborynoche.jar
# Añadir estas líneas para que la aplicación escuche en el host 0.0.0.0 y el puerto proporcionado por la variable de entorno PORT
ENV PORT=8080
EXPOSE $PORT
ENTRYPOINT ["java","-jar","/usr/local/lib/saborynoche.jar", "--server.address=0.0.0.0", "--server.port=$PORT"]
