import subprocess
import sys
import os

# Configuración
REPO_BACKEND = "strast-upm/securehub_backend"
TAG = "latest"
USERNAME = "pruthjara"
TOKEN = "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"
COMPOSE_FILE_PATH = sys.argv[1] if len(sys.argv) > 1 else "docker-compose.yml"

def run_command(command, error_message):
    """Ejecuta un comando y maneja errores."""
    try:
        subprocess.run(command, shell=True, check=True)
    except subprocess.CalledProcessError as e:
        print(f"Error: {error_message}\nDetalles del error: {e}")
        sys.exit(1)

def remove_old_local_backend_image():
    """Elimina imágenes antiguas del backend."""
    images_to_remove = [
        "securehub-backend:latest",
        "ghcr.io/strast-upm/securehub_backend:latest"
    ]
    for image in images_to_remove:
        print(f"Eliminando imagen local {image}...")
        run_command(f"docker rmi -f {image}", f"Error al eliminar la imagen local {image}")

def get_image_id(image_name):
    """Obtiene el ID de una imagen especificada."""
    result = subprocess.run(f"docker images -q {image_name}", shell=True, check=True, stdout=subprocess.PIPE, universal_newlines=True)
    return result.stdout.strip()

def main():
    # Verificar que USERNAME y TOKEN estén definidos
    if not USERNAME or not TOKEN:
        print("Error: Las variables de entorno 'USERNAME' y 'TOKEN' deben estar definidas.")
        sys.exit(1)

    # Ejecutar sbt stage en la carpeta backend
    backend_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "backend")
    print(f"Ejecutando 'sbt stage' en {backend_dir}...")
    run_command(f"cd {backend_dir} && sbt stage", "Error al ejecutar 'sbt stage' en la carpeta backend")

    # Eliminar la imagen local del backend antes de reconstruir
    remove_old_local_backend_image()

    # Construir todas las imágenes definidas en Docker Compose
    print(f"Construyendo las imágenes Docker con Docker Compose desde {COMPOSE_FILE_PATH}...")
    run_command(f"docker-compose -f {COMPOSE_FILE_PATH} build --no-cache", "Error al construir las imágenes con Docker Compose")

    # Obtener el ID de la imagen del backend
    backend_image_id = get_image_id("securehub-backend")

    if not backend_image_id:
        print("Error: No se pudo encontrar la imagen del backend después de la construcción.")
        sys.exit(1)

    # Etiquetar la imagen del backend para GitHub Container Registry
    print("Etiquetando la imagen del backend...")
    run_command(f"docker tag {backend_image_id} ghcr.io/{REPO_BACKEND}:{TAG}", "Error al etiquetar la imagen del backend")

    # Autenticarse en GitHub Container Registry
    print("Autenticándose en GitHub Container Registry...")
    run_command(f"echo {TOKEN} | docker login ghcr.io -u {USERNAME} --password-stdin", "Error al autenticar en GitHub Container Registry")

    # Subir la imagen del backend al registro
    print("Subiendo la imagen del backend al registro de GitHub...")
    run_command(f"docker push ghcr.io/{REPO_BACKEND}:{TAG}", "Error al subir la imagen del backend al registro de GitHub")

    print("¡Imagen del backend subida exitosamente!")

if __name__ == "__main__":
    main()
