package Modelo;

public class Nodo {

    private int idNodo;
    private Nodo izquierdo;
    private Nodo derecho;
    private Rectangulo rectangulo;
    private String tipoIntegracion;
    private Double area;


    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }

    public Rectangulo getRectangulo() {
        return rectangulo;
    }

    public void setRectangulo(Rectangulo rectangulo) {
        this.rectangulo = rectangulo;
    }

    public String getTipoIntegracion() {
        return tipoIntegracion;
    }

    public void setTipoIntegracion(String tipoIntegracion) {
        this.tipoIntegracion = tipoIntegracion;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public int getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(int idNodo) {
        this.idNodo = idNodo;
    }
}
