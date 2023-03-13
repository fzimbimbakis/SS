package TP1;

import TP1.models.Particle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainComparisonBasicMethod {

    static final int PARTICLE_RADIUS_INDEX = 0;
    static final int PARTICLE_X_INDEX = 0;
    static final int PARTICLE_Y_INDEX = 1;

    public static void main(String[] args) {

        Double interactionRadius = null;
        int M = 0;
        String staticFilePath = null;
        String dynamicFilePath = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/java/TP1/config.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;

            interactionRadius = Double.parseDouble(jsonObject.get("interactionRadius").toString());
            M = Integer.parseInt(jsonObject.get("M").toString());
            staticFilePath = jsonObject.get("staticFile").toString();
            dynamicFilePath = jsonObject.get("dynamicFile").toString();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        if (staticFilePath == null || dynamicFilePath == null)
            throw new IllegalArgumentException("Dynamic or static file path not provided");

        double L;
        int N;
//        Double time;
        Set<Particle> particles = new HashSet<>();
        int id = 0;

        try {
            BufferedReader lectorS = new BufferedReader(new FileReader(staticFilePath));
            BufferedReader lectorD = new BufferedReader(new FileReader(dynamicFilePath));

            String staticLine = lectorS.readLine();
            if (staticLine == null)
                throw new IllegalArgumentException("Wrong static file format");
            N = Integer.parseInt(staticLine);

            staticLine = lectorS.readLine();
            if (staticLine == null)
                throw new IllegalArgumentException("Wrong static file format");
            L = Double.parseDouble(staticLine);

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

            final Double iR = interactionRadius;
            long startTime = System.currentTimeMillis();
            particles.forEach(p1 -> {
                particles.forEach(p2 -> {
                    if(!p1.getId().equals(p2.getId()) && p1.isNeighbour(p2, iR)){
                        p1.addNeighbour(p2);
                    }
                });
            });
            System.out.println("Fuerza bruta: " + (System.currentTimeMillis() - startTime));
            particles.forEach(particle -> System.out.print(particle.neighboursToString()));

            particles.forEach(Particle::clearNeighbours);

            startTime = System.currentTimeMillis();
            CellIndexMethod cellIndexMethod = new CellIndexMethod(L, N, interactionRadius, M, particles);
            System.out.println("Cell index method: " + (System.currentTimeMillis() - startTime));
            cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));

            CellIndexMethodPeriodic cellIndexMethodPeriodic = new CellIndexMethodPeriodic(L, N, interactionRadius, M, particles);
            cellIndexMethodPeriodic.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));

        } catch (IOException e) {
            System.out.println("Error opening file " + staticFilePath);
            e.printStackTrace();
        }

    }
}
