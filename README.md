### Base de Datos PostgreSQL[pgAdmin 4]
```sql
CREATE TABLE Usuarioorq (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255),
    curp CHAR(18) NOT NULL UNIQUE,
    codigo_postal CHAR(5)
);
INSERT INTO Usuarioorq (nombre, email, telefono, curp, codigo_postal)
VALUES ('Felipe Franco', 'francoslide@gmail.com', '9512560018', 'FAMF990421HOCBNL04', '71506');
SELECT current_database();
SELECT * FROM Usuarioorq;
```
### Ocupe Spring Reactive Web version de Java 17
####
### Endpoints de interes con el Orquestador
## URL:http://localhost:8016/api/Orquestador/crearUsuario
```json
{
    "nombre": "Melee Santiago",
    "email": "francoslide4849@gmail.com",
    "telefono": "9512560018",
    "curp": "FAMF990421HOCBNL08",
    "codigoPostal": "71506"
}
```
### [UPDATE] De tipo Post
## URL:http://localhost:8016/api/Orquestador/actualizarUsuario
## body
```json
{
    "id": 1,
    "nombre": "Franco Fabian Mendoza",
    "email": "francosliW90@gmail.com",
    "telefono": "9512560018",
    "curp": "FAMF990421HOCBNL04",
    "codigoPostal": "71506"
}
```
###APIGATEWAY port:8016
```yml
server:
  port: 8016

spring:
  application:
    name: msvc-gateway
  output:
    ansi:
      enabled: ALWAYS
  cloud:
    gateway:
      mvc:
        routes:
          - id: msOrquest
            uri: http://localhost:8080
            predicates:
              - Path=/api/Orquestador/**
          - id: msDominio
            uri: http://localhost:8032
            predicates:
              - Path=/api/Usuarios/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8010/eureka/


```
