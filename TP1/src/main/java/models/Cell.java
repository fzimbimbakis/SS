package models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cell {

    private final Set<Particle> particles;


    public Cell() {
        this.particles = new HashSet<>();
    }

    public void analyze(Double interactionRadius, Map<Cell, Cell.NEIGHBOURS_POSITION> neighbours, Double L) {

        if (particles.isEmpty())
            return;

        neighbours.keySet().forEach(key -> analyzeCell(neighbours.get(key), key, interactionRadius, L));

        analyzeThisCell(interactionRadius);
    }

    private void analyzeCell(Cell.NEIGHBOURS_POSITION position, Cell cell, Double interactionRadius, Double L) {
        if (cell != null) {
            cell.getParticles().forEach(neighbour -> particles.forEach(particle -> {
                if (particle.isNeighbour(mapX(neighbour.getX(), position, L), mapY(neighbour.getY(), position, L), neighbour.getRadius(), interactionRadius)) {
                    particle.addNeighbour(neighbour);
                    neighbour.addNeighbour(particle);
                }
            }));
        }
    }

    private Double mapX(Double x, Cell.NEIGHBOURS_POSITION position, Double L) {
        switch (position) {
            case GHOST_BOTTOM_RIGHT:
            case GHOST_RIGHT:
            case GHOST_TOP_RIGHT:
                return x + L;
            default:
                return x;
        }
    }

    private Double mapY(Double y, Cell.NEIGHBOURS_POSITION position, Double L) {
        switch (position) {
            case GHOST_TOP:
            case GHOST_TOP_RIGHT:
                return y + L;
            case GHOST_BOTTOM:
            case GHOST_BOTTOM_RIGHT:
                return y - L;
            default:
                return y;
        }
    }

    protected void analyzeThisCell(Double interactionRadius) {
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

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void removeParticle(Particle particle) {
        particles.remove(particle);
    }

    public enum NEIGHBOURS_POSITION {
        TOP,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        GHOST_TOP,
        GHOST_TOP_RIGHT,
        GHOST_RIGHT,
        GHOST_BOTTOM_RIGHT,
        GHOST_BOTTOM
    }
}
