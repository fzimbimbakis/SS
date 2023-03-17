import models.Particle;
import org.junit.Assert;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

public class GeneralTests {
    static final Double INTERACTION_RADIUS = 2.0;
    static final Double PARTICLE_RAD = 1.0;
    static final Integer M = 3;
    static final Double L = 12.0001;
    static final Set<Particle> particles = new HashSet<>();

    @Test
    public void checkMCondition() {
        particles.add(new Particle(1, 2.0, 2.0, PARTICLE_RAD));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 1, INTERACTION_RADIUS, M + 1, particles));
    }

    @Test
    public void checkArgumentConditions() {
        // N
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 0, INTERACTION_RADIUS, M, particles));
        particles.add(new Particle(1, 2.0, 2.0, PARTICLE_RAD));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, -1, INTERACTION_RADIUS, M, particles));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, 100, INTERACTION_RADIUS, M, particles));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, null, INTERACTION_RADIUS, M, particles));

        // L
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(-1.0, particles.size(), INTERACTION_RADIUS, M, particles));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(null, particles.size(), INTERACTION_RADIUS, M, particles));

        // Radius
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), -1.0, M, particles));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), null, M, particles));

        // Particles
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, M, null));

        // M
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, null, particles));
        Assert.assertThrows(IllegalArgumentException.class, () -> new CellIndexMethodPeriodic(L, particles.size(), INTERACTION_RADIUS, -1, particles));
    }

}
