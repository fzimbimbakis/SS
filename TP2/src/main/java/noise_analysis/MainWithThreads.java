package noise_analysis;

import models.Particle;
import utils.CellIndexMethod;
import utils.CellIndexMethodPeriodic;
import utils.ParticlesUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainWithThreads {
    private static final Double L = 10.0;
    private static final Integer N = 400;
    private static final Integer TIMES = 1000;


    private static final Double PARTICLES_RAD = 0.25;
    private static final Double SPEED = 0.03;
    private static final Double INTERACTION_RAD = 1.0;
    private static final int M = (int) (L / (INTERACTION_RAD + PARTICLES_RAD * 2));
    private static final String DYNAMIC_PATH = "./TP2/src/main/resources/noise_analysis/dynamic.txt";
    private static final String STATIC_PATH = "./TP2/src/main/resources/noise_analysis/static.txt";
    private static final String ANIMATION_PATH = "./TP2/src/main/resources/noise_analysis/animation.xyz";
    private static final String VA_BASE_PATH = "./TP2/src/main/resources/noise_analysis/vaVsTime(noise=";
    private static final String ANIMATION_BASE_PATH = "./TP2/src/main/resources/noise_analysis/animation(noise=";

    public static class C1 implements Runnable{

        private final Set<Particle> particles;
        private final double noise;
        private final CellIndexMethod cellIndexMethod;

        public C1(Set<Particle> original, double noise) {
            noise = Math.round(noise * 10.0) / 10.0;
            particles = new HashSet<>();
            original.forEach(p -> particles.add(Particle.copyOf(p)));
            this.cellIndexMethod = new CellIndexMethodPeriodic(L, N, INTERACTION_RAD, M, particles);
            this.noise = noise;
        }

        @Override
        public void run() {
            try {

                String vaFilePath = VA_BASE_PATH + noise + ").txt";
                String animationFilePath = ANIMATION_BASE_PATH + noise + ").xyz";
                ParticlesUtils.createFile(vaFilePath);
                ParticlesUtils.createFile(animationFilePath);
                ParticlesUtils.writeParticlesToFileXyz(animationFilePath, 0, particles, N, L);
                FileWriter myWriter = new FileWriter(vaFilePath, true);
                FileWriter myWriterXyz = new FileWriter(animationFilePath, true);
                for (int i = 1; i < TIMES; i++) {
                    cellIndexMethod.run();
                    ParticlesUtils.writeVaToFile(myWriter, i, particles, N, SPEED);
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


        Set<Particle> originalParticles = ParticlesUtils.generateRandomParticleFiles(
                DYNAMIC_PATH ,
                STATIC_PATH,
                ANIMATION_PATH,
                N,
                L,
                PARTICLES_RAD,
                SPEED
        );

        final ExecutorService pool = Executors.newFixedThreadPool(8);
        for (double j = 0.0; j < 1.5; j += 0.2) {
            pool.execute(new C1(originalParticles, j));
        }



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
