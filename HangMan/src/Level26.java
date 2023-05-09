import java.util.Random;
import java.util.Scanner;

public class Level26 {


	boolean checkChar (char c, String str) {
	
	}

	String getRandomWord (String array[]) {
	
	}

	String getRandomWord (String array[], int len) {
	
	}

	String getRevealedChars (String word, String guess) {
	
	}

	static void displayHangMan(int numWrong)
	{
		System.out.println();

		switch (numWrong)
		{
		case 0:
			break;
		case 1:
			System.out.print("    _______\n");
			break;
		case 2:
			System.out.print("    _______\n");
			for (int i = 0; i < 6; i++) 
				System.out.println("|        ||");
			break;
		case 3:
			System.out.println("    _______");
			for (int i = 0; i < 6; i++) 
				System.out.println("|        ||");
			System.out.println("|________||");
			break;
		case 4:
			System.out.println("    _______");
			System.out.println("|   |    ||");
			System.out.println("|  \\O/   ||");

			for (int i = 0; i < 4; i++) 
				System.out.println("|        ||");
			System.out.println("|________||");
			break;
		case 5:
			System.out.print("    _______\n");
			System.out.println("|   |    ||");
			System.out.println("|  \\O/   ||");
			System.out.println("|   |    ||");

			for (int i = 0; i < 3; i++) 
				System.out.println("|        ||");
			System.out.println("|________||");
			break;
		case 6:
			System.out.print("    _______\n");
			System.out.println("|   |    ||");
			System.out.println("|  \\O/   ||");
			System.out.println("|   |    ||");
			System.out.println("|  / \\   ||");

			for (int i = 0; i < 2; i++) 
				System.out.println("|        ||");
			System.out.println("|________||");
			break;
		default:
			System.out.println("ERROR: EXCEEDED WRONG NUMBER OF GUESSES!");
		}

		System.out.println("\n");
	}

	public static void main(String[] args) {
		
		
	}


}