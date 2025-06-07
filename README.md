<h1 align="center" id="title">Torneito - API</h1>

<p id="description">
Torneito es un proyecto pensado para organizar torneos y ligas de PES o FIFA entre distintos participantes, permitiendo guardar y consultar los resultados de manera sencilla. Actualmente en desarrollo, esta API RESTful, desarrollada en Java con Spring Boot, facilita la creación y gestión de torneos y ligas, el registro de participantes y el manejo de resultados. El objetivo principal es ofrecer una experiencia ágil y divertida, destacándose por la facilidad para asignar equipos a los participantes (ya sea por sorteo o selección manual), utilizando una base de datos poblada con muchos equipos, selecciones, ligas, confederaciones y temporadas completamente filtrables.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/language-Java-blue.svg" alt="shields">
  <img src="https://img.shields.io/github/last-commit/Alvaro-Rubina/Torneito" alt="shields">
</p>

<h2>🛠️ Tecnologías y Arquitectura</h2>

El backend está construido en Java utilizando Spring Boot y una arquitectura por capas que facilita el mantenimiento y la escalabilidad. Entre las principales tecnologías y patrones implementados o planificados se encuentran:

- <strong>Spring Boot</strong>: Framework principal para el desarrollo de la API.
- <strong>Base de datos MySQL</strong>: Persistencia de datos en una base MySQL.
- <strong>Arquitectura por capas</strong>: Separación clara entre controladores, servicios, repositorios, entidades y DTOs.
- <strong>MapStruct</strong>: Para el mapeo eficiente entre entidades y DTOs.
- <strong>Redis</strong>: Implementación de cache para optimizar el acceso y respuesta de los datos.
- <strong>Swagger</strong>: Documentación interactiva de la API.
- <strong>Auth0</strong> <em>(En desarrollo)</em>: Autenticación segura y centralizada.
- <strong>Docker</strong> <em>(En desarrollo)</em>: Para facilitar el despliegue y la portabilidad del proyecto.
- <strong>React</strong> <em>(Frontend, próximamente)</em>: Interfaz web para los usuarios.

---

<h2>✨ Características principales</h2>

- Creación y gestión de torneos y ligas personalizadas.
- Registro y administración de participantes.
- Asignación de equipos por sorteo o selección manual, con filtros avanzados por confederación, país, liga y temporada.
- Base de datos con equipos, selecciones, ligas y confederaciones.
- Registro y consulta de resultados y posiciones.
- (Próximamente) Visualización de estadísticas y tablas históricas.
- (Próximamente) Autenticación de usuarios.
- (Próximamante) Interfaz grafica con React.

---

<h2>⚡ Requisitos previos</h2>

- Java 17 o superior
- Maven
- MySQL en ejecución (configurar el acceso en <code>application.properties</code>)

---

<h2>📦 Cómo clonar y correr el proyecto</h2>

```bash
git clone https://github.com/Alvaro-Rubina/Torneito.git
cd Torneito
# Importante tener Java 17+ y Maven instalados
mvn clean install
mvn spring-boot:run
```

---

<h2>🗺️ Roadmap</h2>

- ✅ Arquitectura por capas y DTOs
- ✅ Mapeo entidades/DTOs con MapStruct
- ✅ CRUD de entidades principales (torneos, equipos, participantes, resultados, etc.)
- ✅ Implementación de cache con Redis
- ✅ Integración de Swagger para documentación
- ⏳ Integración de Auth0
- ⏳ Dockerización del proyecto
- ⏳ Desarrollo del frontend en React
- ⏳ Asignación avanzada y sorteo de equipos con filtros combinables (frontend)
- ⏳ Visualización de estadísticas y tablas históricas

---

<h2>🎬 Próximamente</h2>

Se agregarán demostraciones del funcionamiento del proyecto y ejemplos de uso en futuras actualizaciones, así como videos del funcionamiento a través del frontend!
