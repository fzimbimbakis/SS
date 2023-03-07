package itba.edu.ar.TP1;

import itba.edu.ar.TP1.models.Cell;
import itba.edu.ar.TP1.models.Particle;

import java.util.HashSet;
import java.util.Set;

public class CellIndexMethodPeriodic extends CellIndexMethod {

    public CellIndexMethodPeriodic(Double l, Integer n, Double interactionRadius, Integer m, Set<Particle> particles) {
        super(l, n, interactionRadius, m, particles);
    }

    @Override
    protected Set<Cell> getNeighbours(Integer row, Integer col) {

        Set<Cell> neighbours = new HashSet<>();
        neighbours.add(this.cells[(row - 1) % M][col]);
        neighbours.add(this.cells[(row - 1) % M][(col + 1) % M]);
        neighbours.add(this.cells[row][(col + 1) % M]);
        neighbours.add(this.cells[(row + 1) % M][(col + 1) % M]);

        return neighbours;
    }
}
