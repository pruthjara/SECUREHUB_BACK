#!/bin/bash

# Configuración
REPO_BACKEND="strast-upm/securehub_backend"
TAG="latest"
USERNAME="pruthjara"
TOKEN="-"
COMPOSE_FILE_PATH="$1"

# Función para ejecutar comandos y manejar errores
run_command() {
    local command="$1"
    local error_message="$2"
    
    if ! eval "$command"; then
        echo "Error: $error_message"
        exit 1
    fi
}

# Función para eliminar imágenes locales antiguas del backend
remove_old_local_backend_image() {
    local images_to_remove=(
        "securehub_back-backend:latest"
        "ghcr.io/strast-upm/securehub_back-backend:latest"
    )

    for image in "${images_to_remove[@]}"; do
        echo "Eliminando imagen local $image..."
        run_command "docker rmi -f $image" "Error al eliminar la imagen local $image"
    done
}

# Función para obtener el ID de la imagen recién construida
get_image_id() {
    docker images -q "$1"
}

# Verificar que USERNAME y TOKEN estén definidos
if [[ -z "$USERNAME" || -z "$TOKEN" ]]; then
    echo "Error: Las variables de entorno 'GITHUB_USERNAME' y 'GITHUB_TOKEN' deben estar definidas."
    exit 1
fi

# Ejecutar sbt stage en la carpeta backend
BACKEND_DIR="$(dirname "$(realpath "$0")")/backend"
echo "Ejecutando 'sbt stage' en $BACKEND_DIR..."
run_command "cd $BACKEND_DIR && sbt stage" "Error al ejecutar 'sbt stage' en la carpeta backend"

# Eliminar las imágenes locales antes de reconstruir
remove_old_local_backend_image

# Construir la imagen con Docker Compose sin caché
echo "Construyendo la imagen Docker del backend con Docker Compose desde $COMPOSE_FILE_PATH..."
run_command "docker-compose -f $COMPOSE_FILE_PATH build --no-cache securehub_back-backend" "Error al construir la imagen del backend con Docker Compose"

# Obtener el ID de la imagen recién construida
backend_image_id=$(get_image_id "securehub_back-backend")

if [[ -z "$backend_image_id" ]]; then
    echo "Error: No se pudo encontrar la imagen del backend después de la construcción."
    exit 1
fi

# Etiquetar la imagen para GitHub Container Registry
echo "Etiquetando la imagen del backend..."
run_command "docker tag $backend_image_id ghcr.io/$REPO_BACKEND:$TAG" "Error al etiquetar la imagen del backend"

# Autenticarse en GitHub Container Registry
echo "Autenticándose en GitHub Container Registry..."
run_command "echo $TOKEN | docker login ghcr.io -u $USERNAME --password-stdin" "Error al autenticar en GitHub Container Registry"

# Subir la imagen al registro
echo "Subiendo la imagen del backend al registro de GitHub..."
run_command "docker push ghcr.io/$REPO_BACKEND:$TAG" "Error al subir la imagen del backend al registro de GitHub"

echo "¡Imagen del backend subida exitosamente!"
