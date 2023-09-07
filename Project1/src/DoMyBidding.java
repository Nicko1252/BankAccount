public class DoMyBidding {
	
	static boolean isEven(long n) {
		
		if (n % 2 == 0) {
			return true;
		}
		
		return false;
		
	}
	
	static boolean isPrime(long n) {
		
		if (n <= 1) {
			return false;
		}
		
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				return false;
			}
		}
	    
	    return true;
	    
	}
	
	static boolean isPalindrome(long n) {
		
		if (n < 0) {
			return false;			
		}
		
		String input = String.valueOf(n);
		
		String newNum = "";
		
		long newN = 0;
		
		for (int i = input.length() - 1; i >= 0; i--) {
			newNum += input.charAt(i);
		}
		
		newN = Long.parseLong(newNum);
		
		if (newN == n) {
			return true;
		}
		
		return false;
		
	}
	
	static boolean isPowerOfTwo(long n) {	

	    if (n == 0) {
	    	return false;	    	
	    }
	    
	    while (n != 1) {
	        if (n % 2 != 0) {	        	
	        	return false;
	        }
	        n /= 2;
	    }
	    
	    return true;
		
	}
	
	static long[] bubbleSort(int[] numArray) {

		for (int i = 0; i < numArray.length; i++) {  
			for (int j = i + 1; j < numArray.length; j++) {
				
				int n = 0;
				
				if (numArray[i] > numArray[j]) {  
					n = numArray[i];  
					numArray[i] = numArray[j];  
					numArray[j] = n;
					
				}  
			}
		}
		
		long[] newArray = new long[numArray.length];
		
		for (int i = 0; i < numArray.length; i++) {
			newArray[i] = numArray[i];
			System.out.println(newArray[i]);
		}
		
		return newArray;
		
	}
	
	static boolean isArmstrong(long n) {
		
		if (n < 0) {
			return false;
		}
		
		String nStr = String.valueOf(n);
		
		long sum = 0;
		
		String xStr = "";
		long x = 0;
		
		for (int i = 0; i < nStr.length(); i++) {
			xStr = String.valueOf(nStr.charAt(i));
			x = Long.parseLong(xStr);
			sum += x*x*x;
		}
		
		if (sum == n) {
			return true;
		}
		
		return false;
		
	}
	
	static String reverse(String str) {
		
		String newStr = "";
		
		for (int i = str.length() - 1; i >= 0; i--) {
			newStr += str.charAt(i);
		}
		
		return newStr;
		
	}
	
	static void printFibonacci(long n) {
		
		long lastNumber = 0;
		long secondLastNumber = 1;
		long fNum = 0;
		
		while (fNum <= n) {
			System.out.println(fNum);
			if (fNum > n) {
				return;
			}
			lastNumber = fNum;
			fNum = lastNumber + secondLastNumber;
			secondLastNumber = lastNumber;
		}
	}
	
	static long factorial(long n) {
		
		long newLong = 1;
		
		if (n > 20) {
			return -1;  
		} else if (n < 0) {
			return -1;
		} else {
			for (long i = n; i > 0; i--) {
				newLong *= i;
			}
		}
		
		return newLong;
		
	}
	
	static void printStarLine(int n) {
		if (n <= 0) {
			System.out.print("\n");
			return;
		}
		System.out.print("* ");
		printStarLine(n - 1);
	}
	
	static void printStarsDescendingMinusOne(int n) {
        if (n <= 0) {
            return;
        }
        printStarLine(n - 1);
        printStarsDescendingMinusOne(n - 1);
	}
	
	static void printStarsAscending(int n) {
        if (n <= 0) {
            return;
        }
        printStarsAscending(n - 1);
        printStarLine(n);
	}
	
	static void printStars(int n) {
        printStarsAscending(n);
        printStarsDescendingMinusOne(n);
	}
	
	static void printCollatz(int n) {
		while (n != 1) {
			System.out.println(n);
			if (isEven(n)) {
				n /= 2;
			} else {
				n = n * 3 + 1;
			}
		}
		System.out.println(n);
	}
	
	static void testCollatz() {
		for (int i = 1; i <= 100; i++) {
			printCollatz(i);
		}
	}
	
	static void printFibonacciPrimes() {
		
		long lastNumber = 0;
		long secondLastNumber = 1;
		long fNum = 0;
		int primeCounter = 0;
		
		while (primeCounter <= 10) {			
			lastNumber = fNum;
			fNum = lastNumber + secondLastNumber;
			secondLastNumber = lastNumber;
			if (isPrime(fNum)) {
				primeCounter++;
				if (primeCounter != 1) {					
					System.out.println(fNum);
				}
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		
		System.out.println(DoMyBidding.isPrime(-64));

		long testValue [] = {-64, -2, -1, 0, 1, 2, 3, 5, 7, 8, 9, 11, 15, 16, 19, 21, 25, 36, 50, 64, 75, 100, 121, 153, 370, 371, 407, 4621, 121151, 1048576, 1234321, 534494836, 912985153};
		boolean b;

		for (int i = 0; i < testValue.length; i++)
		{
			b = DoMyBidding.isEven(testValue[i]);
			if (b)
			{
				System.out.println (testValue[i] + " is Even.");
			}
			else
			{
				System.out.println (testValue[i] + " is NOT Even.");
			}

			b = DoMyBidding.isPrime(testValue[i]);

			if (b)
			{
				System.out.println (testValue[i] + " is prime.");
			}
			else
			{
				System.out.println (testValue[i] + " is NOT prime.");
			}
			
			b = DoMyBidding.isPalindrome(testValue[i]);
		
			if (b)
			{
				System.out.println (testValue[i] + " is Palindrome.");
			}
			else
			{
				System.out.println (testValue[i] + " is NOT Palindrome.");
			}
			b = DoMyBidding.isPowerOfTwo(testValue[i]);
		
			if (b)
			{
				System.out.println (testValue[i] + " is a power of two.");
			}
			else
			{
				System.out.println (testValue[i] + " is NOT a power of two.");
			}
			
			b = DoMyBidding.isArmstrong(testValue[i]);
			
			if (b) {
				System.out.println(testValue[i] + " is Armstrong.");
			} else {
				System.out.println(testValue[i] + " is NOT Armstrong.");
			}
		if (testValue[i] >= 0 && testValue[i] <= 20)
	{
	long result = DoMyBidding.factorial(testValue[i]);
	System.out.println (testValue[i] + "! = " + result);


	}
	System.out.println("\n");
	}
	System.out.println("\nStars:");
	DoMyBidding.printStars(7);
	System.out.println("\nCollatz:");
	DoMyBidding.printCollatz (100);
	System.out.println("\nFibonacci Sequence:");
	DoMyBidding.printFibonacci(100); 


	System.out.println("\nBubble Sort:");
	System.out.println(arrayToString(DoMyBidding.bubbleSort(getArray())));


	System.out.println("\nFibonacci Primes:");
	DoMyBidding.printFibonacciPrimes();
	
	}

	// helper method for main
	public static int[] getArray()	{
		int size=10;
		int []array = new int[size];
		int item = 0;
		for(int i=0;i<size;i++)
		{
			item = (int)(Math.random()*100); 
			array[i] = item;
		}
		
		return array;
	}
	
 	public static String arrayToString(long a[]){
		int b = a.length;
		int i = 0;
		String c = "";
		for (i = 0; i < b; i++){
			c += a[i] + ", ";
		}
		return c;
	}


}
