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

    for j in [i / 10 for i in range(0, 81, 2)]:
        time, va = read_vas_file("../../resources/noise_analysis/vaVsTime(noise="+ str(j)+ ").txt")
        fig, ax = plt.subplots()
        ax.plot(time, va)
        plt.title('Ruido = ' + str(j))
        plt.xlabel('Iteraciones')
        plt.ylabel('Va')
        ax.set_ylim([0.0, 1.1])
        plt.savefig("../../resources/noise_analysis/graphs/vaVsTime(noise="+str(j)+").png")
        plt.close()