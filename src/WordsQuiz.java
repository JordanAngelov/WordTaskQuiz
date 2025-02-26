import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class WordsQuiz {

    public static List<String> loadAllWords() throws IOException {
        URL wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wordsUrl.openConnection().getInputStream()))){
            return bufferedReader.lines().skip(2).collect(Collectors.toList());
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> words = loadAllWords();

        List<String> nineLetterWords = words.stream().filter(word -> word.length() == 9).toList();

        HashSet<String> lessThanTenLetterWords = words.stream().filter(word -> word.length() < 10).collect(Collectors.toCollection(HashSet::new));
        long startTime = System.currentTimeMillis();
        List<String> validWords = new ArrayList<>();
        for(String word: nineLetterWords){
            if(checkIfWordValid(word,lessThanTenLetterWords)){
                validWords.add(word);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(validWords);
        System.out.println(validWords.size());
        System.out.println("Time taken in milli seconds: " + (endTime - startTime));
    }

    private static boolean checkIfWordValid(String word,HashSet<String> lessThanTenLetterWords) {
        if(word.length() == 1 && (word.charAt(0) == 'A' || word.charAt(0) == 'I')){
            return true;
        }
        if(!lessThanTenLetterWords.contains(word)) {
            return false;
        }
        for(int i = 0;i < word.length(); i++){
            if(checkIfWordValid(word.substring(0,i) + word.substring(i + 1), lessThanTenLetterWords)){
                return true;
            }
        }
        return false;
    }
}
