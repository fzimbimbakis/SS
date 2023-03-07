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
