package itba.edu.ar.TP1;

import itba.edu.ar.TP1.models.Particle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainInputFiles {

    static final int PARTICLE_RADIUS_INDEX = 0;
    static final int PARTICLE_X_INDEX = 0;
    static final int PARTICLE_Y_INDEX = 1;

    public static void main(String[] args) {

        double interactionRadius = 0.0;
        int M = 0;
        String staticFilePath = null;
        String dynamicFilePath = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/java/itba/edu/ar/TP1/config.json"))
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

            CellIndexMethod cellIndexMethod = new CellIndexMethod(L, N, interactionRadius, M, particles);
            cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));

            CellIndexMethodPeriodic cellIndexMethodPeriodic = new CellIndexMethodPeriodic(L, N, INTERACTION_RADIUS, M, particles);
            cellIndexMethodPeriodic.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));

        } catch (IOException e) {
            System.out.println("Error opening file " + staticFilePath);
            e.printStackTrace();
        }

    }
}