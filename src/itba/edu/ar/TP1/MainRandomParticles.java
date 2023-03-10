package itba.edu.ar.TP1;

import itba.edu.ar.TP1.models.Particle;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MainRandomParticles {

    static final Double INTERACTION_RADIUS = 1.0;
    static final Double PARTICLE_RADIUS = 0.25;
    static final Double L = 20.0;
    static final Integer M = (int)( L / (INTERACTION_RADIUS + PARTICLE_RADIUS * 2)); // L / (interactionRadius + maxRadius * 2) > M (se tiene que cumplir)
    static final Integer N = 100;


    public static void main(String[] args) {

        try {
            File myObj = new File("resources/TP1/Dynamic.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Set<Particle> particles = new HashSet<>();
        try {
            FileWriter myWriter = new FileWriter("resources/TP1/Dynamic.txt");
            myWriter.write("0\n");
            double x, y;
            for (int i = 0; i < N; i++) {
                x = Math.random() * L;
                y = Math.random() * L;
                myWriter.write(x + " " + y + "\n");
                particles.add(new Particle(i, x, y, PARTICLE_RADIUS));
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("resources/TP1/optimusM.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        CellIndexMethod cellIndexMethod;
        long startTime;

        try {
            FileWriter myWriter = new FileWriter("resources/TP1/optimusM.txt");
            for (int i = M; i > 0 ; i--) {
                startTime = System.currentTimeMillis();
                cellIndexMethod = new CellIndexMethod(L, N, INTERACTION_RADIUS, i, particles);
                cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));
                myWriter.write(i + " " + (System.currentTimeMillis() - startTime) + "\n");
                cellIndexMethod.clearNeighbours();
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
