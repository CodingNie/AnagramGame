import java.io.FileNotFoundException;
import java.util.*;

public class WordFinder {
    /**
     main method in this class, taking file as the command-line argument, and input from the keyboard or the text file
     we pass the dictionary through the first argument, otherwise passing "sowpods.txt" file automatically
     The main function is the final body to catch the exception thrown from the initialization of the AnagramDictionary,
     which is the fileNotFound and the illegalDictionary.

     In order to deal with the special character other than letters, we need to prune the input to fit with the constructor
     of the Rack class.
     */
    public static void main(String[] args) {
        //use the "sowpods.txt" automatically if there is no special input dictionary
        String file = "sowpods.txt";
        if (args.length != 0){
            file = args[0];
        }
        //need to catch the exception thrown from the constructor of the AnagramDictionary, where the file may not exist
        // or  with duplicate elements
        try(Scanner in = new Scanner(System.in)){
            AnagramDictionary dictionary = new AnagramDictionary(file);
            System.out.println("Type . to quit.");
            System.out.print("Rack? ");
            while (in.hasNext()){
                //catch the input (may contain special character)
                String inputRaw = in.next();
                if (inputRaw.equals(".")){
                    break;
                }else{
                    // prune the input, leaving only upper or lower case of letters
                    String inputPruned = prune(inputRaw);
                    // create the Rack object with the map of all words in it
                    Rack rack = new Rack(inputPruned, dictionary);
                    // create the ScoreTable with the map of all <word, score> in it
                    ScoreTable scoretable = new ScoreTable(rack);
                    // print all of them
                    printResult(inputRaw, scoretable);
                    System.out.print("Rack? ");
                }
            }

        }catch(FileNotFoundException e){
            System.out.println("ERROR: Dictionary file \"" + file + "\" does not exist");
            System.out.println("Exiting program.");
        }catch(IllegalDictionaryException e){
            System.out.println("ERROR: Illegal dictionary: dictionary file has a duplicate word: " + e.getMessage());
            System.out.println("Exiting program.");
        }
    }

    /**
     According to the request, we can only process the normal character in the alphabet, which is limited to the 26
     uppercase letters and 26 lower case letters. We extract the letters from the raw input string and return a new
     pruned string for use
     */
    private static String prune(String input){
        StringBuilder sb = new StringBuilder();
        for(char element : input.toCharArray()){
            // here we do not consider the circumstance of the compound of the upper and lower letter case as the input
            // since the mixture is not part of the assignment request. Therefore only two simple following cases
            if ((element >='a' && element <= 'z') || (element >= 'A' && element <= 'Z')){
                sb.append(element);
            }
        }
        return sb.toString();
    }

    /**
     Use this function to print the result out, including a nested class to sort the array as requested
     The whole function contains two part: sorting and printing.
     */
    private static void printResult(String inputRaw, ScoreTable scoretable){
        //the sorting part: get the entry set of the <word, score> pair from the ScoreTable object
        Set<Map.Entry<String, Integer>> scoreset = scoretable.getAllScores().entrySet();
        //put the entry set into a list to use sorting API
        List<Map.Entry<String, Integer>> scoreList = new ArrayList<>(scoreset);
        //sort the entry in collection classes method as requested
        Collections.sort(scoreList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                if (e1.getValue() == e2.getValue()){
                    return e1.getKey().compareTo(e2.getKey());
                }else{
                    return e1.getValue() < e2.getValue() ? 1: -1;
                }
            }
        });

        // the printing part: print the entry pair with the descending order of the score, print with the alphabetical
        // order when score equals --- which is the entry order we have sorted with
        System.out.println("We can make " + scoreList.size() +" words from \"" + inputRaw + "\"");
        if (scoreList.size() != 0){
            System.out.println("All of the words with their scores (sorted by score):");
            for (Map.Entry<String, Integer> entry : scoreList){
                System.out.println(entry.getValue() + ": " + entry.getKey());
            }
        }
    }
}
