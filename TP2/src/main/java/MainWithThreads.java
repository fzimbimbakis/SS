import models.Particle;
import utils.CellIndexMethod;
import utils.CellIndexMethodPeriodic;
import utils.JsonConfigReader;
import utils.ParticlesUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainWithThreads {
    private static final String JSON_CONFIG_PATH = "./TP2/src/main/java/config.json";
    private static final String VA_BASE_PATH = "./TP2/src/main/resources/noiseAnalysis/vaVsNoise=";
    private static final String ANIMATION_BASE_PATH = "./TP2/src/main/resources/noiseAnalysis/animation(noise=";

    public static class C1 implements Runnable{

        int times;
        Set<Particle> particles;
        int N;
        Double speed;
        Double L;
        double noise;
        CellIndexMethod cellIndexMethod;

        public C1(Set<Particle> original, int times, int n, Double speed, Double l, double noise, double IR, int m) {
            noise = Math.round(noise * 10.0) / 10.0;
            particles = new HashSet<>();
            original.forEach(p -> particles.add(Particle.copyOf(p)));
            this.cellIndexMethod = new CellIndexMethodPeriodic(
                    l,
                    n,
                    IR,
                    m,
                    particles
            );
            this.times = times;
            N = n;
            this.speed = speed;
            L = l;
            this.noise = noise;
        }

        @Override
        public void run() {
            try {

                String vaFilePath = VA_BASE_PATH + noise + ".txt";
                String animationFilePath = ANIMATION_BASE_PATH + noise + ").xyz";
                ParticlesUtils.createFile(vaFilePath);
                ParticlesUtils.createFile(animationFilePath);
                ParticlesUtils.writeParticlesToFileXyz(animationFilePath, 0, particles, N, L);
                FileWriter myWriter = new FileWriter(vaFilePath, true);
                FileWriter myWriterXyz = new FileWriter(animationFilePath, true);
                for (int i = 1; i < times; i++) {
                    cellIndexMethod.run();
                    ParticlesUtils.writeVaToFile(myWriter, i, particles, N, speed);
                    ParticlesUtils.writeParticlesToFileXyz(myWriterXyz, i, particles, N, L);
                    particles.forEach(p -> {
                        p.moveParticle(L);
                        p.updateAngle(noise);
                    });
                    cellIndexMethod.clearNeighbours();
                    if(i % 100 == 0)
                        System.out.println(noise + "-" + i);
                }
                myWriter.close();
                myWriterXyz.close();
                System.out.println("Finished with noise= " + noise);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args){

        JsonConfigReader config = new JsonConfigReader(JSON_CONFIG_PATH);


        Set<Particle> originalParticles = ParticlesUtils.generateRandomParticleFiles(
                config.getDynamicFilePath(),
                config.getStaticFilePath(),
                config.getDynamicFilePathXyz(),
                config.getN(),
                config.getL(),
                config.getParticleRadius(),
                config.getSpeed()
        );

        final ExecutorService pool = Executors.newFixedThreadPool(2);
//        for (double j = 2.0; j < 2.1; j += 0.2) {
//            pool.execute(new C1(originalParticles, config.getTimes(), config.getN(), config.getSpeed(), config.getL(), j, config.getInteractionRadius(), config.getM()));
//        }
        pool.execute(new C1(originalParticles, config.getTimes(), config.getN(), config.getSpeed(), config.getL(), 3.6, config.getInteractionRadius(), config.getM()));
        pool.execute(new C1(originalParticles, config.getTimes(), config.getN(), config.getSpeed(), config.getL(), 0.6, config.getInteractionRadius(), config.getM()));


        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.MINUTES)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
