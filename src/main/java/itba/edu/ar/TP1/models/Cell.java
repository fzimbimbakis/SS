package itba.edu.ar.TP1.models;

import java.util.HashSet;
import java.util.Set;

public class Cell {

    private final Set<Particle> particles;

    public Cell() {
        this.particles = new HashSet<>();
    }

    public void analyze(Double interactionRadius, Set<Cell> neighbours) {
        neighbours.forEach(cell -> {
            if (cell != null)
                cell.getParticles().forEach(neighbour -> particles.forEach(particle -> {
                    if (particle.isNeighbour(neighbour, interactionRadius)) {
                        particle.addNeighbour(neighbour);
                        neighbour.addNeighbour(particle);
                    }
                }));
        });

        Object[] array = particles.toArray();
        Particle p1, p2;
        for (int i = 0; i < array.length; i++) {
            p1 = (Particle) array[i];
            for (int j = i + 1; j < array.length; j++) {
                p2 = (Particle) array[j];
                if (p1.isNeighbour(p2, interactionRadius)) {
                    p1.addNeighbour(p2);
                    p2.addNeighbour(p1);
                }
            }
        }
    }

    public Set<Particle> getParticles(){
        return particles;
    }

    public void addParticle(Particle particle){
        particles.add(particle);
    }

    public void removeParticle(Particle particle){
        particles.remove(particle);
    }

}
