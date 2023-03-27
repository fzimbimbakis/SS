import json
import matplotlib.pyplot as plt


if __name__ == '__main__':
    # Abrir el archivo JSON
    with open('../java/config.json') as f:
        config = json.load(f)

    # Obtener información del archivo JSON
    interaction_radius = config['interactionRadius']
    particle_radius = config['particleRadius']
    L = config['L']
    N = config['N']
    speed = config['speed']
    n = config['n']
    times = config['times']
    static_file_path = config['staticFile']
    dynamic_file_path = config['dynamicFile']

    # Abrir el archivo en modo lectura
    with open("../resources/dynamic.txt", 'r') as f:
        vectors = []  # Lista para almacenar los datos de los vectores
        for _ in range(times):  # Repetir el proceso el número de veces indicado en el archivo JSON
            time = f.readline()  # Leer el tiempo
            vec_list = []  # Lista para almacenar los vectores en cada iteración
            for i in range(N):  # Iterar sobre las siguientes 100 líneas
                line = f.readline()
                data = line.split()
                data = [float(d) for d in data]
                vec = (data[0], data[1], data[2], data[3])  # Crear una tupla con los valores de x, y, Vx y Vy
                vec_list.append(vec)
            vectors.append(vec_list)

    # Creación del gráfico
    fig, ax = plt.subplots()

    vec_list = vectors[0]  # Obtener la lista de vectores correspondiente a la iteración actual
    ax.clear()
    ax.set_xlim([0, L])
    ax.set_ylim([0, L])
    ax.set_xlabel('Eje X')
    ax.set_ylabel('Eje Y')
    # ax.grid(which='both', color='gray', linestyle='--')
    ax.set_title('Particulas en movimiento con t=' + str(0))
    for vec in vec_list:
        x, y, Vx, Vy = vec
        ax.quiver(x, y, Vx, Vy)

    plt.savefig('initial_condition.png')