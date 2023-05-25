import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class Mandelbrot extends JPanel {

	static final int MAX_ITERATION_INIT = 150;
	
	static final double ASPECT_RATIO = 0.75;
	static final double M_WIDTH_INIT = 3.8;
	static final double M_HEIGHT_INIT = M_WIDTH_INIT * ASPECT_RATIO;
	
	static final double MX_INIT = -2.55;
	static final double GET_MX_INIT_CONSTANT = -MX_INIT / M_WIDTH_INIT;
	static final double MY_INIT = 1.425;
	
	static final int VIEW_WIDTH_INIT = 1000;
	static final int VIEW_HEIGHT_INIT = (int)(VIEW_WIDTH_INIT * ASPECT_RATIO);
	static final double MX_STEP_INIT = M_WIDTH_INIT/VIEW_WIDTH_INIT;
	static final double MY_STEP_INIT = M_HEIGHT_INIT/VIEW_HEIGHT_INIT;
	
	static final double SAT_INIT = 0.88;
	static final double BRIGHT_INIT = 0.75;
	static final double OFFSET_INIT = 0;
	
	static final double MODULUS_THRESHOLD = 150;

	// Mandelbrot (TOP LEFT) coordinates
	static double mX = MX_INIT;
	static double mY = MY_INIT;
	
	static double mXDown = mX + M_WIDTH_INIT;
	static double mYDown = mY - M_HEIGHT_INIT;

	// Mandelbrot width and height  
	static double mWidth = M_WIDTH_INIT;
	static double mHeight = M_HEIGHT_INIT;

	// view Dimensions
	static final int vWidth = VIEW_WIDTH_INIT;
	static final int vHeight = VIEW_HEIGHT_INIT;

	// How much to step by when drawing
	static double mXStep = MX_STEP_INIT;
	static double mYStep = MY_STEP_INIT;

	static int maxIterations = MAX_ITERATION_INIT;

	static double offset = OFFSET_INIT;
	static double sat = SAT_INIT;
	static double bright = BRIGHT_INIT;
	
	static double modulusThreshold = MODULUS_THRESHOLD;
	
	static boolean whiteAndBlack = false;
	static boolean mandelbrotWhite = false;
	
	static int iterationMultConst = 1;
	
	static double mxInitConst = GET_MX_INIT_CONSTANT;
	
	Color mandelbrotColor = new Color(0, 0, 0);

	public static DLLMandelbrot xStartPositions = new DLLMandelbrot();
	public static DLLMandelbrot yStartPositions = new DLLMandelbrot();
	public static DLLMandelbrot xEndPositions = new DLLMandelbrot();
	public static DLLMandelbrot yEndPositions = new DLLMandelbrot();
	
	static int getViewWidth() {
		return vWidth;
	}

	static int getViewHeight() {
		return vHeight;
	}
	
	static double getAspectRatio() {
		return ASPECT_RATIO;
	}
	
	static void setXY(int x1, int y1, int x2, int y2) {
		if (x2 - x1 == 0 || y2 - y1 == 0) {
			return;
		}
		
		if (x2 - x1 < 0 && y1 - y2 < 0) {
			mXDown = javaToComplexX(x1);
			mYDown = javaToComplexY(y2);
			mX = javaToComplexX(x2);
			mY = javaToComplexY(y1);
			mWidth = Math.abs(mXDown - mX);
			mHeight = mWidth * ASPECT_RATIO;
		} else if (y1 - y2 > 0 && x2 - x1 > 0) {
			mXDown = javaToComplexX(x2);
			mYDown = javaToComplexY(y1);
			mX = javaToComplexX(x1);
			mWidth = Math.abs(mXDown - mX);
			mHeight =  mWidth * ASPECT_RATIO;
			mY = mYDown + mHeight;
//			rectangleZoom.setBounds(endX - Math.abs(endX - startX), startY - (int) (Math.abs(endX - startX) * M_ASPECT_RATIO), Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));
		} else if (x2 - x1 < 0 && y1 - y2 > 0) {
			mXDown = javaToComplexX(x1);
			mYDown = javaToComplexY(y1);
			mX = javaToComplexX(x2);
			mWidth = Math.abs(mXDown - mX);
			mHeight =  mWidth * ASPECT_RATIO;
			mY = mYDown + mHeight;
			
//			rectangleZoom.setBounds(endX , startY - (int) (Math.abs(endX - startX) * M_ASPECT_RATIO), Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));
		} else {
			mXDown = javaToComplexX(x2);
			mYDown = javaToComplexY(y2);
			mX = javaToComplexX(x1);
			mY = javaToComplexY(y1);	
			mWidth = Math.abs(mXDown - mX);
			mHeight = mWidth * ASPECT_RATIO;	
		}
		
		xStartPositions.append(mX);
		yStartPositions.append(mY);
		xEndPositions.append(mXDown);
		yEndPositions.append(mYDown);
		
		System.out.println(mWidth + " ==== " + mHeight);
		System.out.println(mX + " ==== " + mY);
		if (mWidth != 0) {
			mXStep = mWidth / vWidth;
			mYStep = mHeight / vHeight;
		}
	}
	
	static void undo() {
		if (xStartPositions.last != null) {
			if (xStartPositions.last.prev != null) {			
				mX = xStartPositions.last.prev.data;
				mY = yStartPositions.last.prev.data;
				mXDown = xEndPositions.last.prev.data;
				mYDown = yEndPositions.last.prev.data;
				
				mWidth = Math.abs(mXDown - mX);
				mHeight = mWidth * ASPECT_RATIO;
				
				mXStep = mWidth / vWidth;
				mYStep = mHeight / vHeight;
				
				xStartPositions.removeLast();
				yStartPositions.removeLast();
				xEndPositions.removeLast();
				yEndPositions.removeLast();
			} else {
				mX = MX_INIT;
				mY = MY_INIT;
				mWidth = M_WIDTH_INIT;
				mHeight = M_HEIGHT_INIT;
				mXStep = MX_STEP_INIT;
				mYStep = MY_STEP_INIT;
				xStartPositions.removeLast();
				yStartPositions.removeLast();
				xEndPositions.removeLast();
				yEndPositions.removeLast();
			}
		} else {
			mX = MX_INIT;
			mY = MY_INIT;
			mWidth = M_WIDTH_INIT;
			mHeight = M_HEIGHT_INIT;
			mXStep = MX_STEP_INIT;
			mYStep = MY_STEP_INIT;
			xStartPositions.removeLast();
			yStartPositions.removeLast();
			xEndPositions.removeLast();
			yEndPositions.removeLast();
		}
	}
	
	static void setIterationNum(int num) {
		maxIterations = num;
	}
	
	static void setModThreshold(double num) {
		modulusThreshold = num;
	}
	
	static void setSaturation(double num) {
		sat = num;
	}
	
	static void setBrightness(double num) {
		bright = num;
	}
	
	static void setOffset(double num) {
		offset = num;
	}
	
	static void setWhiteAndBlack(boolean bool) {
		whiteAndBlack = bool;
	}

	static void setMandelbrotWhite(boolean bool) {
		mandelbrotWhite = bool;
	}
	
	static void setIterationMultConst(int num) {
		iterationMultConst = num;
	}

    public static void drawPixel(Graphics g, int x, int y, Paint color) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(color);
        g2D.drawLine(x, y, x, y);
    }
   
    @Override
    public void paintComponent(Graphics g) {
    }

	boolean doesEscape(double a, double b) {
		ComplexNumber c = new ComplexNumber(a, b);
		
		ComplexNumber answer = new ComplexNumber(0, 0);
		
		int iterationNum = 0;

		while (iterationNum < maxIterations) {
			answer.squareThisCN();
			answer.addTo(c);
			
			if (answer.modSquare() > modulusThreshold) {
		        if (whiteAndBlack) {
		        	if (mandelbrotWhite) {		        		
		        		mandelbrotColor = Color.black;
		        	} else {		        		
		        		mandelbrotColor = Color.white;
		        	}	
		        } else {		        	
		        	mandelbrotColor = getColor(iterationNum*iterationMultConst);//new Color( (int) Math.abs(Math.sin(answer.modSquare()%255)*255), (int) Math.abs(Math.cos(answer.modSquare()%255)*255), (100*(int)answer.modSquare())%255);
		        }
				return true;
			}
			iterationNum++;
			
		}
		if (mandelbrotWhite) {			
			mandelbrotColor = Color.white;
		} else {
			mandelbrotColor = Color.black;
		}
		return false;
	}
	
	Color getColor(int iteration) {
//		if (mode == COLOR_MODE) {
		float hue = (float) ((iteration % 256) / 255.0f+offset);
		if (hue > 1) {			
			hue = hue - 1;
		}

		return Color.getHSBColor(hue, (float) sat, (float) bright);
//		}
//		else if (mode == BLACK_AND_WHITE_MODE) {
//			int sn = (int)(iteration - Math.log(Math.log(zLast.magSquared())/Math.log(zLast.magSquared()))/Math.log(4));
//			if (sn > 255)
//			return new Color(0,0,0);
//			else
//		//return Color.getHSBColor(sn, sat, bright);
//			return new Color(255-(int)sn,255-(int)sn,255-(int)sn);
//		}

//		return null;
	}


	static int complexToJavaX(double x) {

//		double addToNum = vWidth * mxInitConst;
//
//		double javaX = 0;
//		
//		if (x == 0) {
//			javaX = addToNum;
//		} else {			
//			double solveForCoefficient = -addToNum/mX;
//			
//			javaX = (solveForCoefficient * x) + addToNum;
//		}
//		
//		return (int) javaX;

//		x = -mX-x;
//		
//		return (int)Math.round(x/mWidth*vWidth);
		
//		x = x - mX;
		
		return (int) Math.round((x - mX) / mWidth * vWidth);
		
	}

	static int complexToJavaY(double y) {
		
//		double addToNum = vHeight / 2;
//		
//		double javaY = 0;
//		
//		if (y == 0) {
//			javaY = addToNum;
//		} else {
//			double solveForCoefficient = -addToNum/mY;			
//			javaY = (solveForCoefficient * y) + addToNum;
//		}
		
//		return (int) javaY;
		
//		y = mY-y;
		
		return (int) Math.round((mY - y) / mHeight * vHeight);
		
	}
	
	static double javaToComplexX(int x) {
		
//		double addToX = vWidth * mxInitConst;
//		
//		double complexX = 0;
//		
//		if (x == 0) {
//			complexX = mX;
//		} else {			
//			double solveForDivisor = -addToX/mX;
//			complexX = (x - addToX) / solveForDivisor;
//		}
//		
//		return complexX;

		return mWidth * x / vWidth + mX;
		
	}

	static double javaToComplexY(int y) {
//		
//		double addToY = vHeight / 2;
//		
//		double complexY = 0;
//		
//		if (y == 0) {
//			complexY = mY;
//		} else {			
//			double solveForDivisor = -addToY/mY;
//			complexY = (y - addToY) / solveForDivisor;
//		}
//		
//		return complexY;
		
		return (mHeight * y / vHeight - mY) / -1;
		
	}
//
//	Color getColor(int iteration) {
//	}

	void resetView() {
		mX = MX_INIT;
		mY = MY_INIT;
		mWidth = M_WIDTH_INIT;
		mHeight = M_HEIGHT_INIT;
		mXStep = MX_STEP_INIT;
		mYStep = MY_STEP_INIT;
		offset = 0f;
		sat = SAT_INIT;
		bright = BRIGHT_INIT;
		offset = OFFSET_INIT;
		maxIterations = MAX_ITERATION_INIT;
		modulusThreshold = MODULUS_THRESHOLD;
		iterationMultConst = 1;
		whiteAndBlack = false;
		mandelbrotWhite = false;
//		mode = BLACK_AND_WHITE_MODE;
//		zoom = 1;
		repaint();
	}
	
	public Mandelbrot() {
		setPreferredSize(new Dimension(vWidth, vHeight));
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Mandelbrot());
		frame.setSize(vWidth, vHeight);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		
//		Mandelbrot.drawPixel(g, Mandelbrot.complexToJavaX(-2.17), Mandelbrot.complexToJavaY(Mandelbrot.javaToComplexY(50)), Color.BLACK);
		//Mandelbrot.complexToJavaY(Mandelbrot.javaToComplexY(50)
		
		double currentReal = mX;
		double currentImaginary = mY;
		
		while (currentImaginary >= (mY - mHeight)) {
			
			while (currentReal <= (mX + mWidth)) {
				
//				if (i % 50 == 0) {
				if (this.doesEscape(currentReal, currentImaginary)) {
					Mandelbrot.drawPixel(g, Mandelbrot.complexToJavaX(currentReal), Mandelbrot.complexToJavaY(currentImaginary), mandelbrotColor);
//					System.out.println("Black");
				} else {
					Mandelbrot.drawPixel(g, Mandelbrot.complexToJavaX(currentReal), Mandelbrot.complexToJavaY(currentImaginary), mandelbrotColor);
				}
				
				currentReal = currentReal + mXStep;
			}
			currentImaginary = currentImaginary - mYStep;
			currentReal = mX;
			
//			current = null;
		}
		
		
	}

}
