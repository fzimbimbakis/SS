package itba.edu.ar.TP1;

import itba.edu.ar.TP1.models.Particle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    static final int PARTICLE_RADIUS_INDEX = 0;
    static final int PARTICLE_X_INDEX = 0;
    static final int PARTICLE_Y_INDEX = 1;
    static final Double INTERACTION_RADIUS = 1.0;
    static final Integer M = 58;

    public static void main(String[] args) {

        String staticFilePath = args[0];
        String dynamicFilePath = args[1];

        if (staticFilePath == null || dynamicFilePath == null)
            throw new IllegalArgumentException("Dynamic or static file path not provided");

        Double L;
        Integer N;
//        Double time;
        Set<Particle> particles = new HashSet<>();
        Integer id = 0;

        try {
            BufferedReader lectorS = new BufferedReader(new FileReader(staticFilePath));
            BufferedReader lectorD = new BufferedReader(new FileReader(dynamicFilePath));

            String staticLine = lectorS.readLine();
            if (staticLine == null)
                throw new IllegalArgumentException("Wrong static file format");
            N = Integer.valueOf(staticLine);

            staticLine = lectorS.readLine();
            if (staticLine == null)
                throw new IllegalArgumentException("Wrong static file format");
            L = Double.valueOf(staticLine);

            String dynamicLine = lectorD.readLine();
            if (dynamicLine == null)
                throw new IllegalArgumentException("Wrong dynamic file format");
//            time = Double.valueOf(dynamicLine);

            for (int i = 0; i < N; i++) {

                staticLine = lectorS.readLine();
                if (staticLine == null)
                    throw new IllegalArgumentException("Wrong static file format");

                dynamicLine = lectorD.readLine();
                if (dynamicLine == null)
                    throw new IllegalArgumentException("Wrong dynamic file format");

                String[] staticLinesSplit = staticLine.split(" ");
                String[] dynamicLinesSplit = dynamicLine.split(" ");

                particles.add(
                        new Particle(
                                id++,
                                Double.valueOf(dynamicLinesSplit[PARTICLE_X_INDEX]),
                                Double.valueOf(dynamicLinesSplit[PARTICLE_Y_INDEX]),
                                Double.valueOf(staticLinesSplit[PARTICLE_RADIUS_INDEX])
                        ));
            }


            lectorS.close();
            lectorD.close();

            CellIndexMethod cellIndexMethod = new CellIndexMethod(L, N, INTERACTION_RADIUS, M, particles);
            cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));

        } catch (IOException e) {
            System.out.println("Error opening file " + staticFilePath);
            e.printStackTrace();
        }

    }
}