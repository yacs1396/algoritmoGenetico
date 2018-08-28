package Modelo;

import java.util.List;

public class Stock {

    private Double ancho;
    private Double alto;
    private Nodo bloqueUsado;
    private Double area;

    public Stock(){

    }

    public Stock(Double ancho,Double alto){
        this.ancho = ancho;
        this.alto = alto;
        this.area = new Double(ancho*alto);
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


    public Nodo getBloqueUsado() {
        return bloqueUsado;
    }

    public void setBloqueUsado(Nodo bloqueUsado) {
        this.bloqueUsado = bloqueUsado;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
