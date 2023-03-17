import models.Particle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.CellIndexMethod;
import utils.ParticlesUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class BasicMain {

    public static void main(String[] args) {
        Double L = null;
        Integer N = null ;
        Double particleRadius = null;
        Double interactionRadius = null;
        Integer M = null;
        Double n = null;
        Double speed = null;
        String staticFilePath = null;
        String dynamicFilePath = null;


        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./TP2/src/main/java/config.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;

            L = Double.parseDouble(jsonObject.get("L").toString());
            N = Integer.parseInt(jsonObject.get("N").toString());
            n = Double.parseDouble(jsonObject.get("n").toString());
            speed = Double.parseDouble(jsonObject.get("speed").toString());
            particleRadius = Double.parseDouble(jsonObject.get("particleRadius").toString());
            interactionRadius = Double.parseDouble(jsonObject.get("interactionRadius").toString());
            staticFilePath = jsonObject.get("staticFile").toString();
            dynamicFilePath = jsonObject.get("dynamicFile").toString();
            M = (int)( L / (interactionRadius + particleRadius * 2));


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        Set<Particle> particles = ParticlesUtils.generateRandomParticleFiles(dynamicFilePath, staticFilePath, N, L, particleRadius, speed);


        CellIndexMethod cellIndexMethod = new CellIndexMethod(L, N, interactionRadius, M, particles);

        final Double noise = n;
        final Double length = L;
        for (int i = 1; i < 100; i++) {
            cellIndexMethod.run();
            particles.forEach( p -> {
                p.moveParticle(length);
                p.updateAngle(noise);
            });
            ParticlesUtils.writeParticlesToFile(dynamicFilePath, i, particles);
            cellIndexMethod.clearNeighbours();
        }

    }
}
