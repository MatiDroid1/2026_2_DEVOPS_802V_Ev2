# Guía de contribución

Este documento describe cómo trabajamos en Vinilo Store.

## Ramas

- `main`: versión estable. Cada push a esta rama dispara el despliegue en AWS.
- `dev`: rama de trabajo. Los cambios se preparan aquí y entran a `main` por Pull Request.

## Flujo de trabajo

1. Actualizar `dev` con `git pull origin dev`.
2. Hacer un cambio pequeño y verificable.
3. Ejecutar `./mvnw clean verify` para comprobar que las pruebas pasan.
4. Crear un commit con un mensaje claro.
5. Publicar `dev` en GitHub.
6. Abrir un Pull Request de `dev` hacia `main`.
7. Esperar a que el pipeline termine en verde y fusionar.

## Mensajes de commit

Usamos prefijos para indicar el tipo de cambio:

- `feat:` nueva funcionalidad.
- `fix:` corrección de un error.
- `test:` pruebas nuevas o modificadas.
- `ci:` cambios en el pipeline.
- `docs:` documentación.

## Pull Requests

Cada Pull Request debe explicar qué cambió, por qué y cómo se probó.
Nunca se suben credenciales, claves `.pem`, tokens ni archivos `.env`.

## Revisión

Antes de fusionar, el pipeline completo (pruebas, Snyk y SonarCloud) debe pasar y el diff debe corresponder al cambio descrito.