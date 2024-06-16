<p align="center">
  <img src="img/Badge-Spring.png" alt="Insignia de Alura Latam">
</p>

# Foro Hub - Desafío de Altura

Este es un proyecto de foro web diseñado para el desafío de Altura. El proyecto permite a los usuarios crear, leer, actualizar y eliminar tópicos, con un enfoque en la seguridad y la validación de datos.

## Tecnologías Utilizadas

El proyecto utiliza las siguientes tecnologías y bibliotecas:

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot 3.3.0**: Framework para facilitar el desarrollo de aplicaciones Java.
    - `spring-boot-starter-data-jpa`: Para la interacción con la base de datos utilizando JPA.
    - `spring-boot-starter-security`: Para la seguridad de la aplicación.
    - `spring-boot-starter-validation`: Para la validación de datos.
    - `spring-boot-starter-web`: Para crear servicios web RESTful.
    - `spring-boot-devtools`: Herramientas de desarrollo para Spring Boot.
    - `spring-boot-starter-test`: Dependencias para pruebas unitarias y de integración.
- **Flyway**: Herramienta para la migración y versionado de la base de datos.
    - `flyway-core`
    - `flyway-mysql`
- **MySQL**: Sistema de gestión de bases de datos utilizado.
    - `mysql-connector-j`: Conector JDBC para MySQL.
- **Lombok**: Biblioteca para reducir el código repetitivo en Java.
- **JWT (JSON Web Tokens)**:
    - `java-jwt`: Biblioteca para trabajar con tokens JWT.
    - `jjwt`: Otra biblioteca para trabajar con JWT.
- **OpenAPI**:
    - `springdoc-openapi-starter-webmvc-ui`: Para la documentación de la API.
- **Spring Security Test**: Herramientas para probar la seguridad de la aplicación.

## Configuración del Proyecto

### Requisitos

- Java 17
- Maven 3.6+
- MySQL

### Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu_usuario/foro-hub.git
   cd foro-hub
   ```

2. Configura la base de datos en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/topico?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
api.security.secret=tu_secreto

# Flyway
spring.flyway.enabled=true
spring.flyway.url=jdbc:mysql://localhost:3306/topico
spring.flyway.user=tu_usuario
spring.flyway.password=tu_contraseña
```

3. Ejecuta las migraciones de Flyway para configurar la base de datos:
    ```
    mvn flyway:migrate
    ```
   
4. Compila y ejecuta la aplicación:
    ```
    mvn spring-boot:run
    ```

## JWT Configuration
Asegúrate de configurar correctamente el secreto para los tokens JWT en el archivo application.properties:

# Endpoints Principales

La API expone los siguientes endpoints:

## Usuarios:

- `POST /usuarios`: Registrar un nuevo usuario.
- `GET /usuarios/{email}`: Obtener un usuario por su email.

## Tópicos:

- `POST /topicos`: Crear un nuevo tópico.
- `GET /topicos`: Listar todos los tópicos.
- `GET /topicos/{id}`: Obtener un tópico por su ID.
- `PUT /topicos/{id}`: Actualizar un tópico existente.
- `DELETE /topicos/{id}`: Eliminar un tópico por su ID.

## Seguridad

La aplicación utiliza JWT para la autenticación y autorización de usuarios. 
Asegúrate de configurar correctamente el secreto para los tokens JWT en el archivo `application.properties`.

## Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación de la API en el siguiente endpoint:

http://localhost:8080/swagger-ui.html

