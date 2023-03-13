from matplotlib import pyplot as plt
if __name__ == '__main__':
    metodos = ['Fuerza bruta', 'Cell Index Method']
    ventas = [1423, 25]


    # Create a bar graph with the x-axis and y-axis data
    plt.bar(metodos, ventas)
    for i in range(len(metodos)):
            plt.text(i-0.01,ventas[i]/2,ventas[i])

    # Add a title and labels to the graph
    plt.title('Comparación: Cell Index Method vs Fuerza Bruta')
    plt.ylabel('Tiempo (ms)')

    # Display the graph
    plt.savefig('resources/TP1/comparison.png')
    plt.clf()