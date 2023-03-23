import models.Particle;
import utils.CellIndexMethod;
import utils.CellIndexMethodPeriodic;
import utils.JsonConfigReader;
import utils.ParticlesUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class StatisticsMain {
    private static final String JSON_CONFIG_PATH = "./TP2/src/main/java/config.json";
    private static final String VA_BASE_PATH = "./TP2/src/main/resources/noiseAnalysis/vaVsNoise=";

    public static void main(String[] args) {

        JsonConfigReader config = new JsonConfigReader(JSON_CONFIG_PATH);


        Set<Particle> originalParticles = ParticlesUtils.generateRandomParticleFiles(
                config.getDynamicFilePath(),
                config.getStaticFilePath(),
                config.getN(),
                config.getL(),
                config.getParticleRadius(),
                config.getSpeed()
        );

        final Set<Particle> particles = new HashSet<>();
        originalParticles.forEach(particle -> particles.add(Particle.copyOf(particle)));

        CellIndexMethod cellIndexMethod = new CellIndexMethodPeriodic(
                config.getL(),
                config.getN(),
                config.getInteractionRadius(),
                config.getM(),
                particles
        );


        final Double length = config.getL();

        String vaFilePath;

        for (double j = 0.0; j < 5.1; j += 0.2) {

            j = Math.round(j * 10.0) / 10.0;
            if (j != 0.0){
                particles.clear();
                originalParticles.forEach(particle -> particles.add(Particle.copyOf(particle)));
                cellIndexMethod.setParticles(particles);
            }


            vaFilePath = VA_BASE_PATH + j + ".txt";
            final double noise = j;
            ParticlesUtils.createFile(vaFilePath);
            //System.out.print("Starting");
            for (int i = 1; i < config.getTimes(); i++) {
                cellIndexMethod.run();
                ParticlesUtils.writeVaToFile(vaFilePath, i, particles, config.getN(), config.getSpeed());
                particles.forEach(p -> {
                    p.moveParticle(length);
                    p.updateAngle(noise);
                });
                cellIndexMethod.clearNeighbours();
                //System.out.print(".");
            }
            System.out.println("Finished with noise= " + j);
        }

    }
}