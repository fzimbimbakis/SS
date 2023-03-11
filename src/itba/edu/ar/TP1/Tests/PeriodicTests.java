package itba.edu.ar.TP1.Tests;

import itba.edu.ar.TP1.CellIndexMethodPeriodic;
import itba.edu.ar.TP1.models.Particle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class PeriodicTests {
//             ___ ___ ___
//            | G | H | I |
//            |___|___|___|
//            | D | E | F |
//            |___|___|___|
//            | A | B | C |
//            |___|___|___|

    static final Double INTERACTION_RADIUS = 2.0;
    static final Double PARTICLE_RAD = 1.0;
    static final Integer M = 3;
    static final Double L = 12.0001;

    static final Coordinates A = new Coordinates(0.0, 0.0);
    static final Coordinates B = new Coordinates(4.0, 0.0);
    static final Coordinates C = new Coordinates(8.0, 0.0);
    static final Coordinates D = new Coordinates(0.0, 4.0);
    static final Coordinates E = new Coordinates(4.0, 4.0);
    static final Coordinates F = new Coordinates(8.0, 4.0);
    static final Coordinates G = new Coordinates(0.0, 8.0);
    static final Coordinates H = new Coordinates(4.0, 8.0);
    static final Coordinates I = new Coordinates(8.0, 8.0);

    static final Set<Particle> particles = new HashSet<>();
    static final Map<Integer, Particle> map_A = new HashMap<>();
    static final List<Integer> yesIds = new ArrayList<>();
    static final List<Integer> noIds = new ArrayList<>();

    @BeforeEach
    void before() {
        yesIds.removeIf(id -> true);
        noIds.removeIf(id -> true);
        particles.removeIf(p -> true);
    }

    private static class Coordinates {
        public Coordinates(Double x, Double y) {
            this.x = x;
            this.y = y;
        }

        public Double x;
        public Double y;
    }

    private Map<Integer, Particle> createParticles(Coordinates center, Coordinates right, Coordinates left, Coordinates top, Coordinates top_right, Coordinates top_left, Coordinates bottom, Coordinates bottom_right, Coordinates bottom_left) {
        map_A.put(1, new Particle(1, center.x + 0.5, center.y + 2.0, PARTICLE_RAD));
        map_A.put(2, new Particle(2, center.x + 3.5, center.y + 2.0, PARTICLE_RAD));
        particles.addAll(map_A.values());


        // Main cell
        // Yes
        particles.add(new Particle(1, center.x + 0.5, center.y + 2.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(2, center.x + 3.5, center.y + 2.0, PARTICLE_RAD));

        // Right cell
        // Yes
        particles.add(new Particle(10, right.x + 1.0, right.y + 2.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(20, right.x + 3.8, right.y + 2.0, PARTICLE_RAD));

        // Left cell
        // Yes
        particles.add(new Particle(11, left.x + 3.0, left.y + 2.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(21, left.x + 0.2, left.y + 2.0, PARTICLE_RAD));

        // Top cell
        // Yes
        particles.add(new Particle(12, top.x + 2.0, top.y + 1.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(22, top.x + 2.0, top.y + 3.0, PARTICLE_RAD));

        // Top right cell
        // Yes
        particles.add(new Particle(13, top_right.x + 1.0, top_right.y + 1.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(23, top_right.x + 3.0, top_right.y + 3.0, PARTICLE_RAD));

        // Top left cell
        // Yes
        particles.add(new Particle(14, top_left.x + 3.0, top_left.y + 1.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(24, top_left.x + 1.0, top_left.y + 3.0, PARTICLE_RAD));

        // Bottom cell
        // Yes
        particles.add(new Particle(15, bottom.x + 2.0, bottom.y + 3.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(25, bottom.x + 2.0, bottom.y + 1.0, PARTICLE_RAD));

        // Bottom right cell
        // Yes
        particles.add(new Particle(16, bottom_right.x + 1.0, bottom_right.y + 3.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(26, bottom_right.x + 3.0, bottom_right.y + 1.0, PARTICLE_RAD));

        // Bottom left cell
        // Yes
        particles.add(new Particle(17, bottom_left.x + 3.0, bottom_left.y + 3.0, PARTICLE_RAD));
        // No
        particles.add(new Particle(27, bottom_left.x + 1.0, bottom_left.y + 1.0, PARTICLE_RAD));
        return map_A;
    }

    void assertNeighbours(Map<Integer, Particle> map) {

        Integer[] a1 = new Integer[]{2, 11, 12, 14, 15, 17};
        Integer[] a2 = new Integer[]{1, 10, 12, 13, 15, 16};

        // Assertions 1
        Assertions.assertEquals(a1.length, map.get(1).getNeighbours().size());
        Assertions.assertTrue(map.get(1).getNeighbours().stream().mapToInt(Particle::getId).allMatch(a -> Arrays.stream(a1).anyMatch(b -> a == b)));
        // Assertions 2
        Assertions.assertEquals(a2.length, map.get(2).getNeighbours().size());
        Assertions.assertTrue(map.get(2).getNeighbours().stream().mapToInt(Particle::getId).allMatch(a -> Arrays.stream(a2).anyMatch(b -> a == b)));
    }

    @Test
    void checkA() {
        // Particles
        Map<Integer, Particle> map = createParticles(A, B, C, D, E, F, G, H, I);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkB() {
        // Particles
        Map<Integer, Particle> map = createParticles(B, C, A, E, F, D, H, I, G);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkC() {
        // Particles
        Map<Integer, Particle> map = createParticles(C, A, B, F, D, E, I, G, H);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkD() {
        // Particles
        Map<Integer, Particle> map = createParticles(D, E, F, G, H, I, A, B, C);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkE() {
        // Particles
        Map<Integer, Particle> map = createParticles(E, F, D, H, I, G, B, C, A);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkF() {
        // Particles
        Map<Integer, Particle> map = createParticles(F, D, E, I, G, H, C, A, B);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkG() {
        // Particles
        Map<Integer, Particle> map = createParticles(G, H, I, A, B, C, D, E, F);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkH() {
        // Particles
        Map<Integer, Particle> map = createParticles(H, I, G, B, C, A, E, F, D);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }

    @Test
    void checkI() {
        // Particles
        Map<Integer, Particle> map = createParticles(I, G, H, C, A, B, F, D, E);

        // Method
        new CellIndexMethodPeriodic(L, 18, INTERACTION_RADIUS, M, particles);

        // Assertions
        assertNeighbours(map);
    }


}