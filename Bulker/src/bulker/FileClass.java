/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Javier
 */
public class FileClass {
   public HashMap<String,Integer> readFileAndReturnTerms(String filename) throws IOException {
      
    HashMap<String,Integer> terms = new HashMap<>(); //What I expect from this method.
    Tokenizer t = new Tokenizer();
    Scanner s = new Scanner(new File(filename));    
    while(s.hasNext ())
    {
      HashSet<String> TermsForEachRow = t.getTerms(s.next());
      TermsForEachRow.forEach((string) -> {
          
                if(terms.containsKey(string)){
                    terms.replace(string, terms.get(string)+1); //update word freq.
                }else{
                terms.put(string,1); //add word in hashtable.
                        } 
      });
    }
   return terms; 
 }
 
}
