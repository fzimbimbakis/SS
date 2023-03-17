import models.Particle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import java.util.HashSet;
import java.util.Set;

public class MainRandomParticles {


    public static void main(String[] args) {

        Double L = null;
        Integer N = null ;
        Double particleRadius = null;
        Double interactionRadius = null;
        Integer M = null;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./TP1/src/main/java/config.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;

            L = Double.parseDouble(jsonObject.get("L").toString());
            N = Integer.parseInt(jsonObject.get("N").toString());
            particleRadius = Double.parseDouble(jsonObject.get("particleRadius").toString());
            interactionRadius = Double.parseDouble(jsonObject.get("interactionRadius").toString());
            M = (int)( L / (interactionRadius + particleRadius * 2));


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        try {
            File myObj = new File("./TP1/src/main/resources/Dynamic.txt");
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
            FileWriter myWriter = new FileWriter("./TP1/src/main/resources/Dynamic.txt");
            myWriter.write("0\n");
            double x, y;
            for (int i = 0; i < N; i++) {
                x = Math.random() * L;
                y = Math.random() * L;
                myWriter.write(x + " " + y + "\n");
                particles.add(new Particle(i, x, y, particleRadius));
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("./TP1/src/main/resources/Static.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("./TP1/src/main/resources/Static.txt");
            myWriter.write(N + "\n");
            myWriter.write(L + "\n");
            for (int i = 0; i < N; i++) {
                myWriter.write(particleRadius + "\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            File myObj = new File("./TP1/src/main/resources/optimusM.txt");
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
        cellIndexMethod = new CellIndexMethod(L, N, interactionRadius, M, particles);
        cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));
        cellIndexMethod.clearNeighbours();
        try {
            FileWriter myWriter = new FileWriter("./TP1/src/main/resources/optimusM.txt");
            for (int i = 1; i <= M ; i++) {
                startTime = System.nanoTime();
                cellIndexMethod = new CellIndexMethod(L, N, interactionRadius, i, particles);
                myWriter.write(i + " " + (System.nanoTime() - startTime) + "\n");
//                cellIndexMethod.getParticles().forEach(particle -> System.out.print(particle.neighboursToString()));
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
