package TP1;

import TP1.models.Cell;
import TP1.models.Particle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CellIndexMethodPeriodic extends CellIndexMethod {

    public CellIndexMethodPeriodic(Double l, Integer n, Double interactionRadius, Integer m, Set<Particle> particles) {
        super(l, n, interactionRadius, m, particles);
    }

    @Override
    protected Map<Cell, Cell.NEIGHBOURS_POSITION> getNeighbours(Integer row, Integer col) {
        Map<Cell, Cell.NEIGHBOURS_POSITION> neighbours = new HashMap<>();

        neighbours.put(this.cells[col][(row + 1) % M], row == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_TOP : Cell.NEIGHBOURS_POSITION.TOP);

        neighbours.put(this.cells[(col + 1) % M][row], col == M - 1 ? Cell.NEIGHBOURS_POSITION.GHOST_RIGHT : Cell.NEIGHBOURS_POSITION.RIGHT);

        if(col == M - 1){
            if(row == M - 1)
                neighbours.put(this.cells[0][0], Cell.NEIGHBOURS_POSITION.GHOST_TOP_RIGHT);
            else neighbours.put(this.cells[0][row + 1], Cell.NEIGHBOURS_POSITION.GHOST_RIGHT);
        }else{
            if(row == M - 1)
                neighbours.put(this.cells[col + 1][0], Cell.NEIGHBOURS_POSITION.GHOST_TOP);
            else neighbours.put(this.cells[col + 1][row + 1], Cell.NEIGHBOURS_POSITION.TOP_RIGHT);
        }

        if(col == M - 1){
            if(row == 0)
                neighbours.put(this.cells[(col + 1) % M][M - 1], Cell.NEIGHBOURS_POSITION.GHOST_BOTTOM_RIGHT);
            else neighbours.put(this.cells[0][row - 1], Cell.NEIGHBOURS_POSITION.GHOST_RIGHT);
        }else {
            if (row == 0)
                neighbours.put(this.cells[col + 1][M - 1], Cell.NEIGHBOURS_POSITION.GHOST_BOTTOM);
            else neighbours.put(this.cells[col + 1][row - 1], Cell.NEIGHBOURS_POSITION.BOTTOM_RIGHT);
        }

        return neighbours;
    }
}
