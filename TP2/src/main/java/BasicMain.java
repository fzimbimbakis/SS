import models.Particle;
import utils.CellIndexMethod;
import utils.JsonConfigReader;
import utils.ParticlesUtils;

import java.util.Set;

public class BasicMain {

    private static final String JSON_CONFIG_PATH = "./TP2/src/main/java/config.json";

    public static void main(String[] args) {

        JsonConfigReader config = new JsonConfigReader(JSON_CONFIG_PATH);


        Set<Particle> particles = ParticlesUtils.generateRandomParticleFiles(
                config.getDynamicFilePath(),
                config.getStaticFilePath(),
                config.getN(),
                config.getL(),
                config.getParticleRadius(),
                config.getSpeed()
        );


        CellIndexMethod cellIndexMethod = new CellIndexMethod(
                config.getL(),
                config.getN(),
                config.getInteractionRadius(),
                config.getM(),
                particles
        );

        final Double noise = config.getNoise();
        final Double length = config.getL();

        System.out.print("Starting");
        for (int i = 1; i < config.getTimes(); i++) {
            cellIndexMethod.run();
            particles.forEach(p -> {
                p.moveParticle(length);
                p.updateAngle(noise);
            });
            ParticlesUtils.writeParticlesToFile(config.getDynamicFilePath(), i, particles);
            cellIndexMethod.clearNeighbours();
            System.out.print(".");
        }
        System.out.println("Finished");

    }
}
