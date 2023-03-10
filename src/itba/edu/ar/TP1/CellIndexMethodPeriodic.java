package itba.edu.ar.TP1;

import itba.edu.ar.TP1.models.Cell;
import itba.edu.ar.TP1.models.Particle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CellIndexMethodPeriodic extends CellIndexMethod {

    public CellIndexMethodPeriodic(Double l, Integer n, Double interactionRadius, Integer m, Set<Particle> particles) {
        super(l, n, interactionRadius, m, particles);
    }

    @Override
    protected Map<Cell.NEIGHBOURS_POSITION, Cell> getNeighbours(Integer col, Integer row) {
        Map<Cell.NEIGHBOURS_POSITION, Cell> neighbours = new HashMap<>();

        neighbours.put(row == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_TOP : Cell.NEIGHBOURS_POSITION.TOP, this.cells[col][(row + 1) % M]);

        neighbours.put(row == M - 1 || col == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_TOP_RIGHT : Cell.NEIGHBOURS_POSITION.TOP_RIGHT, this.cells[(col + 1) % M][(row + 1) % M]);

        neighbours.put(col == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_RIGHT : Cell.NEIGHBOURS_POSITION.RIGHT, this.cells[(col + 1) % M][row]);

        neighbours.put(row == 0 || col == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_BOTTOM_RIGHT : Cell.NEIGHBOURS_POSITION.BOTTOM_RIGHT, row == 0 ? this.cells[(col + 1) % M][M - 1] : this.cells[(col + 1) % M][(row - 1) % M]);

        return neighbours;
    }
}
