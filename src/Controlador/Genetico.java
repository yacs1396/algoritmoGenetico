package Controlador;

import Modelo.*;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static Modelo.Constantes.ESPACIO_PIEZA_VACIA;
import static Modelo.Constantes.MAXIMA_GENERACION;

public class Genetico {

    private List<String> listaOperadores;
    private List<String> listaDecision;
    private List<Stock> listaStock;
    private List<Rectangulo> listaPiezas;
    private static String DECISION_PIEZA = "pieza";
    private static String DECISION_OPERADOR = "operador";
    private boolean cantidadStockOk;

    public Genetico(List<Stock> listaStock,List<Rectangulo> listaPiezas){
        listaOperadores = new ArrayList<String>();
        listaOperadores.add("H");
        listaOperadores.add("V");

        listaDecision= new ArrayList<String>();
        listaDecision.add(DECISION_PIEZA);
        listaDecision.add(DECISION_OPERADOR);

        if(listaStock.size() > listaPiezas.size()){
            cantidadStockOk = true;
            for(int i=listaPiezas.size(); i < listaStock.size();i++){
                listaStock.remove(i);
            }
        }else if(listaStock.size() < listaPiezas.size()){
            cantidadStockOk = false;
        }else{
            cantidadStockOk = true;
        }

        this.listaStock = new ArrayList<>(listaStock);
        this.listaPiezas = new ArrayList<>(listaPiezas);
    }

    private Rectangulo crearPiezaPrueba(int idPieza){
        Rectangulo pieza = new Rectangulo();
        pieza.setId(idPieza);
        int alto = 40;
        int ancho = 40;
        switch(idPieza){
            case 1:
                alto = 8;
                ancho = 40;
                break;
            case 2:
                alto = 10;
                ancho = 10;
                break;
            case 3:
                alto = 10;
                ancho = 10;
                break;
            case 4:
                alto = 10;
                ancho = 10;
                break;
            case 5:
                alto = 25;
                ancho = 40;
                break;
            case 6:
                alto = 8;
                ancho = 40;
                break;
            case 7:
                alto = 10;
                ancho = 10;
                break;
            case 8:
                alto = 8;
                ancho = 45;
                break;
            case 9:
                alto = 10;
                ancho = 40;
                break;
        }

        pieza.setAncho(new Double(ancho));
        pieza.setAlto(new Double(alto));

        return pieza;
    }

    private void agregarPieza(List<Rectangulo> listaPiezas,int idPieza){

        Rectangulo pieza = crearPiezaPrueba(idPieza);
        listaPiezas.add(pieza);
    }

    private Rectangulo obtenerPiezaAleatoria(List<Rectangulo> listaPiezas){
        try{
            Rectangulo  pieza =  listaPiezas.get((new Random()).nextInt(listaPiezas.size()));
            listaPiezas.remove(pieza);
            return pieza;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void añadirPiezaAleatoriaCromosoma(List<Rectangulo> listaPiezas, Cromosoma cromosoma){
        Rectangulo piezaAleatoria = obtenerPiezaAleatoria(listaPiezas);
        if(piezaAleatoria == null){
            System.out.println("Error al obtener pieza aleatoria");
        }
        int idPieza = piezaAleatoria.getId();
        cromosoma.añadirGen(""+idPieza);
    }

    private void añadirOperadorAleatoriaCromosoma(Cromosoma cromosoma){

        try{
            String operador = listaOperadores.get((new Random()).nextInt(listaOperadores.size()));
            cromosoma.añadirGen(operador);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private Boolean seleccionAleatoriaEsPieza(){
        try{
            String decision = listaDecision.get((new Random()).nextInt(listaDecision.size()));
            if(decision.equalsIgnoreCase(DECISION_PIEZA)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Cromosoma crearIndividuoAleatorio(int numeroPiezas){
        //List<String> listaGenes = new ArrayList<String>();
        Cromosoma cromosoma = new Cromosoma();
        int longitudCromosoma = numeroPiezas*2 - 1;
        int numeroGenes = longitudCromosoma;
        int numeroOperadoresIntegracion = numeroPiezas - 1;
        List<Rectangulo> listaPiezas = new ArrayList<>(this.listaPiezas);

//        List<Rectangulo> listaPiezas   = new ArrayList<Rectangulo>();
//
//        for(int i = 1; i <= numeroPiezas;i++){
//            agregarPieza(listaPiezas,i);
//        }

        añadirPiezaAleatoriaCromosoma(listaPiezas,cromosoma);
        añadirPiezaAleatoriaCromosoma(listaPiezas,cromosoma);

        for(int i= 3; i<= longitudCromosoma;i++){
            int cantidadPiezas = cromosoma.cantidadPiezas();
            int cantidadOperadores = cromosoma.cantidadOperadores();
            if(cantidadOperadores == cantidadPiezas-1){
                añadirPiezaAleatoriaCromosoma(listaPiezas,cromosoma);
            }else{
                if(listaPiezas.isEmpty()){
                    añadirOperadorAleatoriaCromosoma(cromosoma);
                }else{
                    Boolean piezaAleatoria = seleccionAleatoriaEsPieza();
                    if(piezaAleatoria == null){
                        System.out.println("Error al decidir entre pieza o operador");
                        return null;
                    }

                    if(piezaAleatoria){
                        añadirPiezaAleatoriaCromosoma(listaPiezas,cromosoma);
                    }else{
                        añadirOperadorAleatoriaCromosoma(cromosoma);
                    }

                }
            }
        }

        return cromosoma;

    }

    private List<Cromosoma> contruirPoblacionInicial(int tamanhoPoblacion){
        List<Cromosoma> poblacionInicial = new ArrayList<Cromosoma>();
        int numeroPiezas = this.listaPiezas.size();
        for(int i=0; i<tamanhoPoblacion; i++){
            poblacionInicial.add(crearIndividuoAleatorio(numeroPiezas));
        }
        return poblacionInicial;
    }

    private void ordenarListaPorMayorFitness(List<Cromosoma> listaCromosomas){
        listaCromosomas.sort(Comparator.comparingDouble(Cromosoma::getFitness).reversed());
    }

    private void ordenarListaPorMenorFitness(List<Cromosoma> listaCromosomas){
        listaCromosomas.sort(Comparator.comparingDouble(Cromosoma::getFitness));
        System.out.println("Ordenado---------");
        for(Cromosoma cromosoma:listaCromosomas){
            System.out.print("Fit: "+cromosoma.getFitness());
        }
    }


    private void ordenarNodosPorArea(List<Nodo> listaNodos){
        listaNodos.sort(Comparator.comparingDouble(Nodo::getArea).reversed());
    }

    private void ordenarStockPorArea(List<Stock>  listaStock){
        listaStock.sort(Comparator.comparingDouble(Stock::getArea).reversed());
    }

    private List<Nodo>  eliminarSubArboles(List<Nodo> listaNodos, Nodo nodoBase,Cromosoma cromosoma){
        if(nodoBase.getIzquierdo() == null && nodoBase.getDerecho()==null){
            return listaNodos;
        }else{
            List<Nodo> listaSubnodos = cromosoma.obtenerListaSubNodos(nodoBase);
            listaSubnodos.remove(nodoBase);
            ordenarNodosPorArea(listaSubnodos);
            List<Nodo> copiaListaNodos = new ArrayList<Nodo>(listaNodos);
            for(Nodo nodo: listaNodos){
                for(Nodo subNodo : listaSubnodos){
                    if(nodo.getIdNodo() == subNodo.getIdNodo()){
                        copiaListaNodos.remove(nodo);
                    }
                }
            }

            return copiaListaNodos;
        }

    }

    public  List<Stock> algoritmoDistribucionStocks(Cromosoma cromosoma,List<Stock>  listaStock){

        cromosoma.crearArbol(this.listaPiezas);

        List<Nodo> listaNodos = cromosoma.obtenerListaNodosArbol();

        ordenarNodosPorArea(listaNodos);
        ordenarStockPorArea(listaStock);
        List<Stock>  listaStockNuevo = new ArrayList<Stock>();
        int nroStock = 0;
        while(!listaNodos.isEmpty()){
            Nodo nodo = listaNodos.get(0);
            Stock stock = listaStock.get(nroStock);
            if(nodo.getArea() <= stock.getArea()){
                stock.setBloqueUsado(nodo);
                listaStockNuevo.add(stock);
                listaNodos = eliminarSubArboles(listaNodos,nodo,cromosoma);
                nroStock++;
            }

            listaNodos.remove(0);
        }

        return listaStockNuevo;
    }

    private Double calcularFitnessStock(Stock stock){
        return ((1 - (stock.getBloqueUsado().getArea()/stock.getArea())) * 100);
    }

    public List<Stock> llenarStockDistribuido(Cromosoma cromosoma){
        return algoritmoDistribucionStocks(cromosoma,listaStock);
    }

    private void calcularFitnessLista(List<Cromosoma> listaCromosomas){

        List<Stock> listaStock = new ArrayList<Stock>(this.listaStock);

//        List<Stock> listaStock = new ArrayList<Stock>();
//        for(int i=0;i<10;i++){
//            Stock stock = new Stock();
//            stock.setAlto(100);
//            stock.setAncho(100);
//            stock.setArea(new Double(100*100));
//            listaStock.add(stock);
//        }

        for(Cromosoma cromosoma: listaCromosomas){

            List<Stock> listaStockDistribuido = algoritmoDistribucionStocks(cromosoma,listaStock);

            Double areaPiezas = 0.0;
            Double areaStock = 0.0;
            for(Stock stock : listaStockDistribuido){

                //FUNCION OBJETIVO
                areaPiezas += stock.getBloqueUsado().getArea();
                areaStock += stock.getArea();
                //fitness += calcularFitnessStock(stock);
            }

            Double fitness = ((1 - (areaPiezas/areaStock)) * 100) ;
            cromosoma.setFitness(fitness);
        }
    }

    private List<Cromosoma> seleccionElitismo(List<Cromosoma> listaCromosomas,int elitismo){
        //calcularFitnessLista(listaCromosomas);
        ordenarListaPorMenorFitness(listaCromosomas);
        List<Cromosoma> sobrevivientes = new ArrayList<Cromosoma>();
        for(int i=0;i<=elitismo-1;i++){
            sobrevivientes.add(listaCromosomas.get(i));
        }
        return sobrevivientes;
    }

    private Cromosoma seleccion(List<Cromosoma> poblacion){
        Double sumaFitnessPoblacion = 0.0;
        for(Cromosoma cromosoma: poblacion){
            sumaFitnessPoblacion += cromosoma.getFitness();
        }

        Random r = new Random();
        Double rangoMin = 0.0;
        Double rangoMax = sumaFitnessPoblacion;
        Double valorRandom = rangoMin + (rangoMax - rangoMin) * r.nextDouble();

        Double sumaFitnessRecorrer = 0.0;

        for(Cromosoma cromosoma : poblacion){
            sumaFitnessRecorrer += cromosoma.getFitness();
            if(sumaFitnessRecorrer >= valorRandom){
                return cromosoma;
            }
        }

        System.out.println("ERROR EN SELECCION");
        return null;
    }


    private void validarArreglarListasPiezasIntercambiadas(List<String> piezasIntercambioProgenitor1,List<String> piezasIntercambioProgenitor2,
                                                      int primerCorte,int segundoCorte,List<String> listaPiezasProgenitor1, List<String> listaPiezasProgenitor2){

        System.out.println("");
        System.out.println("Piezas Intercambio- ");
        System.out.println("Piezas Prog1: "+piezasIntercambioProgenitor1);
        System.out.println("Piezas Prog2: "+piezasIntercambioProgenitor2);
        int tamPiezasIntercambio = piezasIntercambioProgenitor1.size();
        List<Integer> listaRepetidaProg1 = new ArrayList<Integer>();
        List<Integer> listaRepetidaProg2 = new ArrayList<Integer>();
        for(int i=0; i<tamPiezasIntercambio;i++){
            for(int j= 0; j <primerCorte;j++){
                String piezaProg1 = piezasIntercambioProgenitor1.get(i);
                if(piezaProg1.equalsIgnoreCase(listaPiezasProgenitor1.get(j))){
                    listaRepetidaProg1.add(j);
                }


                String piezaProg2 = piezasIntercambioProgenitor2.get(i);
                if(piezaProg2.equalsIgnoreCase(listaPiezasProgenitor2.get(j))){
                    listaRepetidaProg2.add(j);
                }
            }

            //porque la posicion del segundo corte es parte de las piezas de intercambio
            for(int j= segundoCorte+1; j < listaPiezasProgenitor1.size();j++){
                String piezaProg1 = piezasIntercambioProgenitor1.get(i);
                if(piezaProg1.equalsIgnoreCase(listaPiezasProgenitor1.get(j))){
                    listaRepetidaProg1.add(j);
                }


                String piezaProg2 = piezasIntercambioProgenitor2.get(i);
                if(piezaProg2.equalsIgnoreCase(listaPiezasProgenitor2.get(j))){
                    listaRepetidaProg2.add(j);
                }
            }

        }
        System.out.println("");
        System.out.println("Repetidas-");
        System.out.println("Rep 1: "+listaRepetidaProg1);
        System.out.println("Rep 2: "+listaRepetidaProg2);
        int tamRepetidas = listaRepetidaProg1.size();
        // el tamaño con repetidas 2 deberia ser el mismo
        for(int i=0;i<tamRepetidas;i++){
            int indiceRepetidaProg1 = listaRepetidaProg1.get(i);
            int indiceRepetidaProg2 = listaRepetidaProg2.get(i);
            String piezaRepetidaProg1 = listaPiezasProgenitor1.get(indiceRepetidaProg1);
            String piezaRepetidaProg2 = listaPiezasProgenitor2.get(indiceRepetidaProg2);

            listaPiezasProgenitor1.remove(indiceRepetidaProg1);
            listaPiezasProgenitor1.add(indiceRepetidaProg1,piezaRepetidaProg2);

            listaPiezasProgenitor2.remove(indiceRepetidaProg2);
            listaPiezasProgenitor2.add(indiceRepetidaProg2,piezaRepetidaProg1);

        }

    }


    private void intercambiarPiezas(List<String> listaPiezasProgenitor1, List<String> listaPiezasProgenitor2, int primerCorte, int segundoCorte){

        System.out.println("Progenitor1 Antes: "+listaPiezasProgenitor1);
        System.out.println("Progenitor2 Antes: "+listaPiezasProgenitor2);
        System.out.println("1er Corte: "+primerCorte);
        System.out.println("2do Corte: "+segundoCorte);
        System.out.println("lista prog1 tam: "+listaPiezasProgenitor1.size());
        System.out.println("Lista prog2 tam: "+listaPiezasProgenitor2.size());

        List<String> piezasIntercambioProgenitor1 = new ArrayList<String>();
        List<String> piezasIntercambioProgenitor2 = new ArrayList<String>();

        for(int i= primerCorte; i <= segundoCorte;i++){

            String piezaProgenitor1 = listaPiezasProgenitor1.get(i);
            String piezaProgenitor2 = listaPiezasProgenitor2.get(i);
            piezasIntercambioProgenitor1.add(piezaProgenitor2);
            piezasIntercambioProgenitor2.add(piezaProgenitor1);

            // reemplazar piezaProg2 en progenitor1
            listaPiezasProgenitor1.remove(i);
            listaPiezasProgenitor1.add(i,piezaProgenitor2);

            // Reemplazar piezaProg1 en progenitor2
            listaPiezasProgenitor2.remove(i);
            listaPiezasProgenitor2.add(i,piezaProgenitor1);
        }
        System.out.println("Despues de intercambio-");
        System.out.println("Prog1: "+listaPiezasProgenitor1);
        System.out.println("Prog2: "+listaPiezasProgenitor2);
        validarArreglarListasPiezasIntercambiadas(piezasIntercambioProgenitor1,piezasIntercambioProgenitor2,primerCorte,segundoCorte,listaPiezasProgenitor1,listaPiezasProgenitor2);

    }

    private Cromosoma unirOperadoresyPiezas(List<String> listaSinPiezasProgenitor,List<String> listaPiezasProgenitor){
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("");
        System.out.println("UNIR--");
        System.out.println("Lista Piezas: "+listaPiezasProgenitor);
        System.out.println("Lista para Unir: "+listaSinPiezasProgenitor);
        for(int i= 0;i<listaSinPiezasProgenitor.size();i++){
            String elemento = listaSinPiezasProgenitor.get(i);
            if(elemento.equalsIgnoreCase(ESPACIO_PIEZA_VACIA)){
                if(!listaPiezasProgenitor.isEmpty()){
                    String pieza = listaPiezasProgenitor.get(0);
                    listaPiezasProgenitor.remove(0);
                    listaSinPiezasProgenitor.remove(i);
                    listaSinPiezasProgenitor.add(i,pieza);
                }
            }
        }
        System.out.println("Lista para Unir Despues: "+listaSinPiezasProgenitor);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Cromosoma cromosoma = new Cromosoma();
        cromosoma.setListaGenes(listaSinPiezasProgenitor);
        return cromosoma;
    }


    private List<Cromosoma> crossover(Cromosoma progenitor1,Cromosoma progenitor2){

        List<String> listaPiezasProgenitor1 = progenitor1.separarPiezas();
        List<String> listaPiezasProgenitor2 = progenitor2.separarPiezas();
        List<String> listaSinPiezasProgenitor1 = progenitor1.removerPiezas();
        List<String> listaSinPiezasProgenitor2 = progenitor2.removerPiezas();

        int min = 1;
        int primerCorte = (new Random()).nextInt(( (listaPiezasProgenitor1.size() - 1) - min) + 1) + min;
        int segundoCorte = (new Random()).nextInt(( listaPiezasProgenitor1.size() - 1 - primerCorte) + 1) + primerCorte;


        System.out.println("###############################################");
        System.out.println("Tam Prog1 Antes: "+listaPiezasProgenitor1.size());
        System.out.println("Tam Prog2 Antes: "+listaPiezasProgenitor2.size());
        intercambiarPiezas(listaPiezasProgenitor1,listaPiezasProgenitor2,primerCorte,segundoCorte);
        System.out.println("Progenitor1 Despues: "+listaPiezasProgenitor1);
        System.out.println("Progenitor2 Despues: "+listaPiezasProgenitor2);
        System.out.println("###############################################");
        Cromosoma hijo1 = unirOperadoresyPiezas(listaSinPiezasProgenitor1,listaPiezasProgenitor1);
        Cromosoma hijo2 = unirOperadoresyPiezas(listaSinPiezasProgenitor2,listaPiezasProgenitor2);

        List<Cromosoma> descendientes = new ArrayList<Cromosoma>();
        descendientes.add(hijo1);
        descendientes.add(hijo2);

        return descendientes;
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    private void intercambiarGenes(Cromosoma cromosoma, int indice1, int indice2){
        String gen1 = cromosoma.getListaGenes().get(indice1);
        String gen2 = cromosoma.getListaGenes().get(indice2);
        cromosoma.getListaGenes().remove(indice1);
        cromosoma.getListaGenes().add(indice1,gen2);
        cromosoma.getListaGenes().remove(indice2);
        cromosoma.getListaGenes().add(indice2,gen1);
    }

    private boolean validarPosRelativa(Cromosoma cromosoma,int indice1, int indice2){

        System.out.println("$$##");
        int cantidadOperadores = 0;
        int cantidadPiezas = 0;
        List<String> listaGenes = cromosoma.getListaGenes();
        int tamLista = listaGenes.size();
        for(int i=0;i<tamLista;i++){
            String gen = listaGenes.get(i);

            if(i == indice1){
                // para simular el intercambio
                // en esta posicion ahora se encontraria un operador
                cantidadOperadores++;
            }else if(i == indice2){
                // en esta posicion ahora se encontraria una pieza
                cantidadPiezas++;
            }else if(isNumeric(gen)){
                cantidadPiezas++;
            }else{
                cantidadOperadores++;
            }


            System.out.println("$$## cantOperadores: "+cantidadOperadores+" cant piezas: "+cantidadPiezas);
            if(cantidadOperadores > (cantidadPiezas - 1)){
                System.out.println("$$##Posicion invalida");
                return false;
            }


//            if( (i>=indice1) && (i<=indice2)){
//                if(cantidadOperadores <= cantidadPiezas - 3){
//                    return false;
//                }
//            }
        }
        System.out.println("$$##Posicion valida");
        return true;
    }

    private Cromosoma mutacionCromosoma(Cromosoma cromosoma){

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("Lista Genes Antes: "+cromosoma.getListaGenes());

        int min  = 0;
        int indice1 =  (new Random()).nextInt(( (cromosoma.getListaGenes().size() - 1) - min) + 1) + min;
        int indice2 =  (new Random()).nextInt(( (cromosoma.getListaGenes().size() - 1 ) - indice1) + 1) + indice1;

        System.out.println("Indice 1: "+indice1);
        System.out.println("Indice 2: "+indice2);


        if(indice1 != indice2){
            String gen1 = cromosoma.getListaGenes().get(indice1);
            String gen2 = cromosoma.getListaGenes().get(indice2);
            if(isNumeric(gen1) && isNumeric(gen2)){
                intercambiarGenes(cromosoma,indice1,indice2);
            }else if(!isNumeric(gen1)){
                intercambiarGenes(cromosoma,indice1,indice2);
            }else if(isNumeric(gen1) && (!isNumeric(gen2))){
                if(validarPosRelativa(cromosoma,indice1,indice2)){
                    intercambiarGenes(cromosoma,indice1,indice2);
                }else{
                    return null;
                }
            }
        }

        System.out.println("Lista de genes Despues: "+cromosoma.getListaGenes());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        return cromosoma;

    }

    private List<Cromosoma> mutacion(List<Cromosoma> nuevaGeneracion, float probabilidadMutacion){
        calcularFitnessLista(nuevaGeneracion);
        ordenarListaPorMenorFitness(nuevaGeneracion);
        int tamLista = nuevaGeneracion.size();
        for(int i=tamLista/2;i<tamLista;i++){
            Cromosoma cromosoma = nuevaGeneracion.get(i);
            Cromosoma cromosomaMutado = mutacionCromosoma(cromosoma);
            if(cromosomaMutado!=null){
                nuevaGeneracion.remove(i);
                nuevaGeneracion.add(i,cromosomaMutado);
            }
        }

        return nuevaGeneracion;
    }

    public Cromosoma realizarAlgoritmo(int tamanhoPoblacion, float probabilidadMutacion, int elitismo){

        if(cantidadStockOk){
            List<Cromosoma> poblacion = contruirPoblacionInicial(tamanhoPoblacion);
            // para la primera iteracion
            calcularFitnessLista(poblacion);
            int nroGeneracion = 0;
            while(nroGeneracion < MAXIMA_GENERACION){

                List<Cromosoma> nuevaGeneracion = seleccionElitismo(poblacion,elitismo);
                int tamanhoNuevaGeneracion = nuevaGeneracion.size();

                while(tamanhoNuevaGeneracion < tamanhoPoblacion){

                    // el fitness de la poblacion ya fue calculado al realizar la funcion de elitismo
                    Cromosoma progenitor1 = seleccion(poblacion);
                    Cromosoma progenitor2 = seleccion(poblacion);

                    List<Cromosoma> descendientes = crossover(progenitor1,progenitor2);

                    nuevaGeneracion.add(descendientes.get(0));
                    nuevaGeneracion.add(descendientes.get(1));
                    tamanhoNuevaGeneracion = nuevaGeneracion.size();
                }
                poblacion = mutacion(nuevaGeneracion,probabilidadMutacion);
                nroGeneracion++;
            }

            calcularFitnessLista(poblacion);
            List<Cromosoma> ultimaGeneracion = seleccionElitismo(poblacion,1);
            System.out.println("ultima generacion tam: "+ultimaGeneracion.size());
            return ultimaGeneracion.get(0);
        }else{
            System.out.println("La cantidad de stock debe cubrir la cantidad de piezas");
            return null;
        }
    }

}
