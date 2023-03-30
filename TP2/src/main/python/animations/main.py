from matplotlib import pyplot as plt

#nombre del archivo donde se encuentran las coordenadas de todas las particulas
dynamic_file ="../../resources/dynamic.txt"
times = 1000

def read_particles_file(name):
    list_x = []
    list_y = []
    i = 0
    with open(name) as archivo:
        for linea in archivo:
            if linea.split().__len__() == 1:
                list_x.insert(i, [])
                list_y.insert(i, [])
                i += 1
            else:
                x_aux, y_aux, vx, vy = linea.split()
                list_x[i-1].append(float(x_aux))
                list_y[i-1].append(float(y_aux))
    return list_x , list_y




if __name__ == '__main__':
    particles_x, particles_y = read_particles_file(dynamic_file)
    fig, ax = plt.subplots()
    ax.set_box_aspect(1)
    for i in range(times):
        plt.cla()
        ax.set_xlim([0, 20])
        ax.set_ylim([0, 20])
        ax.grid(which='both', color='gray', linestyle='--')
        ax.scatter(particles_x[i], particles_y[i])
        plt.title('Iteracion ' + str(i))
        plt.pause(0.02)





