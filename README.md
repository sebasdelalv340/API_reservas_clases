# API de Gestión de Clases, Reservas y Usuarios

## **1. Introducción**

### **1.1. Finalidad de la API**
Esta API permite gestionar un sistema de clases y reservas, así como la administración de usuarios. Los objetivos principales son:
- **Gestión de usuarios**: Registro, autenticación y manejo de permisos.
- **Gestión de clases**: Crear, actualizar, consultar y eliminar clases.
- **Gestión de reservas**: Registrar y consultar reservas, respetando las reglas de negocio.
- **Autenticación y autorización**: Control de acceso basado en roles (`ADMIN` y `USER`).

### **1.2. Público objetivo**
La API está diseñada para aplicaciones que necesiten manejar usuarios autenticados y proveer funcionalidades de reserva de clases, como sistemas de gimnasios o centros educativos.

---

## **2. Modelos y Tablas de Base de Datos**

### **2.1. Usuario**
Representa a los usuarios del sistema.

| Campo          | Tipo          | Descripción                                |
|-----------------|---------------|--------------------------------------------|
| `id`           | Long          | Identificador único del usuario            |
| `username`     | String        | Nombre de usuario                          |
| `password`     | String        | Contraseña cifrada                         |
| `roles`        | String        | Rol del usuario (`USER` o `ADMIN`)         |

### **2.2. Clase**
Representa las clases que pueden ser reservadas.

| Campo          | Tipo              | Descripción                                |
|-----------------|-------------------|--------------------------------------------|
| `id`           | Long              | Identificador único de la clase            |
| `nombre`       | String            | Nombre de la clase                         |
| `descripcion`  | String            | Descripción detallada de la clase          |
| `aforo`        | Int               | Capacidad máxima de la clase               |
| `fechaClase`   | LocalDateTime     | Fecha y hora de la clase                   |

### **2.3. Reserva**
Representa las reservas hechas por los usuarios.

| Campo          | Tipo              | Descripción                                |
|-----------------|-------------------|--------------------------------------------|
| `id`           | Long              | Identificador único de la reserva          |
| `clase`        | Clase             | Clase reservada                            |
| `usuario`      | Usuario           | Usuario que realizó la reserva             |
| `fechaCreacion`| LocalDateTime     | Fecha de creación de la reserva            |

---

## **3. Endpoints de la API**

### **3.1. UsuarioController**

#### **Registro de usuario**
- **Endpoint**: `POST /usuarios/register`
- **Descripción**: Registra un nuevo usuario.
- **Cuerpo de la solicitud**:
    ```json
    {
        "username": "usuario1",
        "password": "contraseña",
        "roles": "USER"
    }
    ```
- **Respuesta**: `201 Created`

#### **Login de usuario**
- **Endpoint**: `POST /usuarios/login`
- **Descripción**: Inicia sesión y genera un token de autenticación JWT.
- **Cuerpo de la solicitud**:
    ```json
    {
        "username": "usuario1",
        "password": "contraseña"
    }
    ```
- **Respuesta**: 
    ```json
    {
        "token": "eyJhbGciOiJIUzI1..."
    }
    ```

#### **Consultar usuario por ID**
- **Endpoint**: `GET /usuarios/get/{id}`
- **Descripción**: Obtiene un usuario por su ID (solo si coincide con el usuario autenticado o si es administrador).
- **Respuesta**: `200 OK`

#### **Actualizar usuario**
- **Endpoint**: `PUT /usuarios/update/{id}`
- **Descripción**: Actualiza los datos de un usuario.
- **Cuerpo de la solicitud**:
    ```json
    {
        "username": "nuevo_usuario",
        "password": "nueva_contraseña"
    }
    ```
- **Respuesta**: `200 OK`

#### **Eliminar usuario**
- **Endpoint**: `DELETE /usuarios/delete/{id}`
- **Descripción**: Elimina un usuario específico (solo accesible para administradores).
- **Respuesta**: `200 OK`

#### **Obtener todos los usuarios**
- **Endpoint**: `GET /usuarios/getAll`
- **Descripción**: Devuelve una lista de todos los usuarios registrados (solo accesible para administradores).
- **Respuesta**: `200 OK`

---

### **3.2. ClaseController**

#### **Consultar todas las clases**
- **Endpoint**: `GET /clases`
- **Descripción**: Obtiene la lista de todas las clases disponibles.
- **Respuesta**: `200 OK`

#### **Consultar clase por ID**
- **Endpoint**: `GET /clases/{id}`
- **Descripción**: Obtiene información sobre una clase específica.
- **Respuesta**: `200 OK`

#### **Registrar una nueva clase**
- **Endpoint**: `POST /clases/register`
- **Descripción**: Registra una nueva clase (solo accesible para administradores).
- **Cuerpo de la solicitud**:
    ```json
    {
        "nombre": "Yoga",
        "descripcion": "Clase de yoga relajante",
        "aforo": 20,
        "fechaClase": "2024-12-15T14:00:00"
    }
    ```
- **Respuesta**: `201 Created`

#### **Actualizar clase**
- **Endpoint**: `PUT /clases/update/{id}`
- **Descripción**: Actualiza una clase específica (solo accesible para administradores).

#### **Eliminar clase**
- **Endpoint**: `DELETE /clases/delete/{id}`
- **Descripción**: Elimina una clase específica (solo accesible para administradores).

---

### **3.3. ReservaController**

#### **Registrar una reserva**
- **Endpoint**: `POST /reservas/register`
- **Descripción**: Registra una nueva reserva para una clase.
- **Cuerpo de la solicitud**:
    ```json
    {
        "clase": {"id": 1},
        "usuario": {"id": 1}
    }
    ```
- **Respuesta**: `201 Created`

#### **Consultar una reserva por ID**
- **Endpoint**: `GET /reservas/{id}`
- **Descripción**: Obtiene información de una reserva específica (solo si pertenece al usuario autenticado o si es administrador).
- **Respuesta**: `200 OK`

#### **Consultar todas las reservas**
- **Endpoint**: `GET /reservas/getAll`
- **Descripción**: Devuelve la lista de todas las reservas realizadas (solo accesible para administradores).
- **Respuesta**: `200 OK`

#### **Actualizar una reserva**
- **Endpoint**: `PUT /reservas/update/{id}`
- **Descripción**: Actualiza una reserva existente.
- **Cuerpo de la solicitud**:
    ```json
    {
        "clase": {"id": 2},
        "usuario": {"id": 1},
        "fechaCreacion": "2024-12-14T12:00:00"
    }
    ```
- **Respuesta**: `200 OK`

#### **Eliminar una reserva**
- **Endpoint**: `DELETE /reservas/delete/{id}`
- **Descripción**: Elimina una reserva existente. Solo es posible si faltan más de 2 horas para la clase.
- **Respuesta**: `200 OK`

---

## **4. Lógica de negocio de la API**

### `validarUsuario(usuario: Usuario, authentication: Authentication)`

#### Descripción

Valida si el usuario autenticado es el propietario del `usuario` o si tiene el rol de "ADMIN". Si no se cumple ninguna de estas condiciones, lanza una excepción `AccessDeniedException`.

#### Parámetros

- `usuario`: El objeto `Usuario` que se está validando.
- `authentication`: El objeto que contiene la información del usuario autenticado.

#### Lógica

- Se obtiene el nombre del usuario autenticado.
- Se verifica si el usuario autenticado es el mismo que el `usuario` o si tiene el rol "ADMIN".
- Si no es así, se lanza una excepción `AccessDeniedException`.

#### Excepciones

- **`AccessDeniedException`**: Si el usuario no es el propietario o no tiene el rol "ADMIN".

---

### `validarFecha(reserva: Reserva): Boolean`

#### Descripción

La función `validarFecha` valida si una reserva puede ser eliminada en función de la proximidad a la fecha de la clase. Si quedan menos de 2 horas para el inicio de la clase, no se permite eliminar la reserva y se lanza una excepción. Si quedan más de 2 horas, la función devuelve `true` indicando que la reserva puede ser eliminada.

#### Parámetros

- `reserva`: Objeto de tipo `Reserva` que contiene información sobre la reserva y la clase asociada.

#### Lógica

1. Se obtiene la fecha de la clase asociada a la reserva.
2. Se calcula la diferencia en horas entre la fecha y hora actuales (`LocalDateTime.now()`) y la fecha de la clase (`fecha_clase`).
3. Si la diferencia de tiempo es menor a 2 horas, se lanza una excepción `IllegalArgumentException`.
4. Si la diferencia es mayor o igual a 2 horas, se devuelve `true` indicando que la reserva puede ser eliminada.

#### Excepciones

- **`IllegalArgumentException`**: Si quedan menos de 2 horas para la clase, no se puede eliminar la reserva.

---

### `validarAforo(reservas: Long, aforo: Int): Boolean`

#### Descripción

La función `validarAforo` verifica si el número de reservas realizadas para una clase alcanza el aforo máximo permitido. Si el número de reservas es diferente al aforo máximo, devuelve `true` indicando que aún hay disponibilidad. Si las reservas son iguales al aforo, devuelve `false` indicando que se ha alcanzado el aforo máximo.

#### Parámetros

- `reservas`: Número de reservas realizadas para una clase. Se espera un valor de tipo `Long`.
- `aforo`: Aforo máximo permitido para la clase. Se espera un valor de tipo `Int`.

#### Lógica

La función convierte el número de reservas (`reservas`) a tipo `Int` y lo compara con el aforo. Si ambos valores no son iguales, devuelve `true` indicando que la clase tiene espacio disponible. Si ambos valores son iguales, devuelve `false` indicando que no hay más espacio.

#### Valor de retorno

- **`true`**: Si las reservas realizadas son diferentes al aforo, hay espacio disponible.
- **`false`**: Si las reservas realizadas son iguales al aforo, no hay más espacio disponible.

---

## **5. Servicios**


### **UsuarioService**
- **Funciones principales**:
    - `registrarUsuario(usuario: Usuario)`: Registra un nuevo usuario.
    - `getUsuarioById(id: String, authentication: Authentication)`: Obtiene un usuario por ID.
    - `getAll()`: Devuelve todos los usuarios.
    - `updateUsuario(id: String, usuario: Usuario, authentication: Authentication)`: Actualiza un usuario.
    - `deleteUsuario(id: String, authentication: Authentication)`: Elimina un usuario.
 
---

### **ClaseService**
- **Funciones principales**:
    - `registerClase(clase: Clase, authentication: Authentication)`: Registra una nueva clase (solo para administradores).
    - `getClaseById(id: Long)`: Obtiene una clase específica por su ID.
    - `getAll()`: Devuelve todas las clases disponibles.
    - `updateClase(id: Long, clase: Clase, authentication: Authentication)`: Actualiza los datos de una clase existente (solo para administradores).
    - `deleteClase(id: Long, authentication: Authentication)`: Elimina una clase (solo para administradores).

---

### **ReservaService**
- **Funciones principales**:
    - `registerReserva(reserva: Reserva, authentication: Authentication)`: Registra una nueva reserva.
    - `getReservaById(id: String?, authentication: Authentication)`: Obtiene una reserva específica.
    - `getAll()`: Devuelve todas las reservas.
    - `updateReserva(id: String?, reserva: Reserva, authentication: Authentication)`: Actualiza una reserva existente.
    - `deleteReserva(id: String?, authentication: Authentication)`: Valida si faltan más de 2 horas antes de eliminar.

---

## **6. Excepciones**
La clase `APIExceptionHandler` se utiliza para gestionar de manera centralizada las excepciones que pueden ocurrir en la API. Implementa una lógica de manejo de errores que devuelve respuestas personalizadas en función del tipo de excepción que se produzca. 

Este enfoque mejora la experiencia del cliente al ofrecer mensajes claros y códigos de estado HTTP adecuados en las respuestas.

---

## **Anotaciones utilizadas**
- `@ControllerAdvice`: Indica que esta clase maneja excepciones globalmente para todos los controladores.
- `@ExceptionHandler`: Define métodos que procesan tipos específicos de excepciones.
- `@ResponseStatus`: Establece el código de estado HTTP que se devuelve en las respuestas.
- `@ResponseBody`: Asegura que el objeto devuelto se serialice en JSON y se envíe como cuerpo de la respuesta HTTP.

---

## **Métodos y manejo de excepciones**

### **1. Manejo de excepciones de tipo BAD REQUEST**
#### **Método**: `handleBadRequest`
- **Excepciones manejadas**:
  - `IllegalArgumentException`: Se lanza cuando un argumento proporcionado no es válido.
  - `NumberFormatException`: Ocurre cuando no se puede convertir un valor a un formato numérico.
  - `BadRequestException`: Se utiliza para errores de solicitud mal formulada.
  - `AccessDeniedException`: Se lanza cuando un usuario no tiene permisos para realizar una acción.
- **Código de estado HTTP**: `400 BAD REQUEST`
- **Respuesta devuelta**:
    ```json
    {
        "mensaje": "Descripción del error",
        "ruta": "/ruta-de-la-solicitud"
    }
    ```

---

### **2. Manejo de excepciones de tipo NOT FOUND**
#### **Método**: `handleNotFound`
- **Excepción manejada**:
  - `ChangeSetPersister.NotFoundException`: Se lanza cuando un recurso solicitado no existe.
- **Código de estado HTTP**: `404 NOT FOUND`
- **Respuesta devuelta**:
    ```json
    {
        "mensaje": "Descripción del error",
        "ruta": "/ruta-de-la-solicitud"
    }
    ```

---

### **3. Manejo de excepciones genéricas**
#### **Método**: `handleGeneric`
- **Excepción manejada**:
  - `Exception`: Cualquier excepción no manejada específicamente por los otros métodos.
- **Código de estado HTTP**: `500 INTERNAL SERVER ERROR`
- **Respuesta devuelta**:
    ```json
    {
        "mensaje": "Descripción del error",
        "ruta": "/ruta-de-la-solicitud"
    }
    ```

---

## **Estructura de la respuesta de error**
La respuesta para cualquier excepción sigue el formato definido por la clase `ErrorRespuesta`:

| Campo    | Tipo   | Descripción                                         |
|----------|--------|-----------------------------------------------------|
| `mensaje`| String | Descripción del error ocurrido.                     |
| `ruta`   | String | Ruta de la solicitud HTTP que produjo la excepción. |

**Ejemplo de respuesta**:
```json
{
    "mensaje": "No tienes permiso para realizar esta acción.",
    "ruta": "/reservas/delete/{id}"
}
