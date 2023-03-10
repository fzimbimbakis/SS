package itba.edu.ar.TP1.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Particle {

    private final Integer id;
    private Double x;
    private Double y;
    private Double radius;
    private final Set<Particle> neighbours;

    public Particle(Integer id, Double x, Double y, Double radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id.equals(particle.id);
    }

    public Integer getIndexX(Double L, Integer M){
        if(this.x > L || this.x < 0)
            throw new IllegalArgumentException("Paticle " + id + " is out of bounds.");
        return (int) (x/(L/M));
    }

    public Integer getIndexY(Double L, Integer M){
        if(this.y > L || this.y < 0)
            throw new IllegalArgumentException("Paticle " + id + " is out of bounds.");
        return (int) (y/(L/M));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addNeighbour(Particle neighbour){
        neighbours.add(neighbour);
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getRadius() {
        return radius;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public boolean isNeighbour(Particle neighbour, Double interactionRadius) {
        return isNeighbour(neighbour.getX(), neighbour.getY(), neighbour.getRadius(), interactionRadius);
    }

    public boolean isNeighbour(Double neighbourX, Double neighbourY, Double rad, Double interactionRadius){
        return (Math.sqrt(Math.pow(neighbourX - this.x, 2) + Math.pow(neighbourY - this.y, 2)) - rad - radius) < interactionRadius;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }


    public String neighboursToString() {
        StringBuilder builder = new StringBuilder(toString());
        neighbours.forEach(particle -> builder.append(", ").append(particle));
        return builder.append("\n").toString();
    }
}
