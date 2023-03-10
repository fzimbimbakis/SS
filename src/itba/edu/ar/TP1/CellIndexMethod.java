package itba.edu.ar.TP1;


import itba.edu.ar.TP1.models.Cell;
import itba.edu.ar.TP1.models.Particle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CellIndexMethod {

    private final Double L;
    private final Integer N;
    private final Double interactionRadius;
    protected final Integer M;
    private final Set<Particle> particles;
    protected final Cell[][] cells;

    protected Map<Cell.NEIGHBOURS_POSITION, Cell> getNeighbours(Integer row, Integer col) {
        Map<Cell.NEIGHBOURS_POSITION, Cell> neighbours = new HashMap<>();

        if (row < M - 1) {
            neighbours.put(Cell.NEIGHBOURS_POSITION.TOP, this.cells[col][row + 1]);
            if (col < M - 1) neighbours.put(Cell.NEIGHBOURS_POSITION.TOP_RIGHT, this.cells[col + 1][row + 1]);
        }

        if (col < M - 1) {
            neighbours.put(Cell.NEIGHBOURS_POSITION.RIGHT, this.cells[col + 1][row]);
            if (row > 0) neighbours.put(Cell.NEIGHBOURS_POSITION.BOTTOM_RIGHT, this.cells[col + 1][row - 1]);
        }

        return neighbours;
    }

    protected void fillCells(Set<Particle> particles) {
        particles.forEach(particle -> {
            Integer indexX = particle.getIndexX(L, M);
            Integer indexY = particle.getIndexY(L, M);

            if (cells[indexX][indexY] == null) {
                cells[indexX][indexY] = new Cell();
            }
            cells[indexX][indexY].addParticle(particle);
        });
    }

    private void checkArguments() {
        if (L == null || L <= 0)
            throw new IllegalArgumentException("Illegal L");

        if (interactionRadius == null || interactionRadius <= 0)
            throw new IllegalArgumentException("Illegal interaction Radius");

        if (N == null || N <= 0)
            throw new IllegalArgumentException("Illegal N");

        if (M == null || M <= 0)
            throw new IllegalArgumentException("Illegal M");

        if (particles == null || particles.size() != N)
            throw new IllegalArgumentException("Illegal particles list size");

        //// Check L, M and interactionRadius
        Double maxRadius = particles.stream().map(Particle::getRadius).max(Double::compareTo).get();
        if (L / M <= interactionRadius - maxRadius * 2)
            throw new IllegalArgumentException("Illegal M. Does not comply with L/M <= interactionRadius - maxRadius*2");
    }

    public CellIndexMethod(Double l, Integer n, Double interactionRadius, Integer m, Set<Particle> particles) {
        L = l;
        N = n;
        this.interactionRadius = interactionRadius;
        M = m;
        this.particles = particles;

        checkArguments();

        this.cells = new Cell[M][M];
        fillCells(particles);

        for (int c = 0; c < this.cells.length; c++) {
            for (int r = 0; r < this.cells[c].length; r++) {
                if (this.cells[c][r] != null) this.cells[c][r].analyze(interactionRadius, getNeighbours(r, c), L);
            }
        }

    }

    public Set<Particle> getParticles() {
        return particles;
    }
}
