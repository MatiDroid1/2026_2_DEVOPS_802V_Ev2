# Changelog

Registro de cambios relevantes del proyecto Vinilo Store, ordenados por versión.

## [Unreleased]

### Added
- Pipeline CI/CD en GitHub Actions con pruebas, análisis de seguridad y despliegue.
- Imágenes Docker etiquetadas con el SHA del commit para trazabilidad.

## [0.2.0] - 2026-10-24

### Added
- Dockerfile multi-etapa para el microservicio Spring Boot.
- Dockerfile.frontend con Nginx y reverse proxy hacia la API.
- docker-compose.yml con PostgreSQL, backend y frontend, con healthchecks y límites de recursos.
- Pruebas unitarias con JUnit 5 y Mockito, y cobertura con JaCoCo.
- Análisis de dependencias con Snyk y de calidad con SonarCloud, ambos bloqueantes.
- Despliegue automático en AWS EC2 solo en push a `main`.

## [0.1.0] - 2026-10-20

### Added
- Microservicio de álbumes con Spring Boot, Lombok y JPA (listar, crear, comprar, eliminar).
- Frontend estático con catálogo, buscador y portadas.
- Carga inicial de álbumes mediante DataInitializer.