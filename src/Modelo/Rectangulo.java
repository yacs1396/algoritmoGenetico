package Modelo;

public class Rectangulo {
    private int id;
    private Double x = 0.0;
    private Double y = 0.0;
    private Double ancho;
    private Double alto;

    public Rectangulo(){

    }

    public Rectangulo(int id, Double ancho,Double alto){
        this.x = 0.0;
        this.y = 0.0;
        this.id = id;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }
}
