# DUXSoftware 
## Tecnologías Utilizadas
- **Spring Boot 3**
- **Java 17**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Swagger UI**
- **H2 Database**
- **Maven**
- **Docker**

## Requerimientos previos

Debe tener instalado:

- **Java 17**
- **Docker**: En caso de ejecutar en un contenedor
- **Postman**: Para probar los endpoints

## Ejecutar aplicación con un IDE

### 1. Clonar el repositorio

```bash 
git clone https://github.com/CGermanSoto/dux-challenge.git
```
### 2. Moverse a la carpeta del proyecto

```bash 
cd challenge
```

### 3. Traer los últimos cambios de la rama

```bash 
git pull origin master
```

## Ejecutar con Docker

### 1. Construir imagen

```bash 
docker build -t challenge-api .
```
### 2. Ejecutar contenedor

```bash 
docker run -p 8080:8080 challenge-api
```
