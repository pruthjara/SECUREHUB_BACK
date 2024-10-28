# SecureHub_Back Repository

Este repositorio contiene los archivos necesarios para el despliegue del **backend** de la aplicación SecureHub en un entorno Docker y Kubernetes.

## Estructura de Archivos

- **backend/**  
  Carpeta que contiene el código fuente del backend de SecureHub.

- **.gitignore**  
  Archivo de configuración que especifica qué archivos y carpetas deben ser ignorados por Git, evitando que archivos temporales o de configuración local sean subidos al repositorio.

- **Dockerfile**  
  Archivo Docker que define la configuración para construir la imagen del backend de SecureHub. Está configurado para ejecutar `sbt stage`, preparando la aplicación para el entorno de producción en Docker.

- **deploy_backend.sh**  
  Script de despliegue para el backend, que automatiza el proceso de construcción, etiquetado y push de la imagen del backend a un registro de contenedores.

- **docker-compose-backend.yaml**  
  Archivo de configuración de Docker Compose para el backend, que permite construir y ejecutar el contenedor del backend de SecureHub de forma aislada o como parte de una red de servicios.

## Instrucciones para el Despliegue del Backend

1. **Construcción de la Imagen Docker**: Utiliza el archivo `Dockerfile` para construir la imagen del backend. Este Dockerfile está configurado para ejecutar `sbt stage`, optimizando la aplicación para producción en Docker.
2. **Despliegue con Docker Compose**:
   - Usa `docker-compose-backend.yaml` para construir y ejecutar el contenedor del backend. Este archivo de configuración facilita la administración de redes y variables de entorno necesarias para el backend.
3. **Script de Despliegue del Backend**: Ejecuta `deploy_backend.sh` para automatizar el proceso de construcción y despliegue de la imagen en un registro de contenedores.

Este repositorio está diseñado para proporcionar una estructura modular, permitiendo el despliegue del backend de SecureHub en entornos Docker y Kubernetes, con configuraciones claras y listas para producción.
