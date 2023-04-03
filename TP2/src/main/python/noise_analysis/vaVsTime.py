from matplotlib import pyplot as plt

def read_vas_file(name):
    list_x = []
    list_y = []
    with open(name) as archivo:
        for linea in archivo:
            x_aux, y_aux= linea.split()
            list_x.append(int(x_aux))
            list_y.append(float(y_aux))
    return list_x , list_y


if __name__ == '__main__':
    plt.xlabel('Tiempo')
    plt.ylabel('Polarización')
    plt.ylim([0.0, 1.1])

    time, va = read_vas_file("../../resources/noise_analysis/vaVsTime(noise=0.2).txt")
    plt.plot(time, va, "g", label="η = 0.6")
    time, va = read_vas_file("../../resources/noise_analysis/vaVsTime(noise=2.0).txt")
    plt.plot(time, va, "r", label="η = 2.4")
    time, va = read_vas_file("../../resources/noise_analysis/vaVsTime(noise=3.5).txt")
    plt.plot(time, va, "b", label="η = 3.6")
    time, va = read_vas_file("../../resources/noise_analysis/vaVsTime(noise=5.0).txt")
    plt.plot(time, va, "k", label="η = 5.0")

    plt.legend(loc='upper right')
    plt.savefig("../../resources/noise_analysis/graphs/vaVsTime.png")