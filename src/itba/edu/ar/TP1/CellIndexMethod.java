package itba.edu.ar.TP1;


import itba.edu.ar.TP1.models.Cell;
import itba.edu.ar.TP1.models.Particle;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CellIndexMethod {

    private final Double L;
    private final Integer N;
    private final Double interactionRadius;
    private final Integer M;
    private final Set<Particle> particles;
    private final Cell[][] cells;

    private void fillCells(Set<Particle> particles){
        particles.forEach(particle -> {
            Integer indexX = particle.getindexX(L,M);
            Integer indexY = particle.getindexY(L,M);

            if(cells[indexX][indexY] == null){
                cells[indexX][indexY] = new Cell();
            }
            cells[indexX][indexY].addParticle(particle);
        });
    }

    public CellIndexMethod(Double l, Integer n, Double interactionRadius, Integer m, Set<Particle> particles) {
        L = l;
        N = n;
        this.interactionRadius = interactionRadius;
        M = m;
        this.particles = particles;

        //// Check arguments
        if(L == null || L <= 0)
            throw new IllegalArgumentException("Illegal L");

        if(interactionRadius == null || interactionRadius <= 0)
            throw new IllegalArgumentException("Illegal interaction Radius");

        if(N == null || N <= 0)
            throw new IllegalArgumentException("Illegal N");

        if(M == null || M <= 0)
            throw new IllegalArgumentException("Illegal M");

        if(particles == null || particles.size() != N)
            throw new IllegalArgumentException("Illegal");

        //// Check L, M and interactionRadius
        Double maxRadius= particles.stream().map(Particle::getRadius).max(Double::compareTo).get();

        if( L/M <= interactionRadius - maxRadius*2 )
            throw new IllegalArgumentException("Illegal M");

        this.cells = new Cell[M][M];


    }
}
