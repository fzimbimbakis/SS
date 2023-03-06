package itba.edu.ar.TP1.models;

import java.util.HashSet;
import java.util.Set;

public class Cell {

    private final Set<Particle> particles;
    private final Set<Cell> neighbours;
    private Integer x;
    private Integer y;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.particles = new HashSet<>();
        this.neighbours = new HashSet<>();
    }

    //// TODO ver como agregar los vecinos. Quedo en poner x e y
    public void analyze(Double interactionRadius, Cell[][] cells){
        neighbours.forEach(cell -> {
            cell.getParticles().forEach(neighbour -> {
                particles.forEach(particle -> {
                    if(particle.isNeighbour(neighbour, interactionRadius)){
                        particle.addNeighbour(neighbour);
                        neighbour.addNeighbour(particle);
                    }
                });
            });
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

    public void addParticles(Set<Particle> newParticles){
        newParticles.forEach(this::addParticle);
    }

    public void removeParticles(Set<Particle> toDelete){
        toDelete.forEach(this::removeParticle);
    }

    public void addNeighbour(Cell cell){
        neighbours.add(cell);
    }

    public void removeNeighbour(Cell cell){
        particles.remove(cell);
    }

    public void addNeighbours(Set<Cell> newCells){
        newCells.forEach(this::addNeighbour);
    }

    public void removeNeighbours(Set<Cell> toDelete) {
        toDelete.forEach(this::removeNeighbour);
    }

}
