package com.mycompany.pacman;

import java.util.Scanner;
import java.util.Random;

public class Pacman {

    public static void main(String[] args) {
        //Atributos iniciales
        Scanner scanner = new Scanner(System.in);
        int optionSelected = 0;
        int premios, paredes, fantasmas;
        int [] puntuacion = new int[10];
        int contUsuarios = 0;
        String [] usuario = new String[10];
        String tipoTablero;
        boolean enPartida = false;
        
        
        //Inicio del programa
        do {
            printMenu();
            optionSelected = selectOption(scanner);
            //Seleccion del menu
            switch(optionSelected){
                //--------------------------Configuracion partida
                case 1 -> {
                limpiarPantalla();
                System.out.println("=====Configuracion tablero = ======");
                System.out.print("Ingrese nombre de usuario: ");
                usuario[contUsuarios] = scanner.next();
                System.out.println("Ingrese los siguientes valores: "); 
                System.out.print("Tipo de tablero (Grande:G, Pequeno:P): ");
                tipoTablero = scanner.next();
                while (!tipoTablero.equals("P") && !tipoTablero.equals("G")){
                    System.out.println("Error: tablero invalido");
                    System.out.print("Tipo de tablero (Grande:G, Pequeno:P): ");
                    tipoTablero = scanner.next();
                }
                System.out.print("PREMIOS[1-40]: ");
                premios = scanner.nextInt();
                while (premios>40 || premios<1){
                    System.out.println("Error: Cantidad fuera de el limite");
                    System.out.print("PREMIOS[1-40]: ");
                    premios = scanner.nextInt();
                }
                System.out.print("PAREDES[1-20]: ");
                paredes = scanner.nextInt();
                while (paredes>20 || paredes<1){
                    System.out.println("Error: Cantidad fuera de el limite");
                    System.out.print("PAREDES[1-20]: ");
                    paredes = scanner.nextInt();
                }
                System.out.print("FANTASMAS[10-20]: ");
                fantasmas = scanner.nextInt();
                while (fantasmas>20 || fantasmas<10){
                    System.out.println("Error: Cantidad fuera de el limite");
                    System.out.print("FANTASMAS[10-20]: ");
                    fantasmas = scanner.nextInt();
                }
                System.out.println("================");
                //Inicion del juego
                limpiarPantalla();
                String[][] tablero = generarTablero(tipoTablero, premios, paredes, fantasmas); 
                enPartida = true;
                imprimirTablero(tablero);
                tablero = ubicacionInicial(tablero, tipoTablero, scanner);
                imprimirTablero(tablero);//Generacion de tablero
                int cont = contPremios(tablero); //Conteo de premios
                int vidas = 3;
                //Inicio partida
                while(enPartida == true){
                int [] ubicacionActual = ubicacion(tablero);//Registra ubicacion actual de pacman
                System.out.println("Movimiento: ");
                String entrada = scanner.next();//Ingresar movimiento
                while (!entrada.equals("F") && !entrada.equals("4") && !entrada.equals("5") && !entrada.equals("8") && !entrada.equals("6")){
                    System.out.println("Error: Entrada invalida");
                    System.out.println("Movimiento: ");
                    entrada = scanner.next();
                }
                //Pausa
                if(entrada.equalsIgnoreCase("F")){
                    enPartida = menuPausa(scanner);//Regresa valor verdaderp o falso para enPartida
                    limpiarPantalla();
                    imprimirTablero(tablero);
                    continue;
                }
                int mov = Integer.parseInt(entrada);//Si no es pausa covierte a entero
                int caso = colision(ubicacionActual, tablero, mov);//Registra diferentes tipos de colicion
                int caso2;
                switch(caso){//Cada uno de los casos de colision
                    case 1:
                        //Premios simples
                        puntuacion[contUsuarios] = puntuacion[contUsuarios] + 10;
                        cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 2: 
                        //Premios especiales
                        puntuacion[contUsuarios] = puntuacion[contUsuarios] + 15;
                        cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 3:
                        //Fantasmas
                        vidas = vidas - 1;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 4:
                        //Pared
                        break;
                        //
                    case 5:
                        //Pared superior o inferior
                        //Mueve la ubicacion al lado contrario de su movimiento 
                        if (mov == 8)ubicacionActual[0] = tablero.length-1 ;
                        else if (mov == 5)ubicacionActual[0] = 0 ;
                        caso2 = colision(ubicacionActual, tablero, mov);
                        switch(caso2){//Casos para colision al momento de el cambio de ubicacion
                            case 1:puntuacion[contUsuarios] = puntuacion[contUsuarios] + 10;
                            cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);//Realiza el movimiento y actualiza la ubicacion
                        break;
                    case 2: 
                        puntuacion[contUsuarios] = puntuacion[contUsuarios] + 15;
                        cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 3:
                        vidas = vidas - 1;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 4:
                        //Pared: Al contacto no cambia de posicion
                        break;
                    default: 
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                        }
                        break;
                        // Pared inferior
                    case 6: 
                        //Pared izquierda o derecha
                        if (mov == 4)ubicacionActual[1] = tablero[0].length-1 ;//Cambio de posicion
                        else if (mov == 6)ubicacionActual[1] = 0 ;
                        caso2 = colision(ubicacionActual, tablero, mov);
                        switch(caso2){
                            case 1:puntuacion[contUsuarios] = puntuacion[contUsuarios] + 10;
                            cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 2: 
                        puntuacion[contUsuarios] = puntuacion[contUsuarios] + 15;
                        cont--;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 3:
                        vidas = vidas - 1;
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                    case 4:
                        //Pared normal
                        break;
                    default: 
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                        }
                        break;
                    default: 
                        tablero = movimiento(tablero, ubicacionActual, mov);
                        break;
                        }
                //---------------------------------------------------
                if (vidas == 0 || cont == 0){
                    //Se indica si quedan vidas o se tomaron todos los premios  
                    int nada = 0;
                    System.out.println("=====================");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("GAME OVER");
                    System.out.println("Puntuacion: "+puntuacion[contUsuarios]);
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("");
                    System.out.println("=====================");
                    System.out.println("Ingrese cualquier numero para continuar: ");
                    nada = scanner.nextInt();
                    enPartida = false;// Sale de la partida
                    limpiarPantalla();
                }
                //Se indican datos y se reinicia el juego
                 System.out.println("=====================");
                 System.out.println("Usuario: "+usuario[contUsuarios]);
                 System.out.println("Puntuacion: "+puntuacion[contUsuarios]);
                 System.out.println("Vidas: "+vidas);
                 imprimirTablero(tablero);
                 
                }
                contUsuarios++;
                }
                //----------------------------------------------------
                case 2 -> {//Se recorren el arreglo de punteo y usuarios
                    limpiarPantalla();
                    int contador=1;
                    System.out.println("=============HISTORIAL DE PARTIDAS============");
                    System.out.printf("%-5s %-15s %-10s%n", "No.", "Usuario", "Puntuacion");
                        for (int k = usuario.length-1; k>=0; k--) {
                            if (usuario[k] != null) {
                                System.out.printf("%-5d %-15s %-10d%n",
                                contador,
                                usuario[k],
                                puntuacion[k]);
                                contador++;
                            }
                        }
                    System.out.println("=============================================");
                    System.out.println("Ingrese cualquier numero para continuar: ");
                    int nada = scanner.nextInt();
                    limpiarPantalla();
                    
                        
                }
            }
        } while (optionSelected != 3);

        scanner.close();
        limpiarPantalla();
        System.out.println("Gracias Por Jugar :)");
        System.out.println("Por: Gabriel Sales");
    }

    public static int selectOption(Scanner sc) {
        int opcion = sc.nextInt();
        return opcion;
    }

    public static void printMenu() {//Imprime el menu principal
        System.out.println("");
        System.out.println("==== MENU DE INICIO ====");
        System.out.println("1. Iniciar juego");
        System.out.println("2. Historial de partidas");
        System.out.println("3. Salir");
        System.out.println("========================");
        System.out.print("=> ");
    }
    
    public static void limpiarPantalla(){//Limpia la pantalla
        for (int i= 0; i<5; i++ ){
            System.out.println("");}
    }
    
    public static String[][] generarTablero(String tamaño, int premios, int paredes, int fantasmas){
        //Genera el tablero dependiendo del tamaño con numeros al azar
        Random rand = new Random();
        //Tablero grande --------------------------
        if (tamaño.equals("G")){
            String[][] matriz = new String[12][12];
            int probabilidad;
            
            //Generar paredes
            for(int k = 0; k < matriz.length; k++){
                matriz[0][k] = "-"; 
                matriz[matriz.length-1][k] = "-";
            }
            for(int k = 1; k < matriz.length-1; k++){
                matriz[k][0] = "|"; 
                matriz[k][matriz[0].length-1] = "|";
            }
            
            
            //Espacios vacios
            for (int i = 1; i < matriz.length - 1; i++) {
                for (int j = 1; j < matriz[i].length - 1; j++) {
                         matriz[i][j] = " ";
                }
            }
             //Premios
            while(premios > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            probabilidad = rand.nextInt(11);
                            if (probabilidad == 5){
                                matriz[i][j] = "$";
                            }
                            else {
                                matriz[i][j] = "0";
                            }
                            premios--;
                        }  
                    }  
                }
            }
            //Paredes
            while(paredes > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            matriz[i][j] = "X";
                            paredes--;
                        }  
                    }  
                }
            }
            //Fantasma
            while(fantasmas > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            matriz[i][j] = "@";
                            fantasmas--;
                        }  
                    }  
                }
            }
            return matriz;
        }
        
        else if (tamaño.equals("P")){
        premios = (int)(30*(premios*0.01));
        paredes = (int)(30*(paredes*0.01));
        fantasmas = (int)(30*(fantasmas*0.01));
        String[][] matriz = new String[7][8];
        int probabilidad;
            
            //Generar paredes
            for(int k = 0; k < matriz[0].length; k++){
                matriz[0][k] = "-"; 
                matriz[matriz.length-1][k] = "-";
            }
            for(int k = 1; k < matriz.length-1; k++){
                matriz[k][0] = "|"; 
                matriz[k][matriz[0].length-1] = "|";
            }
            
            
            //Espacios vacios
            for (int i = 1; i < matriz.length - 1; i++) {
                for (int j = 1; j < matriz[i].length - 1; j++) {
                         matriz[i][j] = " ";
                }
            }
             //Premios
            while(premios > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            matriz[i][j] = "0";
                            premios--;
                        }  
                    }  
                }
            }
            //Paredes
            while(paredes > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            matriz[i][j] = "X";
                            paredes--;
                        }  
                    }  
                }
            }
            //Fantasma
            while(fantasmas > 0){
                for (int i=1;i < matriz.length-1; i++){
                    for (int j=1;j < matriz[0].length-1; j++){
                        probabilidad = rand.nextInt(30);
                        if(matriz[i][j].equals(" ")&& probabilidad == 6){
                            matriz[i][j] = "@";
                            fantasmas--;
                        }  
                    }  
                }
            }
            return matriz;
        }
        
        
        return null;
    }
    
    public static void imprimirTablero(String[][] tablero){
        for(int i = 0; i< tablero.length; i++){
                    for(int j = 0; j< tablero[i].length; j++){
                        System.out.print(tablero[i][j]);
                    }
                    System.out.println();
                }
    }
    
    public static String[][] ubicacionInicial(String[][] tablero, String tipoTablero, Scanner sc){
        boolean inGame = false;
        while(inGame == false){
        System.out.println("Indique la ubicacion de inicio:");
        if(tipoTablero.equals("G")){
            System.out.println("Fila (1-10): ");
            int i = sc.nextInt();
            while (i>10 || i<1){
                    System.out.println("Error: Ubicacion no valida");
                    System.out.println("Fila (1-10): ");
                    i = sc.nextInt();
                }
            System.out.println("Columna (1-10)");
            int j = sc.nextInt();
            while (j>10 || j<1){
                    System.out.println("Error: Ubicacion no valida");
                    System.out.println("Columna (1-10)");
                    j = sc.nextInt();
                    }
            if(tablero[i][j].equals(" ")){
                tablero[i][j] = "<";
                inGame = true; 
            }
            else{System.out.println("Error: Espacio ocupado");}
        }
        else if(tipoTablero.equals("P")){
            System.out.println("Fila (1-5): ");
            int i = sc.nextInt();
            while (i>5 || i<1){
                    System.out.println("Error: Ubicacion no valida");
                    System.out.println("Fila (1-5): ");
                    i = sc.nextInt();
                }
            System.out.println("Columna (1-6): ");
            int j = sc.nextInt();
            while (j>6 || j<1){
                    System.out.println("Error: Ubicacion no valida");
                    System.out.println("Columna (1-6): ");
                    j = sc.nextInt();
                    }
            if(tablero[i][j].equals(" ")){
                tablero[i][j] = "<";
                inGame = true; 
            }
            else{System.out.println("Error: Espacio ocupado");}
        }
        }
        return tablero; 
        }
    
    public static int[] ubicacion(String[][] tablero){//Registra la ubicacion actual
        int tempi = 0;
        int tempj = 0; 
        //Buscar ubicacion actual
        for (int i = 1; i<tablero.length-1; i++ ){
            for (int j = 1; j<tablero[i].length-1; j++ ){
                if(tablero[i][j].equals("<")){
                    tempi = i;
                    tempj = j;         
                }
            }
        }
        int[] matriz = new int[2]; 
        matriz[0] = tempi;
        matriz[1] = tempj;
        return matriz; 
        
    }
    
    public static int colision(int[] ubicacionActual, String[][] tablero, int mov){
        //Registra colisiones antes del movimiento 
        int tempi = ubicacionActual[0];
        int tempj = ubicacionActual[1];
        if(mov == 8){
            switch (tablero[tempi-1][tempj]) {
                case "0":
                    return 1;
                case "$":
                    return 2;
                case "@":
                    return 3;
                case "X":
                    return 4;
                case "-":
                    return 5;
                case "|":
                    return 6;
                default:
                    break;
            }
        }
        if(mov == 5){
            switch (tablero[tempi+1][tempj]) {
                case "0":
                    return 1;
                case "$":
                    return 2;
                case "@":
                    return 3;
                case "X":
                    return 4;
                case "-":
                    return 5;
                case "|":
                    return 6;
                default:
                    break;
            }
        }
        if(mov == 6){
            switch (tablero[tempi][tempj+1]) {
                case "0":
                    return 1;
                case "$":
                    return 2;
                case "@":
                    return 3;
                case "X":
                    return 4;
                case "-":
                    return 5;
                case "|":
                    return 6;
                default:
                    break;
            }
        }
        if(mov == 4){
            if(tempj - 1 < 0) return 5;
            switch (tablero[tempi][tempj-1]) {
                case "0":
                    return 1;
                case "$":
                    return 2;
                case "@":
                    return 3;
                case "X":
                    return 4;
                case "-":
                    return 5;
                case "|":
                    return 6;
                default:
                    break;
            }
        }   
        return 0;
    }
    
    public static String[][] movimiento(String[][] tablero, int[]ubicacion, int mov){
        //Realiza los cambios en las ubicaciones
        int x = ubicacion[0];
        int y = ubicacion[1];
        if(mov == 8){
            //Arriba
            if(x != tablero.length-1)tablero[x][y] = " ";
            else tablero[1][y] = " ";
            tablero[x-1][y] = "<";
            return tablero;
        }
        else if(mov == 5){
            //Abajo
            if(x != 0)tablero[x][y] = " ";
            else tablero[tablero.length-2][y] = " ";
            tablero[x+1][y] = "<";
            return tablero;
        }
        else if(mov == 6){
            //Derecha
            if(y != 0)tablero[x][y] = " ";
            else tablero[x][tablero[0].length-2] = " ";
            tablero[x][y+1] = "<";
            return tablero;
        }
        else if(mov == 4){
            //Izquierda
            if(y != tablero[0].length-1)tablero[x][y] = " ";
            else tablero[x][1] = " ";
            tablero[x][y-1] = "<";
            return tablero; 
        }
        return null; 
    }
    
    public static boolean menuPausa(Scanner sc){//Menu de pausa
        boolean inGame = true;
        int respuesta = 0;
        System.out.println("========PAUSA==========");
        System.out.println("Desea continuar?");
        System.out.println("1.Regresar");
        System.out.println("2.Terminar partida");
        respuesta = sc.nextInt();
        if(respuesta == 2){
            inGame=false;
            limpiarPantalla();
            return inGame;
        }
        else{return inGame;}
    }
    
    public static int contPremios(String[][] tablero){
        //Registra el numer total de premios para terminar el juego
        int cont = 0; 
        for (int i=0; i<tablero.length-1; i++){
            for (int j=0; j<tablero[i].length-1; j++){
                if(tablero[i][j].equals("0") || tablero[i][j].equals("$")){
                    cont++;
                }
            }
        }
        return cont;
    
    }  
}