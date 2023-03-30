from matplotlib import pyplot as plt


def read_vas_file(name):
    list_x = []
    list_y = []
    with open(name) as archivo:
        for linea in archivo:
            x_aux, y_aux = linea.split()
            list_x.append(int(x_aux))
            list_y.append(float(y_aux))
    return list_x, list_y


va_vs_time_file_path = "../../resources/density_analysis/"
graph_path = "../../resources/density_analysis/graphs"

if __name__ == '__main__':

    for j in range(100, 1100, 200):
        time, va = read_vas_file(va_vs_time_file_path + "vaVsTime(N=" + str(j) + ").txt")
        fig, ax = plt.subplots()
        ax.plot(time, va)
        plt.title('N = ' + str(j))
        plt.xlabel('Iteraciones')
        plt.ylabel('Va')
        ax.set_ylim([0.0, 1.1])
        plt.savefig(graph_path + "/vaVsTime(N=" + str(j) + ").png")
        plt.close()
