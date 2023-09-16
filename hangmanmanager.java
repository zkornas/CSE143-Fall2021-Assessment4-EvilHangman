// Zacharia Kornas
// TA: Kent Zeng
// The HamngmanManager manages a game of EvilHangman where the player
// must attempt to guess a constanatly changing word.
import java.util.*;

public class HangmanManager {
   private int guessesLeft;
   private Set<String> currentWords;
   private Set<Character> charsGuessed;
   private String currentPattern;
   
   // Constructs a new HangmanManger
   // Parameters:
   //    dictionary - a collection of possible words for the player to guess.
   //    length - the desired length of the word the player will guess.
   //    max - the maximum number of guesses the player can make.
   // Throws:
   //    IllegalArgumentException - if the length of the word to guess is less than 1.
   //                             - if the max guess is less than 0.
   public HangmanManager(Collection<String> dictionary, int length, int max){
      if(length < 1 || max < 0){
         throw new IllegalArgumentException();
      }
      guessesLeft = max;
      charsGuessed = new TreeSet<>();
      currentWords = new TreeSet<>();
      for(String s : dictionary){
         if(s.length() == length){
            currentWords.add(s);
         }
      }
      String emptyGuess = "-";
      for(int i = 2; i <= length; i++){
         emptyGuess += " -";
      }
      currentPattern = emptyGuess;
   }
   
   // Returns a Set of the current possible words.
   public Set<String> words(){
      return currentWords;
   }
   
   // Returns how many guesses the player has left.
   public int guessesLeft(){
      return guessesLeft;
   }
   
   // Returns a Set of the characters that the player has already guessed
   public Set<Character> guesses(){
      return charsGuessed;
   }
   
   // Returns the current pattern
   // Throws:
   //    IllegalStateException - if there are no possible words.
   public String pattern(){
      if(words().isEmpty()){
         throw new IllegalStateException();
      }
      return currentPattern;
   }
   
   // Modifies the state of the game by updating the list of guessed characters
   // made by the player to include the new guess, if that guess was not already made. 
   // Updates the current pattern to match the largest set of possible words being considered.
   // If the guessed character is not in the updated pattern,
   // then the number of guesses left for the player to make decreases by 1.
   // Returns number of times the guessed character appeared in the new pattern
   // Throws:
   //    IllegalStateException - if the player has less than 1 guess left.
   //                          - if there are no possible set of words.
   //    IllegalArgumentException - if the player already made the same guess before.
   // Parameters:
   //    guess - the character that the player guessed.
   public int record(char guess){
      if(guessesLeft < 1 || currentWords.isEmpty()){
         throw new IllegalStateException();
      } else if(charsGuessed.contains(guess)){
         throw new IllegalArgumentException();
      }
      charsGuessed.add(guess);
      Map<String, Set<String>> wordFam = wordFamilies(guess);
      currentPattern = patternFinder(wordFam);; 
      currentWords = wordFam.get(currentPattern);
      return charCounter(guess);
   }

   // Helper method that creates and returns a Map of patterns and
   // word families based on the player's guess
   // Parameters:
   //    guess - the character that the player guessed.
   private Map<String, Set<String>> wordFamilies(char guess){
      Map<String, Set<String>> wordFam = new TreeMap<>();
      for(String word : currentWords){
         String pattern = currentPattern;
         for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) == guess){
               pattern = pattern.substring(0, (i * 2)) + guess 
               + pattern.substring((i * 2) + 1);
            }
         }
         if(!wordFam.containsKey(pattern)){ 
            wordFam.put(pattern, new TreeSet<String>());
         }
         (wordFam.get(pattern)).add(word);
      }
      return wordFam;
   }

   // Helper method that finds and returns the pattern with
   // the most possible words to use going forward.
   // Returns the pattern with the most possible words
   // Parameters:
   //    wordFam - Map of possible patterns and words based 
   //              on the users previous guess
   private String patternFinder(Map<String, Set<String>> wordFam){
      int mostWords = 0;
      String newPattern = "";
      for(String pattern : wordFam.keySet()){
         int count = wordFam.get(pattern).size();
         if(count > mostWords){
            newPattern = pattern;
            mostWords = count;
         }
      }
      return newPattern;
   }

   // Helper method that counts the number of times the character
   // that the player guessed occurs in the pattern
   // Returns the number of times the guess occurs in patter
   // Parameters:
   //    guess - the character that the player guessed.
   private int charCounter(char guess){
      int counter = 0;
      for(int i = 0; i < currentPattern.length(); i++){
         char curr = currentPattern.charAt(i);
         if(curr == guess){
            counter++;
         }
      }
      if(currentPattern.indexOf(guess) == -1){
         guessesLeft--;
      }
      return counter;
   }
}
