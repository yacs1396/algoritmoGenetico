package Modelo;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static Modelo.Constantes.*;

public class Cromosoma {

    private List<String> listaGenes;
    private Double fitness;
    private Nodo arbol;


    public Cromosoma(){
        listaGenes = new ArrayList<String>();
    }

    public List<String> getListaGenes() {
        return listaGenes;
    }

    public void setListaGenes(List<String> listaGenes) {
        this.listaGenes = listaGenes;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Nodo getArbol() {
        return arbol;
    }

    public void setArbol(Nodo arbol) {
        this.arbol = arbol;
    }


    public void añadirGen(String idPieza){
        listaGenes.add(""+idPieza);
    }

    private boolean isNumeric(String gen) {
        String caracterNumerico = gen;
        if(gen.contains(ROTACION)){
            caracterNumerico = (gen.split(ROTACION))[0];
        }
        return caracterNumerico != null && caracterNumerico.matches("[-+]?\\d*\\.?\\d+");
    }



    public List<String> separarPiezas(){
        List<String> listaPiezas = new ArrayList<String>();
        for(String gen : listaGenes){
            String caracterNumerico = gen;
            if(gen.contains(ROTACION)){
                caracterNumerico = (gen.split(ROTACION))[0];
            }
            if(isNumeric(caracterNumerico)){
                listaPiezas.add(gen);
            }
        }
        return listaPiezas;
    }

    public List<String> removerPiezas(){
        List<String> listaPiezas = new ArrayList<String>(listaGenes);
        System.out.println("ANTES ======================");
        System.out.println("tam lista genes: "+listaGenes.size());
        System.out.println("tam lista piezas: "+listaPiezas.size());
        for(int i=0; i< listaGenes.size();i++){
            String gen = listaPiezas.get(i);
            String caracterNumerico = gen;
            if(gen.contains(ROTACION)){
                caracterNumerico = (gen.split(ROTACION))[0];
            }
            if(isNumeric(caracterNumerico)){
                listaPiezas.remove(gen);
                listaPiezas.add(i,ESPACIO_PIEZA_VACIA);
            }
        }
        System.out.println("DESPUES  ======================");
        System.out.println("tam lista genes: "+listaGenes.size());
        System.out.println("tam lista piezas: "+listaPiezas.size());
        System.out.println("======================");


        return listaPiezas;
    }

    public int cantidadPiezas(){
        int cantidadPiezas = 0;
        for(String gen : listaGenes){
            String caracterNumerico = gen;
            if(gen.contains(ROTACION)){
                caracterNumerico = (gen.split(ROTACION))[0];
            }
            if(isNumeric(caracterNumerico)){
                cantidadPiezas++;
            }
        }
        return cantidadPiezas;
    }

    public int cantidadOperadores(){
        int cantidadOperadores = 0;
        for(String gen : listaGenes){
            if((!isNumeric(gen))){
                cantidadOperadores++;
            }
        }
        return cantidadOperadores;
    }

    private Rectangulo buscarPieza(List<Rectangulo> listaPiezas, int idPieza){
        for(Rectangulo pieza : listaPiezas){
            if(pieza.getId() == idPieza){
                return pieza;
            }
        }
        System.out.println("No se encontro ninguna pieza con el id: "+idPieza);
        return null;
    }

    public void crearArbol(List<Rectangulo> listaPiezas){
        Stack<Nodo> pilaNodos = new Stack<Nodo>();
        System.out.println("Lista de genes---");
        for(int i=0;i < listaGenes.size();i++){
            System.out.print(listaGenes.get(i)+", ");
        }
        System.out.println("--");
        for(int i=0;i < listaGenes.size();i++){
            String gen = listaGenes.get(i);
            System.out.println("Gen: "+gen+" ID: "+i);
            Nodo nodo = new Nodo();
            nodo.setIdNodo(i);
            nodo.setDerecho(null);
            nodo.setIzquierdo(null);
            if(gen.equalsIgnoreCase(ROTACION_HORIZONTAL) || gen.equalsIgnoreCase(ROTACION_VERTICAL)){
                nodo.setTipoIntegracion(gen);
                nodo.setRectangulo(null);
                Double area = 0.0;
                Nodo nodoDerecho = pilaNodos.pop();
                Nodo nodoIzquierdo = pilaNodos.pop();
                area = nodoDerecho.getArea();
                area += nodoIzquierdo.getArea();
                nodo.setArea(area);
                Double alto;
                if(nodoDerecho.getAlto() > nodoIzquierdo.getAlto()){
                    alto = nodoDerecho.getAlto();
                }else{
                    alto = nodoIzquierdo.getAlto();
                }

                Double ancho;
                if(nodoDerecho.getAncho() > nodoIzquierdo.getAncho()){
                    ancho = nodoDerecho.getAncho();
                }else{
                    ancho = nodoIzquierdo.getAncho();
                }

                nodo.setAlto(alto);
                nodo.setAncho(ancho);
                nodo.setIzquierdo(nodoIzquierdo);
                nodo.setDerecho(nodoDerecho);
                pilaNodos.push(nodo);
            }else{
                String caracterNumerico = gen;
                if(gen.contains(ROTACION)){
                    caracterNumerico = (gen.split(ROTACION))[0];
                }
                int idPieza = Integer.parseInt(caracterNumerico);
                Rectangulo pieza = buscarPieza(listaPiezas,idPieza);
                nodo.setRectangulo(pieza);
                nodo.setTipoIntegracion(null);
                Double ancho;
                Double alto;
                if(gen.length() == LONGITUD_GEN_ROTACION){
                    ancho = pieza.getAlto();
                    alto = pieza.getAncho();
                }else{
                    ancho = pieza.getAncho();
                    alto = pieza.getAlto();
                }
                nodo.setAncho(ancho);
                nodo.setAlto(alto);
                nodo.setArea(ancho*alto);
                pilaNodos.push(nodo);
            }

            if(i==listaGenes.size()-1){
                this.arbol = nodo;
            }
        }

    }



    private void agregarHijos(List<Nodo> listaNodos,Nodo nodo){
        Nodo nodoIzquierdo = nodo.getIzquierdo();
        Nodo nodoDerecho = nodo.getDerecho();
        //System.out.println("id: "+nodo.getIdNodo());
        if(nodoIzquierdo == null && nodoDerecho == null){
            //System.out.println(""+nodo.getIdNodo());
            listaNodos.add(nodo);
            return;
        }


        agregarHijos(listaNodos,nodoIzquierdo);

        agregarHijos(listaNodos,nodoDerecho);

        listaNodos.add(nodo);

    }

    public List<Nodo> obtenerListaSubNodos(Nodo arbol){
        List<Nodo> listaNodos = new ArrayList<Nodo>();

        Nodo nodo = arbol;

        //listaNodos.add(nodo);

        agregarHijos(listaNodos,nodo);

        return listaNodos;
    }


    public List<Nodo> obtenerListaNodosArbol(){
        return obtenerListaSubNodos(arbol);
    }
}
