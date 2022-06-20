// Name: 
// USC NetID: 
// CS 455 PA4
// Spring 2022

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   A Rack of Scrabble tiles.
   Use the characters in a given rack to find all the available words from the given dictionary, and store all the
   available words in an ArrayList<string> through the constructor. This class provide the interface for getting all the
   available words in a given rack, which is invoked usually by the ScoreTable.
 */

public class Rack {
   /**
    the input is the normal string input as a Rack simulation
    the allRackWords is to store all the available words in a given dictionary
    */
   private String input;
   private ArrayList<String> allRackWords;

   /**
    The constructor is to take the input string and the certain dictionary as the argument
    Using the input to find all the subset strings through the getAllSubsets function, through the following anagrams
    finding method, we can construct all of the available words.
    we use the field "allRackWords" to store them.
    */
   public Rack(String input, AnagramDictionary dictionary){
      this.input = input;
      ArrayList<String> subsetList = getAllSubsets(input);
      ArrayList<String> result = new ArrayList<>();
      for (String s : subsetList){
         ArrayList<String> cur = dictionary.getAnagramsOf(s);
         if (cur != null){
            for (String element : cur){
               result.add(element);
            }
         }

      }
      this.allRackWords = result;
   }

   /**
    This function is a requested wrapper method to call the method "allSubset", in order to find all the subset from
    the input normal string. Inside the function, we transform the input to the into the two parallel things,
    the "unique" string and the "mult" array, following by passing through them into the defined function "allSubsets".
    It returns the list of all subsets of the input string, which represents the Rack.
    */
   private static ArrayList<String> getAllSubsets(String input){
      Map<Character, Integer> mapUnique = new HashMap<>();
      char[] arrInput = input.toCharArray();
      StringBuilder sb = new StringBuilder();
      // put the element and their frequency into a map;
      for (char element : arrInput){
         Integer val = mapUnique.get(element);
         if (val != null){
            mapUnique.put(element, val + 1);
         }else{
            mapUnique.put(element, 1);
         }
      }
      // the size of the mult should be at least as big as the "unique" string
      int[] mult = new int[mapUnique.size()];
      int i = 0; // pointer of the array when filling in with the number
      // filling in with the number corresponding to each character
      for (Map.Entry<Character, Integer> entry : mapUnique.entrySet()){
         sb.append(entry.getKey());
         mult[i++] = entry.getValue();
      }
      String unique = sb.toString();
      return allSubsets(unique, mult, 0);
   }
   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
      each subset is represented as a String that can have repeated characters in it.
      @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }

   /**
    This is the interface we provide the available rack words to caller
    */
   public ArrayList<String> getAllRackWords(){
      return this.allRackWords;
   }
}
