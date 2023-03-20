package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Particle {

    private final Integer id;
    protected Double x;
    protected Double y;
    private Double radius;
    protected final Set<Particle> neighbours;
    //// Movement
    private Double angle;
    private Double speed;

    public Particle(Integer id, Double x, Double y, Double radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();
    }

    public Particle(Integer id, Double x, Double y, Double radius, Double angle, Double speed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();
        this.speed = speed;
        this.angle = angle;
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
            throw new IllegalArgumentException("Particle " + id + " is out of bounds.");
        return (int) (x/(L/M));
    }

    public Integer getIndexY(Double L, Integer M){
        if(this.y > L || this.y < 0)
            throw new IllegalArgumentException("Particle " + id + " is out of bounds.");
        return (int) (y/(L/M));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addNeighbour(Particle neighbour){
        neighbours.add(neighbour);
    }

    public void clearNeighbours(){
        neighbours.clear();
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
        return x + " " + y + " " + Math.cos(angle)*speed + " " + Math.sin(angle)*speed;
    }


    public String neighboursToString() {
        StringBuilder builder = new StringBuilder(toString());
        neighbours.forEach(particle -> builder.append(", ").append(String.valueOf(id)));
        return builder.append("\n").toString();
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public Integer getId() {
        return id;
    }

    // si queremos que reboten en el borde
    // si es mayor a L -> le restamos a L el resto de x/L
    // si es menor a 0 usamos el valor absoluto

    public void moveParticle(Double L){
        this.x = (this.x + this.speed * Math.cos(this.angle)) % L;
        if (x < 0)
            x += L;
        this.y = (this.y + this.speed * Math.sin(this.angle)) % L;
        if (y < 0)
            y += L;
    }

    public void updateAngle(Double n){
        double senSum = Math.sin(angle) + neighbours.stream().mapToDouble(p -> Math.sin(p.angle)).sum();
        double cosSum = Math.cos(angle) + neighbours.stream().mapToDouble(p -> Math.cos(p.angle)).sum();
        double senAverage = senSum / (neighbours.size() + 1);
        double cosAverage = cosSum / (neighbours.size() + 1);

        angle = Math.atan2(senAverage, cosAverage) + Math.random() * n - (n/2);
    }

    public Integer normalizeAngle(int module) {
        return (int)(((angle + Math.PI)/ (2 * Math.PI)) * module);
    }
}
