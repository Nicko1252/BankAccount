import java.math.BigDecimal;
import java.math.MathContext;

public class ComplexNumber {
	
	// the real part of a complex number
	double real;
	// the imaginary part of a complex number
	double imaginary;
	
	
	// initializes real and imaginary to zero
	ComplexNumber() {
		this.real = 0;
		this.imaginary = 0;
	}
	
	// initializes the private variables real and imaginary to r and i respectively
	ComplexNumber(double r, double i) {
		this.real = r;
		this.imaginary = i;
	}
	
	void addTo(ComplexNumber c) {
		this.real = this.real + c.real;
		this.imaginary = this.imaginary + c.imaginary;
	}
	// For all the examples below assume c1 and c2 are complex numbers that have already
	// been declared and initialized

	// Example of how to use this method: c1.addTo (c2) ;
	// The above line (example) updates c1 with the result of adding c1 (the caller) with c2
	void subFrom(ComplexNumber c) {
		this.real = this.real - c.real;
		this.imaginary = this.imaginary - c.imaginary;
	}
	// Example of how to use this method: c1.subFrom (c2) ;
	// The above line (example) updates c1 with the result of subtracting c2 from c1

	void multBy(ComplexNumber c) {
		double r1 = this.real * c.real;
		double i1 = this.real * c.imaginary;
		double r2 = (this.imaginary * c.imaginary) * -1;
		double i2 = this.imaginary * c.real;
		
		this.real = r1 + r2;
		this.imaginary = i1 + i2;
	}
	// Example of how to use this method: c1.multBy (c2) ;
	// The above line (example) updates c1 with the result of multiplying c1 with c2

	void squareThisCN() {
		double r1 = this.real * this.real;
		double i1 = this.real * this.imaginary;
		double r2 = (this.imaginary * this.imaginary) * -1;
		double i2 = this.imaginary * this.real;
		
		this.real = r1 + r2;
		this.imaginary = i1 + i2;
	}
	// Example of how to use this method: c1.squareThisCN () ;
	// The above line (example) updates c1 with the result of squaring c1

	ComplexNumber add(ComplexNumber c) {
		double r = this.real + c.real;
		double i = this.imaginary + c.imaginary;
		
		ComplexNumber c2 = new ComplexNumber(r, i);
		return c2;
	}
	// Example of how to use this method: ComplexNumber myCN = c1.add(c2);
	// The above line (example) initializes the complex number myCN with the result of
	// adding c1 with c2
	// Note: this method does not change c1 (the caller) in any way (as opposed to addTo).

	ComplexNumber sub(ComplexNumber c) {
		double r = this.real - c.real;
		double i = this.imaginary - c.imaginary;
		
		ComplexNumber c2 = new ComplexNumber(r, i);
		return c2;
	}
	// Example of how to use this method: ComplexNumber myCN = c1.sub(c2);
	// The above line (example) initializes the complex number myCN with the result of
	// subtracting c2 from c1
	// Note: this method does not change c1 (the caller) in any way (as opposed to subFrom).

	ComplexNumber mult(ComplexNumber c) {
		double r1 = this.real * c.real;
		double i1 = this.real * c.imaginary;
		double r2 = (this.imaginary * c.imaginary) * -1;
		double i2 = this.imaginary * c.real;
		
		double real = r1 + r2;
		double imaginary = i1 + i2;
		
		ComplexNumber c2 = new ComplexNumber(real, imaginary);
		
		return c2;
	}
	// Example of how to use this method: ComplexNumber myCN = c1.mult(c2);
	// The above line (example) initializes the complex number myCN with the result of
	// multiplying c1 with c2
	// Note: this method does not change c1 (the caller) in any way (as opposed to multBy).

	ComplexNumber square() {
		double r1 = this.real * this.real;
		double i1 = this.real * this.imaginary;
		double r2 = (this.imaginary * this.imaginary) * -1;
		double i2 = this.imaginary * this.real;
		
		double real = r1 + r2;
		double imaginary = i1 + i2;
		
		ComplexNumber c2 = new ComplexNumber(real, imaginary);
		
		return c2;
	}
	// Example of how to use this method: ComplexNumber myCN = c1.square();
	// The above line (example) initializes the complex number myCN with the result of
	// squaring c1
	// Note: this method does not change c1 (the caller) in any way (as opposed to
	// squareThisCN).

	boolean equals(ComplexNumber c) {
		if (this.real == c.real && this.imaginary == c.imaginary) {
			return true;
		}
		
		return false;
	}
	// Example of how to use this method: if ( c1.equals(c2) ) { ... } else ...
	// The above line uses the method equals as a condition for an if statement.
	// This method returns true if c1 equals c2, otherwise this method returns false
	// Note: this method does not change c1 (the caller) in any way.
//
	double modulus() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}
	// Example of how to use this method: double absValue = c1.modulus ();
	// The above line uses the method modulus to calculate the absolute (or modulus) value
	// of the complex number c1 .
	// Note: this method does not change c1 (the caller) in any way.

	double modSquare() {
		return (this.real * this.real) + (this.imaginary * this.imaginary);
	}
	// Example of how to use this method: double absValue = c1.modSquare();
	// The above line uses the method modSquare to calculate square of the absolute (or
	// modulus) value of the complex number c1 .
	// Note: this method does not change c1 (the caller) in any way.

	void display() {
//		if (this.real.compareTo(BigDecimal.valueOf(0)) != 0 && this.imaginary.compareTo(BigDecimal.valueOf(0)) != 0) {			
//			BigDecimal i = BigDecimal.valueOf(0);
//			if (this.imaginary.compareTo(BigDecimal.valueOf(0)) < 0) {
//				i = this.imaginary.multiply(BigDecimal.valueOf(-1));
//				if (i.compareTo(BigDecimal.valueOf(1)) == 0) {					
//					System.out.println(this.real + " - i");
//				} else {					
//					System.out.println(this.real + " - " + i + "i");
//				}
//			} else {
//				i = this.imaginary;
//				if (i.compareTo(BigDecimal.valueOf(1)) == 01) {					
//					System.out.println(this.real + " + i");
//				} else {
//					System.out.println(this.real + " + " + i + "i");
//				}
//			}
//		} else if (this.real.compareTo(BigDecimal.valueOf(0)) == 0 && this.imaginary.compareTo(BigDecimal.valueOf(0)) == 0) {
//			System.out.println("0");
//		} else if (this.real.compareTo(BigDecimal.valueOf(0)) == 0) {
//			if (this.imaginary.compareTo(BigDecimal.valueOf(1)) == 0) {	
//				System.out.println("i");
//			} else if (this.imaginary.compareTo(BigDecimal.valueOf(-1)) == 0) {
//				System.out.println("-i");
//			} else {
//				System.out.println(this.imaginary + "i");
//			}
//		} else if (this.imaginary.compareTo(BigDecimal.valueOf(0)) == 0) {
//			System.out.println(this.real);
//		}
		if (this.imaginary >= 0) {
			System.out.println(real + " + " + this.imaginary + "i");
		} else {
			System.out.println(real + " - " + this.imaginary * -1 + "i");
		}
	}
	// This method prints the complex number to the screen. Below is an example of how you
	// can use this method

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComplexNumber cn = new ComplexNumber(2, 3);
		cn.display();
		
		System.out.println(cn.modSquare());
		
		ComplexNumber c1 = new ComplexNumber(2, 2);
		
		cn.addTo(c1);
		cn.display();
		
	}

}
