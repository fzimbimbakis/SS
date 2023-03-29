import numpy as np
from matplotlib import pyplot as plt


def read_vas_file(name):
    list_x = []
    list_y = []
    with open(name) as archivo:
        i = 1
        for linea in archivo:
            if i > 900:
                x_aux, y_aux = linea.split()
                list_x.append(int(x_aux))
                list_y.append(float(y_aux))
            i += 1
    return list_x, list_y


if __name__ == '__main__':
    plt.title('Va en funcion de la densidad')
    plt.xlabel('Densidad')
    plt.ylabel('Va')
    label = False
    for j in range(200, 4200, 200):
        time, va = read_vas_file("../../resources/density_analysis/vaVsTime_N_" + str(j) + ".txt")
        plt.errorbar(j, np.mean(va), yerr=np.std(va), color='black', fmt="o")
    plt.savefig("../../resources/density_analysis/graphs/noiseResults.png")
