from matplotlib import pyplot as plt

#poner la particula y sus respectivos vecinos
particle = 94
neighbours = [52, 10, 58, 91]
#nombre del archivo donde se encuentran las coordenadas de todas las particulas
dynamic_file ="../resources/Dynamic.txt"
times_file = "../resources/optimusM.txt"

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

def read_times_file(name):
    m_list = []
    time_list = []
    with open(name) as archivo:
        for linea in archivo:
            x_aux, y_aux = linea.split()
            m_list.append(int(x_aux))
            time_list.append(int(y_aux))
    return m_list, time_list



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
    plt.title('Representacion de las particulas')
    plt.savefig("../resources/particles.png")
    plt.clf()


    x, y = read_times_file(times_file)
    plt.title('M optimo')
    plt.ylabel('Tiempo (ns)')
    plt.xlabel('M (numero de celdas) ')
    plt.scatter(x, y)
    plt.plot(x, y)
    plt.savefig("../resources/times.png")

    plt.clf()

