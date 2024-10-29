# SecureHub_Back

Backend del proyecto **SecureHub** creado con [Play Framework](https://www.playframework.com/) y desarrollado en **Scala**. Este backend gestiona la lógica de negocio y la API para la comunicación con el frontend y otros servicios de la plataforma, incluyendo FreeIPA para autenticación y un sistema de control de acceso.

## Estructura del Proyecto

```bash
/backend/
├── app
│   ├── controllers         # Controladores del backend
│   ├── models              # Modelos de datos
│   ├── services            # Servicios de negocio
├── conf
│   ├── application.conf    # Archivo de configuración principal
│   ├── routes              # Definición de rutas de la API
├── project
│   ├── plugins.sbt         # Plugins de SBT
├── public
│   ├── assets              # Archivos públicos (si aplica)
├── test
│   ├── controllers         # Tests de controladores
├── build.sbt               # Configuración del proyecto en SBT
└── README.md               # Documentación del proyecto
```

## Requisitos Previos

- **Java 11** o superior
- **Scala 2.13** o superior
- **SBT (Scala Build Tool)**

## Configuración

El archivo `application.conf` ubicado en `conf/` es el principal archivo de configuración. Aquí se pueden ajustar parámetros como la conexión a bases de datos, las variables de entorno y las configuraciones de la API de FreeIPA. Asegúrate de configurar correctamente la variable de entorno `SECRET_KEY` para la autenticación segura.

## Instrucciones de Uso

### Clonar el Repositorio

```bash
git clone https://github.com/STRAST-UPM/SysControl_Back.git
cd SysControl_Back/backend
```
### Configurar Entorno de Desarrollo

Asegúrate de configurar la base de datos en `application.conf`. Aquí se conecta la base de datos SQL, usando preferentemente **PostgreSQL**.

### Ejecutar el Proyecto en Local

Para compilar y ejecutar el proyecto, utiliza el siguiente comando:

```bash
sbt run
```
El backend estará disponible en http://localhost:9000.

## Documentación de la API

La API sigue un formato REST y las rutas están definidas en el archivo `conf/routes`. A continuación, se muestran algunos de los endpoints principales:

- **GET** `/api/v1/users` - Obtener todos los usuarios
- **POST** `/api/v1/auth/login` - Autenticar un usuario con FreeIPA (mock en desarrollo)
- **GET** `/api/v1/access/logs` - Obtener los logs de acceso

## Pruebas

El backend incluye pruebas unitarias y de integración. Puedes ejecutar todas las pruebas utilizando:

```bash
sbt test
```
## Despliegue en Producción

El backend se puede desplegar en un clúster de Kubernetes. Asegúrate de utilizar las imágenes Docker correctas y configura las variables de entorno necesarias para la base de datos y FreeIPA.

