# Tablas para la Base de Datos

## 1. Tabla: `Usuario`
Un usuario puede realizar múltiples reservas.

| Campo       | Tipo          | Restricciones               |
|-------------|---------------|-----------------------------|
| id          | LONG          | PRIMARY KEY, AUTO_INCREMENT |
| username    | STRING        | NOT NULL, UNIQUE            |
| password    | STRING        | NOT NULL                    |
| roles       | STRING        | NOT NULL                    |


## 2. Tabla: `Clase`
Una clase tiene asociado varios usuarios y varias reservas.

| Campo       | Tipo          | Restricciones               |
|-------------|---------------|-----------------------------|
| id          | LONG          | PRIMARY KEY, AUTO_INCREMENT |
| nombre      | STRING        | NOT NULL                    |
| descripcion | STRING        | NOT NULL                    |
| fecha_clase | DATE          | NOT NULL                    |
| reservas    | LIST<RESERVA> | NOT NULL                    |


## 3. Tabla: `Reserva`
Una reserva está asociada a un usuario y a una clase.

| Campo       | Tipo          | Restricciones               |
|-------------|---------------|-----------------------------|
| id          | LONG          | PRIMARY KEY, AUTO_INCREMENT |
| usuario_id  | USUARIO       | NOT NULL, FOREIGN KEY       |
| clase_id    | CLASE         | NOT NULL, FOREIGN KEY       |


# Endpoints de la API

## Endpoints para Usuarios
Gestión de los usuarios registrados en el sistema.

| Método HTTP | Endpoint          | Descripción                                   |
|-------------|-------------------|-----------------------------------------------|
| GET         | /usuarios         | Obtener la lista de todos los usuarios.       |
| GET         | /usuarios/{id}    | Obtener los detalles de un usuario por su ID. |
| POST        | /usuarios         | Registrar un nuevo usuario.                  |
| PUT         | /usuarios/{id}    | Actualizar los datos de un usuario existente. |
| DELETE      | /usuarios/{id}    | Eliminar un usuario por su ID.               |

---

## Endpoints para Clases
Gestión de las clases que se pueden reservar.

| Método HTTP | Endpoint          | Descripción                                   |
|-------------|-------------------|-----------------------------------------------|
| GET         | /clases           | Obtener la lista de todas las clases.         |
| GET         | /clases/{id}      | Obtener los detalles de una clase por su ID.  |
| POST        | /clases           | Crear una nueva clase.                        |
| PUT         | /clases/{id}      | Actualizar los datos de una clase existente.  |
| DELETE      | /clases/{id}      | Eliminar una clase por su ID.                 |

---

## Endpoints para Reservas
Gestión de las reservas realizadas por los usuarios.

| Método HTTP | Endpoint          | Descripción                                   |
|-------------|-------------------|-----------------------------------------------|
| GET         | /reservas         | Obtener la lista de todas las reservas.       |
| GET         | /reservas/{id}    | Obtener los detalles de una reserva por su ID.|
| POST        | /reservas         | Crear una nueva reserva (usuario reserva clase). |
| DELETE      | /reservas/{id}    | Cancelar una reserva por su ID.              |
