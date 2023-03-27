from matplotlib import pyplot as plt
import numpy as np

def read_vas_file(name):
    list_x = []
    list_y = []
    with open(name) as archivo:
        i=1
        for linea in archivo:
            if i > 400:
                x_aux, y_aux= linea.split()
                list_x.append(int(x_aux))
                list_y.append(float(y_aux))
            i+=1
    return list_x , list_y




if __name__ == '__main__':
    plt.title('Va en funcion del ruido')
    plt.xlabel('Ruido')
    plt.ylabel('Va')
    label0 = False
    label1 = False
    label2 = False
    label3 = False
    label4 = False
    for x in range(4):
        print(x)
        for j in [i / 10 for i in range(2, 51, 2)]:
            time, va = read_vas_file("../resources/noiseAnalysis" + str(x) + "/vaVsNoise="+ str(j)+ ".txt")
            if x == 0:
                if label0:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="ob")
                else:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="ob", label="∂ = 0,08")
                    label0 = True
            if x == 1:
                if label1:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="ok")
                else:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="ok", label="∂ = 0,16")
                    label1= True
            if x == 2:
                if label2:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="og")
                else:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="og", label="∂ =0,4")
                    label2= True
            if x == 3:
                if label3:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="or")
                else:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="or", label="∂ = 0,8")
                    label3= True
            if x == 4:
                if label4:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="oy")
                else:
                    plt.errorbar(j, np.mean(va),yerr=np.std(va), fmt="oy", label="∂ = 1,6")
                    label4= True
    plt.legend()
    plt.show()