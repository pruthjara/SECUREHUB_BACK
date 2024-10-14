# Etapa de construcción usando sbt para compilar la aplicación
FROM hseeberger/scala-sbt:8u312_1.5.5_3.1.0 as builder

# Establecer el directorio de trabajo en la etapa de construcción
WORKDIR /app

# Copiar los archivos de la aplicación al contenedor
COPY ./backend /app

# Ejecutar sbt stage para preparar la aplicación
RUN sbt stage

# Etapa final utilizando OpenJDK para la aplicación compilada
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar la aplicación empaquetada desde la etapa anterior
COPY --from=builder /app/target/universal/stage ./stage

# Exponer el puerto 9000 (predeterminado de Play Framework)
EXPOSE 9000

# Comando para iniciar la aplicación
CMD ["./stage/bin/backend"]


