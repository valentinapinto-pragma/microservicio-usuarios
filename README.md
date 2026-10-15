# Microservicio de Usuarios - Plazoleta de Comidas

Backend para la gestión centralizada de usuarios, roles y autenticación de la plataforma multirrestaurante Plazoleta de Comidas.

---

## 🏛️ Arquitectura

El microservicio está construido bajo los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** y **Clean Architecture**:
* **Dominio:** Contiene las entidades puras del negocio, reglas de decisión, excepciones semánticas y los puertos de entrada y salida, totalmente desacoplado de frameworks y tecnologías externas.
* **Aplicación:** Orquesta la interacción mediante DTOs con validaciones de entrada, mappers de transferencia y handlers de caso de uso.
* **Infraestructura:** Aloja los adaptadores de entrada (controladores REST) y adaptadores de salida (persistencia con Spring Data JPA y encriptación de seguridad con BCrypt).
* **Configuración:** Composition root encargado del registro e inyección de beans en el contexto de Spring.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 11
* **Framework:** Spring Boot 2.7.2 (Spring Web, Spring Data JPA, Spring Validation)
* **Base de Datos:** MySQL 8.x
* **Seguridad:** Spring Security Crypto (BCrypt)
* **Mapeo de Objetos:** MapStruct 1.5.2.Final
* **Productividad:** Project Lombok
* **Documentación:** Springdoc OpenAPI 3 / Swagger UI
* **Pruebas:** JUnit 5, Mockito, MockMvc

---

## ⚙️ Configuración y Requisitos

### Requisitos Previos:
* Java Development Kit (JDK) 11 instalado.
* Servidor MySQL corriendo (localmente o en Docker) en el puerto `3306`.

### Variables de Configuración (`application.yml`):
El microservicio soporta las siguientes variables de entorno con valores por defecto para desarrollo local:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `DB_HOST` | Host de la base de datos MySQL | `localhost` |
| `DB_PORT` | Puerto de conexión MySQL | `3306` |
| `DB_NAME` | Nombre de la base de datos | `users_db` |
| `DB_USER` | Usuario de MySQL | `root` |
| `DB_PASSWORD` | Contraseña de MySQL | *(definida en local)* |
| `server.port` | Puerto de escucha del microservicio | `8081` |

---

## 🚀 Ejecución del Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/valentinapinto-pragma/microservicio-usuarios.git
   ```
2. Compilar y ejecutar con Gradle:
   ```bash
   ./gradlew bootRun
   ```
3. El servicio quedará disponible en: `http://localhost:8081`

---

## 📖 Documentación Interactiva (Swagger / OpenAPI)

Una vez iniciado el microservicio, la documentación OpenAPI y la interfaz de Swagger UI están disponibles en:
👉 `http://localhost:8081/swagger-ui.html`

---

## 🧪 Pruebas Automatizadas y Calidad

Para ejecutar la suite de pruebas unitarias y de integración web:
```bash
./gradlew test
```

### Colección de Postman:
En la carpeta `/postman` se encuentra disponible la colección completa y el entorno listos para importar:
* `postman/Plazoleta_Postman_Collection.json`
* `postman/Plazoleta_Local_Environment.json`
