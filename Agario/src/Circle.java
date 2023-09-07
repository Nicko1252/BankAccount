import java.awt.*;
import java.util.Random;
import java.lang.Math;

public class Circle {
	
	final int MAX_SPEED = 10;
	final int INIT_RADIUS = 12;
	private String userName;
	private int speed;
	private int radius;
	private Color color;
	private int xPos;
	private int yPos;
	private Random randomGenerator = new Random();
	private int r = randomGenerator.nextInt(255);
	private int g = randomGenerator.nextInt(255);
	private int b = randomGenerator.nextInt(255);
	
	Circle(String name, int x, int y) {
		
		userName = name;
		speed = MAX_SPEED;
		radius = INIT_RADIUS;
		Color c = new Color(r, g, b);
		color = c;
		xPos = x;
		yPos = y;
		
	}
	
	void incrementX() {
		xPos++;
	}
	
	void decrementX() {
		xPos--;
	}
	
	void incrementY() {
		yPos++;
	}
	
	void decrementY() {
		yPos--;
	}
	
	boolean setRadius(int val) {
		radius  = val;
		if (val >= INIT_RADIUS) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean setColor(int r, int g, int b) {
		if (r >= 0 && r <= 255) {
			if (g >= 0 && g <= 255) {
				if (b >= 0 && b <= 255) {
					color = new Color(r, g, b);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	boolean setColor(Color c) {
		if (c != null) {
			color = c;
			return true;
		} else {
			return false;
		}
	}
	
	boolean updateSpeed () {
		int change = (int)(Math.log(radius/INIT_RADIUS)/Math.log(2));
		
		if (change > 0) {
			speed -= change;
			return true;
		} else {
			return false;
		}
	}
	
	int getSpeed() {
		return speed;
	}
	
	int getRadius() {
		return radius;
	}
	 
	String getUserName() {
		return userName;
	}
	
	Color getColor() {
		return color;
	}
	
	int diameter() {
		return radius * 2;
	}
	
	double area() {
		return Math.pow((double) getRadius(), 2.0) * (double) Math.PI;
	}
	
	double circumference() {
		return (double) diameter() * (double) Math.PI;
	}
	
	boolean eat(Circle c) {
		if (c.getRadius() < radius) {
			this.radius += c.radius;
			updateSpeed();
			c.setRadius(0);
			return true;
		} else {
			return false;
		}
	}
	
	void display() {
		System.out.println("Maximum Speed: " + MAX_SPEED);
		System.out.println("Initial Radius: " + INIT_RADIUS);
		System.out.println("User Name: " + getUserName());
		System.out.println("Current Speed: " + getSpeed());
		System.out.println("Current Radius: " + getRadius());
		System.out.println("Current Color: " + getColor());
		System.out.println("Current X-Coordinate: " + xPos);
		System.out.println("Current Y-Coordinate: " + yPos);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Circle c1 = new Circle("Name", 0, 0);
		Circle c2 = new Circle("Name", 0, 0);
		c1.display();
		c2.display();
		c1.radius = 15;
		c1.eat(c2);
		c1.display();
		c2.display();

		
		//circle.display();
		
		
		
	}

}