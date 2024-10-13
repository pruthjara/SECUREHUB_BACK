# Utiliza una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo de compilación y las dependencias
COPY ./backend/target/universal/stage ./stage

# Exponer el puerto 9000 (predeterminado de Play)
EXPOSE 9000

# Comando para iniciar la aplicación
CMD ["./stage/bin/backend"]

