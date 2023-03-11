package TP1;

import TP1.models.Particle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class GeneralTests {
    static final Double INTERACTION_RADIUS = 2.0;
    static final Double PARTICLE_RAD = 1.0;
    static final Integer M = 3;
    static final Double L = 12.0001;
    static final Set<Particle> particles = new HashSet<>();

    @Test
    void checkMCondition() {
        particles.add(new Particle(1, 2.0, 2.0, PARTICLE_RAD));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 1, INTERACTION_RADIUS, M + 1, particles));
    }

    @Test
    void checkArgumentConditions() {
        // N
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 0, INTERACTION_RADIUS, M, particles));
        particles.add(new Particle(1, 2.0, 2.0, PARTICLE_RAD));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, -1, INTERACTION_RADIUS, M, particles));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 100, INTERACTION_RADIUS, M, particles));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, null, INTERACTION_RADIUS, M, particles));

        // L
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(-1.0, particles.size(), INTERACTION_RADIUS, M, particles));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(null, particles.size(), INTERACTION_RADIUS, M, particles));

        // Radius
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), -1.0, M, particles));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), null, M, particles));

        // Particles
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, M, null));

        // M
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, null, particles));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, -1, particles));
    }

}
