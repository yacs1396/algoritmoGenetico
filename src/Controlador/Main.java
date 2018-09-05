package Controlador;

import Modelo.Cromosoma;
import Modelo.Nodo;
import Modelo.Rectangulo;
import Modelo.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public void imprimirLista(List<Integer> lista){
        for(Integer valor : lista){
            System.out.println(valor+", ");
        }
    }

    public static void main(String ar[]){

        Stock stock = new Stock(10.0,10.0);
        Stock stock2 = new Stock(20.0,10.0);
        Stock stock3 = new Stock(30.0,10.0);
        Stock stock4 = new Stock(40.0,10.0);
        Stock stock5= new Stock(50.0,10.0);


        List<Stock> listaStock = new ArrayList<Stock>();
        listaStock.add(stock);
        listaStock.add(stock2);
        listaStock.add(stock3);
        listaStock.add(stock4);
        listaStock.add(stock5);


        Rectangulo rectangulo1 = new Rectangulo(7,9.0,9.0);
        Rectangulo rectangulo2 = new Rectangulo(8,20.0,9.0);
        Rectangulo rectangulo3 = new Rectangulo(9,30.0,9.0);
        Rectangulo rectangulo4 = new Rectangulo(11,50.0,9.0);
        Rectangulo rectangulo5 = new Rectangulo(12,40.0,9.0);


        List<Rectangulo> listaPiezas = new ArrayList<Rectangulo>();
        listaPiezas.add(rectangulo1);
        listaPiezas.add(rectangulo2);
        listaPiezas.add(rectangulo3);
        listaPiezas.add(rectangulo4);
        listaPiezas.add(rectangulo5);


        Genetico genetico = new Genetico(listaStock,listaPiezas);
        Cromosoma cromosoma = genetico.realizarAlgoritmo(15,(float) 0.5,2);
        List<Stock> stockDistribuidoLista = genetico.llenarStockDistribuido(cromosoma);
        System.out.println("SOL: "+cromosoma.getListaGenes());

        for(Stock stockDistribuido: stockDistribuidoLista){
            System.out.print("Stock Ancho: "+stockDistribuido.getAncho()+" Stock Alto: "+stockDistribuido.getAlto());
            System.out.print(" Area usada: "+stockDistribuido.getBloqueUsado().getArea());
            System.out.print(" ID Nodo: "+stockDistribuido.getBloqueUsado().getIdNodo());
            System.out.println("");
        }
//        Cromosoma cromosoma = new Cromosoma();
//        List<String> listaGenes = new ArrayList<String>();
//        cromosoma.añadirGen("1");
//        cromosoma.añadirGen("5");
//        cromosoma.añadirGen("H");
//        cromosoma.añadirGen("7");
//        cromosoma.añadirGen("6");
//        cromosoma.añadirGen("V");
//        cromosoma.añadirGen("2");
//        cromosoma.añadirGen("H");
//        cromosoma.añadirGen("4");
//        cromosoma.añadirGen("H");
//        cromosoma.añadirGen("V");
//        cromosoma.añadirGen("9");
//        cromosoma.añadirGen("8");
//        cromosoma.añadirGen("V");
//        cromosoma.añadirGen("3");
//        cromosoma.añadirGen("V");
//        cromosoma.añadirGen("H");
//
//        List<String> listaGenes2 = cromosoma.getListaGenes();
//        listaGenes2.remove(0);
//        for(String valor : cromosoma.getListaGenes()){
//            System.out.print(valor+", ");
//        }

//
//        List<Stock> listaStock = new ArrayList<Stock>();
//        for(int i=0;i<10;i++){
//            Stock stock = new Stock();
//            stock.setAlto(5);
//            stock.setAncho(5);
//            stock.setArea(new Double(5*5));
//            listaStock.add(stock);
//        }
//        List<Stock> listaStockDistribuido = genetico.algoritmoDistribucionStocks(cromosoma,listaStock);
//
//        for(Stock stock: listaStockDistribuido){
//            System.out.println(stock.getBloqueUsado().getIdNodo()+"/");
//        }



//        cromosoma.crearArbol();
//        List<Nodo> listaNodos = cromosoma.obtenerListaNodosArbol();
//        for(Nodo valor : listaNodos){
//            System.out.print(valor.getIdNodo()+", ");
//        }


        //genetico.realizarAlgoritmo(1,1,1);

//        List<Integer> listaPrubea = new ArrayList<Integer>();
//        listaPrubea.add(1);
//        listaPrubea.add(2);
//        listaPrubea.add(3);
////        System.out.println("-----------");
////        for(Integer valor : listaPrubea){
////            System.out.println(valor+", ");
////        }
//        listaPrubea.remove(1);
////        System.out.println("-----------");
////        for(Integer valor : listaPrubea){
////            System.out.println(valor+", ");
////        }
//        listaPrubea.add(1,45);
//
////        System.out.println("-----------");
////        for(Integer valor : listaPrubea){
////            System.out.println(valor+", ");
////        }
//
//        System.out.println("It works");
//
//
//        List<Integer> listaNueva = new ArrayList<>();
//        listaNueva.add(87);
////        System.out.println("-----------");
////        for(Integer valor : listaNueva){
////            System.out.println(valor+", ");
////        }
//
//        listaNueva = listaPrubea;
//        System.out.println("-----------");
//        for(Integer valor : listaNueva){
//            System.out.println(valor+", ");
//        }
//
//        try{
//            int randomInt = (new Random()).nextInt(listaNueva.size());
//            System.out.println("Random int: "+randomInt);
//            Integer elemento =  listaNueva.get(randomInt);
//            listaNueva.remove(elemento);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        System.out.println("-----------");
//        for(Integer valor : listaNueva){
//            System.out.println(valor+", ");
//        }
//
//        try{
//            int randomInt = (new Random()).nextInt(listaNueva.size());
//            System.out.println("Random int: "+randomInt);
//            Integer elemento =  listaNueva.get(randomInt);
//            listaNueva.remove(elemento);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        System.out.println("-----------");
//        for(Integer valor : listaNueva){
//            System.out.println(valor+", ");
//        }

    }
}
