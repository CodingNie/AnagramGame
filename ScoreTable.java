import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   using a given score panel and store all of the available words getting from the rack and together with their scores
   in a map. This class provide the interface for getting all of the <string, score> pair for the caller WordFinder use.
 */
public class ScoreTable {
    //using a map to store all the scores of the words on a given rack (including a given dictionary)
    private Map<String, Integer> allScores;
    //the following is the given score of each character. each index represents the character : index + 'a'
    private static int[] SCORE_TABLE = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4,
            8, 4, 10};
    /**
     this constructor is to produce a scoretable of the given rack.
     we get all the words from the interface of the rack and compute the score individually, and store them into a map
     since the character of the lower case and the upper case has the same score, we can transform the uppercase to
     lower case temporarily in counting
     */
    public ScoreTable(Rack rack){
        ArrayList<String> allWords = rack.getAllRackWords();
        allScores = new HashMap<>();
        for (String word : allWords){
            // transform to lower case for calculation, since the credit of the letter is the same
            String wordLower = word.toLowerCase();
            int score = 0;
            char[] array = wordLower.toCharArray();
            for(char c : array){
                score += SCORE_TABLE[c - 'a'];
            }
            allScores.put(word, score);
        }
    }

    // the interface of this class is to provide all the <word, score> in pair
    public Map<String, Integer> getAllScores(){
        return this.allScores;
    }
}
