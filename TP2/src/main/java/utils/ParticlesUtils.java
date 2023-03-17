package utils;

import models.Particle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParticlesUtils {

        public static Set<Particle> generateRandomParticleFiles(String dynamicPath, String staticPath, Integer N, Double L, Double particleRadius, Double speed){
                Set<Particle> particles = new HashSet<>();

                ///  Write dynamic file
                ///  time
                ///  x y Vx Vy for each particle

                try {
                        File myObj = new File(dynamicPath);
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
                        FileWriter myWriter = new FileWriter(dynamicPath);
                        myWriter.write("0\n");
                        double x, y, angle;
                        for (int i = 0; i < N; i++) {
                                // Generate
                                x = Math.random() * L;
                                y = Math.random() * L;
                                angle = Math.random() * 2 * Math.PI;
                                particles.add(new Particle(i, x, y, particleRadius, angle, speed));
                                // Write x y Vx Vy
                                myWriter.write(x + " " + y + " " + Math.cos(angle)*speed + " " + Math.sin(angle*speed) + "\n");
                        }

                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                        throw new RuntimeException("Error writing random particles to file (" + dynamicPath + ") in RandomParticlesGenerator.generate.");
                }


                /// Write static file
                /// N
                /// L
                /// particleRadius for each particle

                try {
                        File myObj = new File(staticPath);
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

                return particles;
        }

        public static void writeParticlesToFile(String filePath, Integer time, Set<Particle> particles){
                try {
                        FileWriter myWriter = new FileWriter(filePath, true);
                        myWriter.write(time + "\n");
                        for (Particle particle: particles)
                                myWriter.write(particle.toString());
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                        throw new RuntimeException("Error writing particles to file (" + filePath + ") in ParticlesUtils.writeParticlesToFile.");
                }
        }

}
