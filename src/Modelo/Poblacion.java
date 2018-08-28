package Modelo;

import java.util.List;

public class Poblacion {

    private int tamaño;
    private float probabilidadMutacion;
    private int generacion;
    private List<Cromosoma> listaIndividuos;


    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public float getProbabilidadMutacion() {
        return probabilidadMutacion;
    }

    public void setProbabilidadMutacion(float probabilidadMutacion) {
        this.probabilidadMutacion = probabilidadMutacion;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public List<Cromosoma> getListaIndividuos() {
        return listaIndividuos;
    }

    public void setListaIndividuos(List<Cromosoma> listaIndividuos) {
        this.listaIndividuos = listaIndividuos;
    }
}
