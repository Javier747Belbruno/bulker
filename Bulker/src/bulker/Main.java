/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulker;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Javier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        int option; //Guardaremos la opcion del usuario

        while(!exit){
            
           System.out.println("1. Crear .sql");
           System.out.println("2. Salir");
            
           System.out.println("Escribe una de las opciones");
           option = input.nextInt();
            
           switch(option){
               case 1:
                   try {
                    controller();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                  
                   break;
               case 2:
                   exit=true;
                   break;
                default:
                   System.out.println("Solo números entre 1 y 2");
           }
            
       }
        
    }

    private static void controller() throws Exception {
        ManagerFilesClass mf = new ManagerFilesClass();
        Scanner input = new Scanner(System.in);
        System.out.println("Buscando documentos en la carpeta /documents.");
        
            File[] f = mf.SearchDocuments();
            if(f.length>0);
                System.out.println("    Existen " + f.length +" documentos a procesar.");
            
            System.out.println("Desea iniciar Proceso? 0-SI - Culaquier numero para NO.");
            int number = input.nextInt();
            if(number==0){
                mf.ProcessFilesOther(f);}

      
    }
}
    

