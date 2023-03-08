from matplotlib import pyplot as plt

#poner la particula y sus respectivos vecinos
particle = 65
neighbours = [23]
#nombre del archivo donde se encuentran las coordenadas de todas las particulas
dynamic_file ="resources/TP1/Dynamic1.txt"

def read_particles_file(name):
    x_list = []
    y_list = []
    with open(name) as archivo:
        for linea in archivo:
            if linea.split().__len__() == 2:
                x_aux, y_aux = linea.split()
                x_list.append(float(x_aux))
                y_list.append(float(y_aux))
    return x_list, y_list

def read_radius_file(name):
    radius_list = []
    with open(name) as archivo:
        for linea in archivo:
            radius_aux = linea.split().__getitem__(0)
            radius_list.append(float(radius_aux))
    return radius_list



if __name__ == '__main__':
    x, y = read_particles_file(dynamic_file)
    for i in range(x.__len__()):
        highlight = False
        if particle == i:
            highlight = True
            plt.plot(x[i], y[i], 'ro')

            #plt.Circle((x[i], y[i]), 3.7, fill=False)
        for neighbour in neighbours:
            if i == neighbour:
                highlight = True
                plt.plot(x[i], y[i], 'ko')
        if not highlight:
            plt.plot(x[i], y[i], 'co')
        #plt.annotate(str(i), (x[i], y[i]))

    plt.show()

