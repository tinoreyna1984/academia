# Academia backend

## Dependencias empleadas
* Spring Web
* Spring Data JPA
* Spring Security
* Validation
* Lombok
* Spring Boot Dev Tools
* MySQL Driver (o para otro motor de base de datos)
* JWT:
```xml
		<!-- JWT -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.5</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.5</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.11.5</version>
		</dependency>
```
* Jackson:
```xml
		<!--Jackson-->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.15.2</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.15.2</version>
		</dependency>
```
* Swagger
```xml
		<!--Swagger-->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.0.4</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency> <!--requiere Validation-->
```

## Login de prueba (par usuario/clave)
* admin/Tr20010878 (administrador)
* treyna/u$uari0CRM (usuario)

## Ejecución
Desde el programa principal AcademiaApplication.java (con IntelliJ IDEA u otro IDE).
También con el ejecutable de Maven si aplica.

## Docker
* Levantar contenedor:
```bash
docker-compose up # desde la raíz
```

## Endpoints:
Definidos en el archivo: Academia - Springboot.postman_collection.json (usarlo con Postman)

## Swagger:
Probar en http://localhost:4007/swagger-ui/index.html