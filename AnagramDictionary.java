// Name: 
// USC NetID: 
// CS 455 PA4
// Spring 2022

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
   A dictionary of all anagram sets. Initialize the dictionary and store them in a map of <String, ArrayList<String>>
   pair using the constructor, in which the Key is the uniformly compressed format of strings, the Value is the list
   of the corresponding anagrams. This class provide the interface for getting the list of the given string's anagram,
   which is invoked usually by the Rack class.

   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
 */
public class AnagramDictionary {

   // using a map to store all of the words by mapping a uniform pattern to a list of its all anagrams;
   // this pattern for key is a compressed string for "bbbcca" to "a1b3c2";
   private Map<String, ArrayList<String>> anaDict;

   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.
      For every string read from the dictionary file, we need to compress the string (as mentioned in the field) to a
      pattern and map them to a list of anagrams. We also need to check the duplicate element when put them in.
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
      @throws IllegalDictionaryException  if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException,
                                                    IllegalDictionaryException {
      anaDict = new HashMap<>();
      File file = new File(fileName);
      try(Scanner in = new Scanner(file)){
         while (in.hasNext()){
            String name = in.next();
            // as mentioned in the instance variable, the pattern "character + number" ("a1b3c2") used uniformly
            String patternName = compress(name);
            ArrayList<String> anaWords = anaDict.get(patternName);
            if (anaWords != null){ // add the new word into the list under this pattern
               if (anaWords.contains(name)){ // check the duplicate string in the list
                  throw new IllegalDictionaryException(name);
               }
               anaWords.add(name);
            }else{ // create a new pattern list and add the word into it
               anaWords = new ArrayList<>();
               anaWords.add(name);
            }
            anaDict.put(patternName, anaWords); // update the list
         }
      }catch(FileNotFoundException e){
         throw e;
      }catch(IllegalDictionaryException e){
         throw e;
      }
   }

   /**
    Compress the input string to the pattern of "character + number" in alphabetical order, as "dddbba" --> "a1b2d3";
    This uniform pattern will be used as the key of the map if the anagram dictionary;
    Since the efficiency of the TreeMap and the StringBuilder is better than most of the sorting method and the string
    concatenation, we implement this method using this two structure.
    */
   private String compress(String name){
      char[] nameArr = name.toCharArray();
      Map<Character, Integer> nameMap = new TreeMap<>();
      StringBuilder sb = new StringBuilder();
      // labeling and sorting the character
      for (char element : nameArr){
         Integer val = nameMap.get(element);
         if (val != null){
            nameMap.put(element, val + 1);
         }else{
            nameMap.put(element, 1);
         }
      }
      // construct the result string
      for (Map.Entry<Character, Integer> entry : nameMap.entrySet()){
         sb.append(entry.getKey());
         sb.append(entry.getValue());
      }
      return sb.toString();
   }


   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param s string to process
      @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String string) {
      String patternName = compress(string);
      return anaDict.get(patternName);
   }
}
