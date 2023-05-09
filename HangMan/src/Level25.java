import java.util.Random;
import java.util.Scanner;


public class Level25 {
    
    public static int gamesWon = 0;
    public static int gamesLost = 0;
    public static int gamesPlayed = 0;
    public static int totalGuesses = 0;
    public static int wrongGuesses = 0;
    public static int rightGuesses = 0;


    static boolean checkChar (char c, String str) {
            String x = Character.toString(c);
            if(str.contains(x)) {
                    return true;
            } else {
                    return false;
            }
    }


    static String getRandomWord (String array[]) {
            int allWords = array.length -1;
            Random myRandomGen = new Random();
            int random = myRandomGen.nextInt(allWords);
            return array[random];
    }


    static String getRandomWord (String array[], int len) {
            int i = 0;
            String ranWord = "";
            while(i<=500) {
                    ranWord = getRandomWord(array);
                    if(ranWord.length()>=len) {
                            return ranWord;
                    } else {
                            i++;
                    }
            }
            return null;
    }


    static String getRevealedChars (String word, String guess) {
            String allGuess = "";
            for(int i = 0; i < word.length(); i++) {
                    char cGuess = word.charAt(i);
                    if(checkChar(cGuess, guess)) {
                            allGuess = allGuess + cGuess;
                    } else {
                            allGuess = allGuess + " _ ";
                    }
            }
            return allGuess;
    }


    static void displayHangMan(int numWrong)
    {
            System.out.println();


            switch (numWrong)
            {
            case 0:
                    break;
            case 1:
                    System.out.print("        _______\n");
                    break;
            case 2:
                    System.out.print("        _______\n");
                    for (int i = 0; i < 6; i++)
                            System.out.println("|            ||");
                    break;
            case 3:
                    System.out.println("        _______");
                    for (int i = 0; i < 6; i++)
                            System.out.println("|            ||");
                    System.out.println("|________||");
                    break;
            case 4:
                    System.out.println("        _______");
                    System.out.println("|   |        ||");
                    System.out.println("|  \\O/   ||");


                    for (int i = 0; i < 4; i++)
                            System.out.println("|            ||");
                    System.out.println("|________||");
                    break;
            case 5:
                    System.out.print("        _______\n");
                    System.out.println("|   |        ||");
                    System.out.println("|  \\O/   	 ||");
                    System.out.println("|   |        ||");


                    for (int i = 0; i < 3; i++)
                            System.out.println("|            ||");
                    System.out.println("|________||");
                    break;
            case 6:
                    System.out.print("        _______\n");
                    System.out.println("|   |        ||");
                    System.out.println("|  \\O/      ||");
                    System.out.println("|   |        ||");
                    System.out.println("|  / \\      ||");


                    for (int i = 0; i < 2; i++)
                            System.out.println("|            ||");
                    System.out.println("|________||");
                    break;
            default:
                    System.out.println("ERROR: EXCEEDED WRONG NUMBER OF GUESSES!");
            }


            System.out.println("\n");
    }
    
    public static void playGame(String array[]) {
            gamesPlayed++;


            String allGuess = "";


            String word = getRandomWord(array);


            System.out.println(getRevealedChars(word,""));


            Scanner fromKey = new Scanner(System.in);
            int incor = 0;
            while(incor <= 6) {
                    if(getRevealedChars(word,allGuess).contains(word)) {
                            gamesWon++;
                            System.out.print("Congrats You Win!");
                            //ndisplayMenu();
                            return;
                    }


                    System.out.println("Guess Your Letter,");
                    System.out.println("Lower Case Letters Only!");


                    String word1 = fromKey.next();
                    char letter = word1.charAt(0);


                    if(checkChar(letter, word) == true) {
                            totalGuesses++;
                            rightGuesses++;
                            allGuess+=letter;
                            System.out.println(getRevealedChars(word,allGuess));




                    } else {
                            totalGuesses++;
                            wrongGuesses++;
                            incor++;
                            displayHangMan(incor);
                            System.out.println(getRevealedChars(word,allGuess));
                    }




                    if(word1.length()>1) {
                            gamesLost++;
                            System.out.println("Sorry You Lose");
                            System.out.println("Please Only Pick One Letter");
                            System.out.println("Please Try Again");
                            return;
                    }


                    if(incor == 6) {
                            gamesLost++;
                            System.out.println("\nSorry You Lose!");
                            System.out.print("Your Word Was "+word);
                            return;
                    }








            }
fromKey.close();
    }


    public static void main(String[] args) {
            int i = 1;
            
            while (i==1) {
                    String array[] = { "car", "chess", "hamza", "french", "brian", "tariq", "computer", "coder", "school", "board", "bicycle", "mouse", "whiteboard", "dinner", "teacher", "headphones", "poster", "cookies", "wordle", "chair", "rain", "ball", "monitor", "hypnotic", "bruise", "entertainment", "inject", "voiceless", "spill", "murky", "gainful", "spirtual", "efficacious", "gorgeous", "demonic", "pastoral", "parallel", "numberless", "unwritten", "polution", "infamous", "sunny", "wonder", "sweltering", "window1", "holistic", "obedient", "monitor", "filler"};
                    
                    System.out.println("\nWelcome To Nick's Hangman!\n");
                    System.out.println("Menu");
                    System.out.println("Press 1 To Play Hangman");
                    System.out.println("Press 2 To Open Stats");
                    System.out.println("Press 3 To End The Game");
                    
                    Scanner menuScan = new Scanner(System.in);
                    String userChoice = menuScan.next();
                    
                    switch(userChoice) {
                    case "1":
                            playGame(array);
                            break;
                    case "2":
                            System.out.println("Games Won "+gamesWon);
                            System.out.println("Games Lost "+gamesLost);
                            System.out.println("Games Played "+gamesPlayed);
                            System.out.println("Total Guesses "+totalGuesses);
                            System.out.println("Wrong Guesses "+wrongGuesses);
                            System.out.println("Right Guesses "+rightGuesses);
                            System.out.print("\n");
                            break;
                    case "3":
                            System.out.println("\nTHANKS FOR PLAYING :)");
                            userChoice = "0";
                            menuScan.close();
                            i--;
                            return;
                            default:
                                    System.out.println("VALID OPTIONS ONLY");
                                    System.out.println("TRY AGAIN");
                                    break;
                            
                    }
            }
            
    }


}