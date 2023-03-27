package utils;

import models.Particle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParticlesUtils {

        public static File createFile(String path) {
                try {
                        File file = new File(path);
                        if (file.createNewFile()) {
                                System.out.println("File created: " + file.getName());
                        } else {
                                throw new IllegalStateException("Eliminar archivo " + path + " y correr devuelta.");
                        }
                        return file;
                } catch (IOException e) {
                        throw new RuntimeException("Error writing random particles to file (" + path + ") in ParticleUtils.createFile.");
                }
        }

        private static Set<Particle> generateParticles(Integer N, Double L, Double particleRadius, Double speed) {
                Set<Particle> particles = new HashSet<>();
                double x, y, angle;
                for (int i = 0; i < N; i++) {
                        // Generate
                        x = Math.random() * L;
                        y = Math.random() * L;
                        angle = (Math.random() * 2 -  1) * Math.PI;
                        particles.add(new Particle(i, x, y, particleRadius, angle, speed));
                }
                return particles;
        }

        public static Set<Particle> generateRandomParticleFiles(String dynamicPath, String staticPath, String animationPath, Integer N, Double L, Double particleRadius, Double speed) {


                //**    Write dynamic file      **********************************************
                //**              time
                //**              x y Vx Vy (for each particle)

                createFile(dynamicPath);

                Set<Particle> particles = generateParticles(N, L, particleRadius, speed);

                writeParticlesToFile(dynamicPath, 0, particles);

                createFile(animationPath);

                writeParticlesToFileXyz(animationPath, 0, particles, N, L);

                //****************************************************************************

                //**    Write static file      **********************************************
                //**               N
                //**               L
                //**               particleRadius for each particle

                createFile(staticPath);

                try {
                        FileWriter myWriter = new FileWriter(staticPath);
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

                //****************************************************************************

                return particles;
        }
        public static void writeVaToFile(String filePath, Integer time,  Set<Particle> particles, Integer N , Double speed) {
                Double Va = calculateVa(particles, N , speed);
                try {
                        FileWriter myWriter = new FileWriter(filePath, true);
                        myWriter.write(time + " " + Va + "\n");
                        myWriter.close();
                } catch (IOException e) {
                        throw new RuntimeException("Error writing Va to file (" + filePath + ") in ParticlesUtils.writeVaToFile.");
                }
        }

        public static void writeVaToFile(FileWriter myWriter, Integer time,  Set<Particle> particles, Integer N , Double speed) {
                Double Va = calculateVa(particles, N , speed);
                try {
                        myWriter.write(time + " " + Va + "\n");
                } catch (IOException e) {
                        throw new RuntimeException("Error writing Va to file in ParticlesUtils.writeVaToFile.");
                }
        }


        public static void writeParticlesToFile(String filePath, Integer time, Set<Particle> particles){
                try {
                        FileWriter myWriter = new FileWriter(filePath, true);
                        myWriter.write(time + "\n");
                        for (Particle particle: particles)
                                myWriter.write(particle.toString() + "\n");
                        myWriter.close();
                } catch (IOException e) {
                        throw new RuntimeException("Error writing particles to file (" + filePath + ") in ParticlesUtils.writeParticlesToFile.");
                }
        }



        public static void writeParticlesToFileXyz(String filePath, Integer time, Set<Particle> particles, Integer N, Double L){
                try {
                        FileWriter myWriter = new FileWriter(filePath, true);
                        myWriter.write((N + 2) + "\n" + time + "\n");
                        myWriter.write("0 0 0 0 0" + "\n");
                        myWriter.write(L + " " + L +" 0 0 0" + "\n");
                        for (Particle particle: particles)
                                myWriter.write(particle.toString() + " " + particle.normalizeAngle(255) + "\n");
                        myWriter.close();
                } catch (IOException e) {
                        throw new RuntimeException("Error writing particles to file (" + filePath + ") in ParticlesUtils.writeParticlesToFile.");
                }
        }

        private static Double calculateVa(Set<Particle> particles, Integer N , Double speed){
                double sumVx = particles.stream().mapToDouble(Particle::getVx).sum();
                double sumVy = particles.stream().mapToDouble(Particle::getVy).sum();
                double moduleV = Math.sqrt(Math.pow(sumVx,2) + Math.pow(sumVy,2));

                return moduleV/(N*speed);


        }

}
