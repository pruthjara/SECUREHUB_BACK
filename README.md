# SecureHub_Back Repository

Este repositorio contiene los archivos necesarios para el despliegue del **backend** de la aplicación SecureHub en entornos Docker y Kubernetes.

## Estructura de Archivos

- **backend/**  
  Carpeta que contiene el código fuente del backend de SecureHub, desarrollado en Scala utilizando Play Framework.

- **.gitignore**  
  Especifica los archivos y carpetas que deben ser ignorados por Git, como archivos temporales o configuraciones locales.

- **Dockerfile**  
  Define la configuración para construir la imagen Docker del backend. Ejecuta `sbt stage` para preparar la aplicación para su ejecución en producción.

- **push_back_image.py**  
  Script en Python que automatiza la construcción y subida de la imagen Docker del backend a un registro de contenedores.

- **LICENSE**  
  Archivo que contiene la licencia del proyecto.

- **README.md**  
  Este documento con información sobre el despliegue del backend de SecureHub.

## Instrucciones para el Despliegue del Backend

1. **Construcción de la Imagen Docker**  
   Utiliza el `Dockerfile` para construir la imagen del backend. Este archivo está preparado para compilar y optimizar la aplicación con `sbt stage`.

2. **Automatizar la Construcción y Subida de la Imagen**  
   Ejecuta el script `push_back_image.py` para construir y subir automáticamente la imagen del backend a tu registro de contenedores.

---

Este repositorio está diseñado con una estructura modular y orientada a producción, facilitando el despliegue del backend de SecureHub en entornos Dockerizados y orquestados con Kubernetes.

