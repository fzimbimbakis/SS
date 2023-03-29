package density_analysis;

import models.Particle;
import utils.CellIndexMethod;
import utils.CellIndexMethodPeriodic;
import utils.ParticlesUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainWithThreads {
    private static final String VA_BASE_PATH = "./TP2/src/main/resources/density_analysis/vaVsTime_N_";
    private static final String ANIMATION_BASE_PATH = "./TP2/src/main/resources/density_analysis/animation_N_";

    public static class DensityAnalysis implements Runnable {

        private final Set<Particle> particles;
        private final int N;
        private final CellIndexMethod cellIndexMethod;

        private static final Double L = 20.0;
        private static final Double PARTICLES_RAD = 0.25;
        private static final Double SPEED = 0.03;
        private static final Integer TIMES = 1000;
        private static final Double NOISE = 3.0;
        private static final Double INTERACTION_RAD = 1.0;
        private static final int M = (int) (L / (INTERACTION_RAD + PARTICLES_RAD * 2));
        private static final String INITIAL_DYNAMIC_PATH = "./TP2/src/main/resources/density_analysis/dynamic_";
        private static final String INITIAL_STATIC_PATH = "./TP2/src/main/resources/density_analysis/static_";
        private static final String INITIAL_DYNAMIC_XYZ_PATH = "./TP2/src/main/resources/density_analysis/dynamic_xyz_";

        public DensityAnalysis(int n) {

            this.particles = ParticlesUtils.generateRandomParticleFiles(INITIAL_DYNAMIC_PATH + n + ".txt", INITIAL_STATIC_PATH + n + ".txt", INITIAL_DYNAMIC_XYZ_PATH + n + ".xyz", n, L, PARTICLES_RAD, SPEED);

            this.cellIndexMethod = new CellIndexMethodPeriodic(L, n, INTERACTION_RAD, M, particles);
            this.N = n;
        }

        @Override
        public void run() {
            try {

                String vaFilePath = VA_BASE_PATH + this.N + ".txt";
                String animationFilePath = ANIMATION_BASE_PATH + this.N + ".xyz";

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
                        p.updateAngle(NOISE);
                    });
                    cellIndexMethod.clearNeighbours();
                    if (i % 100 == 0) System.out.println(this.N + "-" + i);
                }

                myWriter.close();
                myWriterXyz.close();

                System.out.println("Finished with N= " + this.N);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {


        final ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int n = 3400; n <= 4000; n += 200) {
            pool.execute(new DensityAnalysis(n));
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
