# CredEdu - Testimonial CMS (API)

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-green?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=flat-square&logo=docker)
![Render](https://img.shields.io/badge/Deploy-Render-black?style=flat-square&logo=render)

Este proyecto es el **backend** de un Sistema de Gestión de Contenidos (CMS) diseñado para la recopilación, moderación y publicación de testimonios para el sector EdTech. Esta API RESTful maneja la seguridad, la lógica de negocio y la integración con servicios en la nube para multimedia, sirviendo como núcleo para la aplicación web principal.

---

## 💻 Ecosistema / Frontend

Este backend sirve a una aplicación cliente desarrollada en React. Puedes ver el repositorio y el despliegue del frontend aquí:

| Recurso | Link |
| :--- | :--- |
| **📱 Aplicación Web (Deploy)** | **https://testimonial-cms-client.onrender.com/** (Render) |
| **📂 Repositorio Frontend** | **https://github.com/nicolaselozano/testimonial-cms-client** |

---

## 👥 Equipo de Desarrollo

Este proyecto fue realizado como parte de una simulación laboral en **No Country**.

| Rol | Nombre | Contacto / GitHub |
| :--- | :--- | :--- |
| **Backend Developer** | **Nicolás Lozano** | [@nicolaselozano](https://github.com/nicolaselozano) |
| **Backend Developer** | **María Valentina Calogeropulos** | [@ValenCalog](https://github.com/ValenCalog) |
| **Backend Developer** | **Walter Fernando Laborde** | [@walterLaborde](https://github.com/walterLaborde) |
| **UX/UI Designer** | **Gabriela Lezama Chacón** | [Behance](https://www.behance.net/gabylezama) |

---

## 📐 Arquitectura y Diseño de Datos

### Diagrama Entidad-Relación (DER)
A continuación se presenta el modelo de datos utilizado en PostgreSQL, destacando las relaciones entre Usuarios, Roles, Testimonios y Entidades Multimedia.

<img width="1280" height="586" alt="DERTestimonial" src="https://github.com/user-attachments/assets/a232274c-22d4-4bdb-949a-3f0de49b2783" />

---

## ✨ Características Principales

* **Gestión de Testimonios:** Flujo completo de moderación (Pendiente → Aprobado/Rechazado).
* **Multimedia:** Carga de imágenes y videos optimizados con **Cloudinary**.
* **Seguridad:** Autenticación **Google OAuth2** + JWT en Cookies HttpOnly (Stateless).
* **Roles:** Control de acceso granular (`ADMIN` vs `USER`).
* **API Pública:** Endpoint `/api/testimonials/search` abierto para integración en landings externas.

---

## 🛠 Stack Tecnológico

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.5.7
* **Base de Datos:** PostgreSQL
* **Seguridad:** Spring Security, OAuth2 Client, JWT
* **Almacenamiento:** Cloudinary SDK
* **Documentación:** SpringDoc OpenAPI (Swagger UI)
* **Build Tool:** Gradle

---

## ⚙️ Variables de Entorno

Para correr el proyecto localmente, configura estas variables en tu IDE o en un archivo `.env` en la raíz (basado en `application.properties`):

| Variable | Descripción |
| :--- | :--- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/` |
| `DB_NAME` | Nombre de la base de datos (ej: `csm_db`) |
| `DB_USER` | Usuario de PostgreSQL |
| `DB_PASS` | Contraseña de PostgreSQL |
| `JWT_SECRET_KEY` | Clave secreta para JWT (Min 32 chars) |
| `GOOGLE_CLIENT_ID` | ID de Cliente de Google Cloud |
| `GOOGLE_CLIENT_SECRET`| Secreto de Cliente de Google |
| `CLOUDINARY_NAME` | Cloud Name de Cloudinary |
| `CLOUDINARY_KEY` | API Key de Cloudinary |
| `CLOUDINARY_SECRET` | API Secret de Cloudinary |
| `GOOGLE_REDIRECT` | URL del Frontend para redirección post-login |

---

## 🚀 Instalación Local

1.  **Clonar repositorio:**
    ```bash
    git clone [https://github.com/usuario/testimonial-cms-api.git](https://github.com/usuario/testimonial-cms-api.git)
    cd testimonial-cms-api
    ```

2.  **Configurar Base de Datos:**
    Asegúrate de tener PostgreSQL corriendo y crea una base de datos vacía.

3.  **Ejecutar:**
    ```bash
    # Linux / Mac
    ./gradlew bootRun

    # Windows
    gradlew.bat bootRun
    ```

4.  **Acceder:**
    El servidor iniciará en `http://localhost:8080`.

---

## 📖 Documentación

Una vez iniciada la aplicación, puedes probar todos los endpoints en:

👉 **http://localhost:8080/swagger-ui/index.html**

---

