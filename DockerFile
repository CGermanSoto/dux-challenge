# Usamos una imagen de Maven para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Construimos el proyecto y generamos el archivo JAR
RUN mvn clean package -DskipTests

# Usamos una imagen de OpenJDK para ejecutar la aplicación
FROM eclipse-temurin:17-jdk

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/challenge-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
