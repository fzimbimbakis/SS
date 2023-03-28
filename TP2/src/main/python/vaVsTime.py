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
    for j in [i / 10 for i in range(0, 51, 2)]:
        time, va = read_vas_file("../resources/noiseAnalysis/vaVsNoise="+ str(j)+ ".txt")
        fig, ax = plt.subplots()
        ax.plot(time, va)
        plt.title('Va vs iterations with noise = ' + str(j))
        plt.xlabel('Iterations')
        plt.ylabel('Va')
        ax.set_ylim([0.0, 1.1])
        plt.savefig("../resources/graphs/vaVsNoise="+str(j)+".png")
        plt.close()