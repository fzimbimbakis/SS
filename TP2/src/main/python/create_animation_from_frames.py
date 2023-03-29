import os
import glob
import imageio
# Ruta a la carpeta que contiene las imágenes
animation_frames_folder = '../resources/graphs/animationImages'
frames_files_name_format = 'animation*.png'
animation_name = '../resources/graphs/animation.mp4'


def main():

    # Obtener la lista de archivos de imagen en la carpeta
    images = sorted(glob.glob(os.path.join(animation_frames_folder, frames_files_name_format)))

    # Crear la animación con las imágenes en la lista
    with imageio.get_writer(animation_name, fps=2) as writer:
        for image in images:
            # Leer la imagen
            img = imageio.imread(image)

            # Añadir la imagen al archivo de video
            writer.append_data(img)

    print('La animación se ha guardado correctamente en ' + animation_name)


if __name__ == "__main__":
    main()
