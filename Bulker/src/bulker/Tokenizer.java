/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bulker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Tokenizer {
       
  private final String separator = ",";  
    public Tokenizer(){
    CreateHashSetStopWords();
    }
    
    public HashSet<String> getTerms(String token){
        
        //Three Steps.
        String cleanToken = ClearToken(token);
        List<String> dividedToken = DivideToken(cleanToken);
        HashSet<String> Terms = DeleteStopWords(dividedToken);
        
        return Terms;
    }
     
    /** ClearToken 
  * First: Removes special characters to the input 
  * Second: Prepares for split token adding a comma between each word.*/ 
    private String ClearToken(String s) {
    String cleanToken = s;
//   for (char ch: "@#$%^?;&*=`():<>~\"][!+{}".toCharArray()) {
//       cleanToken =  cleanToken.replace (Character.toString(ch),"");
//    }
      cleanToken = cleanToken.replaceAll("[^a-zA-Z0-9\\.\\'\\-\\/\\ ]", ""); 
   for (char ch: ".'-/\\_".toCharArray()) {
       cleanToken =  cleanToken.replace (Character.toString(ch),separator);
    }
    return cleanToken;
}

/** DivideToken detects if a token has a separator 
 * and splits in two or more tokens */ 
private List<String> DivideToken(String s) {
   List<String> partsToken = new ArrayList<>();
     if(s.contains(separator)){
         partsToken = Arrays.asList(s.split(separator));
      }
     else{
         partsToken.add(s);
     }
    return partsToken;
    }

private HashSet<String> DeleteStopWords(List<String> dividedStopWords){
    HashSet<String> hsLS = new HashSet<>();
      
    for (String word : dividedStopWords) {
        if(!word.isBlank() || !word.isEmpty())
          {
           String wordLowerCase = word.toLowerCase();
            if(!hsSW.contains(wordLowerCase)) // Is a stopWord?
            {
              hsLS.add(wordLowerCase);
            } 
        }
    }
    return hsLS;
}


private static HashSet<String> hsSW = new HashSet<String>();
private void CreateHashSetStopWords()
{
    int len= sw.length;
    for(int i=0;i<len;i++){
        hsSW.add(sw[i]);
    }
}
//Stop Words...It´ll be okey if reads this words from a file.
private static String[] sw = {"a", "able", "about",
        "across", "after", "all", "almost", "also", "am", "among", "an",
        "and", "any", "are", "as", "at", "b", "be", "because", "been",
        "but", "by", "c", "can", "cannot", "could", "d", "dear", "did",
        "do", "does", "e", "either", "else", "ever", "every", "f", "for",
        "from", "g", "get", "got", "h", "had", "has", "have", "he", "her",
        "hers", "him", "his", "how", "however", "i", "if", "in", "into",
        "is", "it", "its", "j", "just", "k", "l", "least", "let", "like",
        "likely", "m", "may", "me", "might", "most", "must", "my",
        "neither", "n", "no", "nor", "not", "o", "of", "off", "often",
        "on", "only", "or", "other", "our", "own", "p", "q", "r", "rather",
        "s", "said", "say", "says", "she", "should", "since", "so", "some",
        "t", "than", "that", "the", "their", "them", "then", "there",
        "these", "they", "this", "tis", "to", "too", "twas", "u", "us",
        "v", "w", "wants", "was", "we", "were", "what", "when", "where",
        "which", "while", "who", "whom", "why", "will", "with", "would",
        "x", "y", "yet", "you", "your", "z"};

}
