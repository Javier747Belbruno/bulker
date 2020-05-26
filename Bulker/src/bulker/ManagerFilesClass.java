/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


/**
 *
 * @author Javier
 */
public class ManagerFilesClass {
    
  
     
     public void ProcessFiles(File[] f) throws FileNotFoundException, Exception{
         
        FileClass fc = new FileClass();

        String nombreBasedeDatos = "prueba";
        
        String sb = "use "+nombreBasedeDatos +" ;";
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm");
        System.out.println(  );
        for (File file : f) {
             try {
               HashMap<String,Integer> terms = fc.readFileAndReturnTerms(file.getCanonicalPath());
               String nameDocument = file.getName();
               
               sb += "\n\n/*Documento "+nameDocument+"*/\n\n START TRANSACTION;\n\n "
                       + "CALL getIdDocumento(\""+nameDocument+"\",@id_documento);\n\n";

               for (String key : terms.keySet()) {
                     sb += "CALL insertPosting(@id_documento,\""+key+"\","+ terms.get(key)+");\n";
               }
               
               sb += "COMMIT;\n\n";

               System.out.println("Document processed " + nameDocument + "..." + java.time.LocalDateTime.now()); 
               
               //MoveFile To processedFiles.
               moveFile("documents/" +file.getName(), "documents/processedFiles/" + file.getName());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
             
             //CREAR SCRIPT SQL
             try (PrintWriter out = new PrintWriter("documents/processedFiles/scriptSP_cantDocs-"+f.length+"_"+sdf.format(cal.getTime())+".sql")) {
                out.println(sb);
              }
           
        }
    
    }
     
  public void ProcessFilesOther(File[] f) throws FileNotFoundException, Exception{
         
        FileClass fc = new FileClass();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm");
        
         try(FileWriter fw = new FileWriter("documents/processedFiles/scriptSP_cantDocs-"+f.length+"_"+sdf.format(cal.getTime())+".sql", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outPw = new PrintWriter(bw))
            {
                 String nombreBasedeDatos = "prueba";
                String sb1 = "use "+nombreBasedeDatos +" ;";
                
                outPw.println(sb1);
              
           for (File file : f) {
             try {
               HashMap<String,Integer> terms = fc.readFileAndReturnTerms(file.getCanonicalPath());
               String nameDocument = file.getName();
               
               String sb2 = "\n\n/*Documento "+nameDocument+"*/\n\n START TRANSACTION;\n\n "
                       + "CALL getIdDocumento(\""+nameDocument+"\",@id_documento);\n\n";

               for (String key : terms.keySet()) {
                     sb2 += "CALL insertPosting(@id_documento,\""+key+"\","+ terms.get(key)+");\n";
               }
               
               sb2 += "COMMIT;\n\n";
               outPw.println(sb2);
                
                
               System.out.println("Document processed " + nameDocument + "..." + java.time.LocalDateTime.now()); 
               
              
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
              //MoveFile To processedFiles.
               //moveFile("documents/" +file.getName(), "documents/processedFiles/" + file.getName());
        }

            } catch (IOException e) {
                e.getMessage();
            }

    }
    
      //CHECK IF EXISTS FILE TO PROCESS.
    public File[] SearchDocuments()throws IOException, Exception {
            

        File directory = new File("documents");

            if (! directory.exists()){
                directory.mkdir();
               throw new Exception("Agregue documentos en la carpeta /documents");
            }

        
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };
        File[] files = directory.listFiles(textFilter);
        return files;
    }
    
     private void moveFile(String src, String dest ) throws Exception {
      Path result = null;
      try {
         File directory = new File("documents/processedFiles");

            if (! directory.exists()){
                directory.mkdir();
                System.out.println("Creada carpeta documents/processedFiles");
            }
         result =  Files.move(Paths.get(src), Paths.get(dest),StandardCopyOption.REPLACE_EXISTING);
         
      } catch (IOException e) {
         System.out.println("Exception while moving file: " + e.getMessage());
      }
      if(result != null) {
         System.out.println("File moved successfully.");
      }else{
         System.out.println("File movement failed.");
      }  
   }
     

    
    
}
