import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class MandelbrotGUI {

	private JFrame frame;	static final int GUI_WIDTH = 1226;
	static final int MANDELBROT_WIDTH = Mandelbrot.getViewWidth();
	static final int MANDELBROT_HEIGHT = Mandelbrot.getViewHeight();
	
	static final double M_ASPECT_RATIO = Mandelbrot.getAspectRatio();
	
	int startX = 0;
	int startY = 0;
	
	int endX = 0;
	int endY = 0;
	
	boolean isDragging = false;
	
	private JLabel iterationLbl;
	private JLabel modLbl;
	
	private JLabel offsetLbl;
	private JLabel brightLbl;
	private JLabel satLbl;
	private JLabel iterationMultiplierLbl;
	
	private int iterationInit = 150;
	private int modInit = 150;
	private int satInit = 88;
	private int brightInit = 75;
	private int offsetInit = 0;
	private int multInit = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotGUI window = new MandelbrotGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MandelbrotGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(325, 150, GUI_WIDTH, (int) ((MANDELBROT_WIDTH) * 0.75));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
//		frame.setGlassPane(glassPane); 
//		glassPane.setBounds(0, 0, 1000, 711);

		
//		GlassPane glassPane = new GlassPane();
//		glassPane.setBounds(0, 0, 1000, 711);
//		frame.getContentPane().add(glassPane);
//		frame.setGlassPane(glassPane);
		
		JLayeredPane glassLayer = new JLayeredPane();
//		glassLayer.setBorder(new LineBorder(new Color(0, 0, 0)));
		glassLayer.setBounds(0, 0, 1000, 711);
		frame.getContentPane().add(glassLayer);
		
		JLayeredPane rectangleZoom = new JLayeredPane();
//		rectangleZoom.setBorder(new LineBorder(new Color(0, 0, 0)));
		rectangleZoom.setBounds(0, 0, 0, 0);
		glassLayer.add(rectangleZoom);
		
		
		Mandelbrot mPanel = new Mandelbrot();
		mPanel.setBounds(0, 0, 1000, 711);
		
		glassLayer.addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) {
				startX = me.getX();
				startY = me.getY();
				
			}
			public void mouseReleased(MouseEvent me) {
				endX = me.getX();
				endY = me.getY();
				
				Mandelbrot.setXY(startX, startY, endX, endY);
				mPanel.repaint();
				glassLayer.setBounds(0, 0, 1000, 711);

				rectangleZoom.setVisible(false);
			}
		});
		
		glassLayer.addMouseMotionListener(new MouseAdapter() {			
			public void mouseDragged(MouseEvent me) {
				endX = me.getX();
				endY = me.getY();
				rectangleZoom.setVisible(true);
				
				if (endX - startX < 0 && startY - endY < 0) {
					rectangleZoom.setBounds(endX, startY, Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));
				} else if (startY - endY > 0 && endX - startX > 0) {
					rectangleZoom.setBounds(endX - Math.abs(endX - startX), startY - (int) (Math.abs(endX - startX) * M_ASPECT_RATIO), Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));
				} else if (endX - startX < 0 && startY - endY > 0) {
					rectangleZoom.setBounds(endX , startY - (int) (Math.abs(endX - startX) * M_ASPECT_RATIO), Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));
				} else {
					rectangleZoom.setBounds(startX, startY, Math.abs(endX - startX), (int) Math.abs(((endX - startX) * M_ASPECT_RATIO)));					
				}
				rectangleZoom.setBorder(new LineBorder(new Color(255, 0, 0), 2));
			}
		});
		
		frame.getContentPane().add(mPanel);
		mPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("Black and White");
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		panel.setBounds(1000, 0, 210, 711);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JSlider iterationSlider = new JSlider();
		iterationSlider.setSnapToTicks(true);
		iterationSlider.setRequestFocusEnabled(false);
		iterationSlider.setValue(iterationInit);
		iterationSlider.setMinimum(1);
		iterationSlider.setMaximum(2000);
		iterationSlider.setBackground(Color.BLACK);
		iterationSlider.setBounds(10, 36, 190, 26);
		iterationSlider.addChangeListener(new ChangeListener(){
	          public void stateChanged(ChangeEvent e) {
	        	  Mandelbrot.setIterationNum(iterationSlider.getValue());
	        	  iterationLbl.setText(("Number of Iterations: " + iterationSlider.getValue()));
	        	  mPanel.repaint();
	          }
	    });
		panel.add(iterationSlider);
		
		iterationLbl = new JLabel("Number of Iterations: " + iterationSlider.getValue());
		iterationLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		iterationLbl.setForeground(Color.WHITE);
		iterationLbl.setBounds(10, 11, 190, 26);
		panel.add(iterationLbl);
		
		JLabel iterationMin = new JLabel("1");
		iterationMin.setForeground(Color.WHITE);
		iterationMin.setBounds(20, 60, 27, 14);
		panel.add(iterationMin);
		
		JLabel iterationMax = new JLabel("2000");
		iterationMax.setForeground(Color.WHITE);
		iterationMax.setBounds(163, 60, 37, 14);
		panel.add(iterationMax);
		
		JSlider modSlider = new JSlider();
		modSlider.setValue(modInit);
		modSlider.setMinimum(1);
		modSlider.setMaximum(1000);
		modSlider.setBackground(Color.BLACK);
		modSlider.setBounds(10, 133, 190, 26);
		modSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Mandelbrot.setModThreshold(modSlider.getValue());
				modLbl.setText("Modulus Threshold: " + modSlider.getValue());
				mPanel.repaint();
			}
		});
		panel.add(modSlider);
		
		modLbl = new JLabel("Modulus Threshold: " + modSlider.getValue());
		modLbl.setForeground(Color.WHITE);
		modLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		modLbl.setBounds(10, 108, 190, 26);
		panel.add(modLbl);
		
		JLabel modMin = new JLabel("1");
		modMin.setForeground(Color.WHITE);
		modMin.setBounds(20, 157, 27, 14);
		panel.add(modMin);
		
		JLabel modMax = new JLabel("1000");
		modMax.setForeground(Color.WHITE);
		modMax.setBounds(163, 157, 37, 14);
		panel.add(modMax);
		
		JSlider satSlider = new JSlider();
		satSlider.setValue(satInit);
		satSlider.setBackground(Color.BLACK);
		satSlider.setBounds(10, 230, 190, 26);
		satSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Mandelbrot.setSaturation(satSlider.getValue()/100.0);
				satLbl.setText("Saturation Percentage: " + satSlider.getValue() + "%");
				mPanel.repaint();
			}
		});
		panel.add(satSlider);
		
		satLbl = new JLabel("Saturation Percentage: " + satSlider.getValue() + "%");
		satLbl.setForeground(Color.WHITE);
		satLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		satLbl.setBounds(10, 205, 190, 26);
		panel.add(satLbl);
		
		JLabel satMin = new JLabel("0");
		satMin.setForeground(Color.WHITE);
		satMin.setBounds(20, 254, 27, 14);
		panel.add(satMin);
		
		JLabel satMax = new JLabel("100");
		satMax.setForeground(Color.WHITE);
		satMax.setBounds(173, 254, 27, 14);
		panel.add(satMax);
		
		JLabel brightMin = new JLabel("0");
		brightMin.setForeground(Color.WHITE);
		brightMin.setBounds(20, 351, 27, 14);
		panel.add(brightMin);
		
		JLabel brightMax = new JLabel("100");
		brightMax.setForeground(Color.WHITE);
		brightMax.setBounds(173, 351, 27, 14);
		panel.add(brightMax);
		
		JSlider brightSlider = new JSlider();
		brightSlider.setValue(brightInit);
		brightSlider.setBackground(Color.BLACK);
		brightSlider.setBounds(10, 327, 190, 26);
		brightSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Mandelbrot.setBrightness(brightSlider.getValue()/100.0);
				brightLbl.setText("Brightness Percentage: " + brightSlider.getValue() + "%");
				mPanel.repaint();
			}
		});
		panel.add(brightSlider);
		
		brightLbl = new JLabel("Brightness Percentage: " + brightSlider.getValue() + "%");
		brightLbl.setForeground(Color.WHITE);
		brightLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		brightLbl.setBounds(10, 302, 190, 26);
		panel.add(brightLbl);
		
		JLabel offsetMin = new JLabel("0");
		offsetMin.setForeground(Color.WHITE);
		offsetMin.setBounds(20, 448, 27, 14);
		panel.add(offsetMin);
		
		JSlider offsetSlider = new JSlider();
		offsetSlider.setValue(offsetInit);
		offsetSlider.setBackground(Color.BLACK);
		offsetSlider.setBounds(10, 424, 190, 26);
		offsetSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Mandelbrot.setOffset(offsetSlider.getValue()/100.0);
				offsetLbl.setText("Offset Percentage: " + offsetSlider.getValue() + "%");
				mPanel.repaint();
			}
		});
		panel.add(offsetSlider);
		
		offsetLbl = new JLabel("Offset Percentage: " + offsetSlider.getValue() + "%");
		offsetLbl.setForeground(Color.WHITE);
		offsetLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		offsetLbl.setBounds(10, 399, 190, 26);
		panel.add(offsetLbl);
		
		JLabel offsetMax = new JLabel("100");
		offsetMax.setForeground(Color.WHITE);
		offsetMax.setBounds(173, 448, 27, 14);
		panel.add(offsetMax);
		
		JCheckBox blackAndWhiteCB = new JCheckBox("Black and White");
		blackAndWhiteCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blackAndWhiteCB.setBackground(Color.BLACK);
		blackAndWhiteCB.setForeground(Color.WHITE);
		blackAndWhiteCB.setBounds(36, 566, 137, 23);
		blackAndWhiteCB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (blackAndWhiteCB.isSelected()) {					
					Mandelbrot.setWhiteAndBlack(true);
				} else {					
					Mandelbrot.setWhiteAndBlack(false);
				}

				mPanel.repaint();
			}
		});
		panel.add(blackAndWhiteCB);
		
		JCheckBox whiteMandelbrotCB = new JCheckBox("White Mandelbrot");
		whiteMandelbrotCB.setForeground(Color.WHITE);
		whiteMandelbrotCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		whiteMandelbrotCB.setBackground(Color.BLACK);
		whiteMandelbrotCB.setBounds(36, 599, 137, 23);
		whiteMandelbrotCB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (whiteMandelbrotCB.isSelected()) {					
					Mandelbrot.setMandelbrotWhite(true);
				} else {					
					Mandelbrot.setMandelbrotWhite(false);
				}

				mPanel.repaint();
			}
		});
		panel.add(whiteMandelbrotCB);
		
		JLabel multMin = new JLabel("1");
		multMin.setForeground(Color.WHITE);
		multMin.setBounds(20, 545, 27, 14);
		panel.add(multMin);
		
		JSlider multSlider = new JSlider();
		multSlider.setMinimum(1);
		multSlider.setValue(multInit);
		multSlider.setBackground(Color.BLACK);
		multSlider.setBounds(10, 521, 190, 26);
		multSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Mandelbrot.setIterationMultConst(multSlider.getValue());
				iterationMultiplierLbl.setText("Iteration Multiplier: " + multSlider.getValue());
				mPanel.repaint();
			}
		});
		panel.add(multSlider);
		
		iterationMultiplierLbl = new JLabel("Iteration Multiplier: " + multSlider.getValue());
		iterationMultiplierLbl.setForeground(Color.WHITE);
		iterationMultiplierLbl.setFont(new Font("Berlin Sans FB", Font.PLAIN, 14));
		iterationMultiplierLbl.setBounds(10, 496, 190, 26);
		panel.add(iterationMultiplierLbl);
		
		JLabel multMax = new JLabel("100");
		multMax.setForeground(Color.WHITE);
		multMax.setBounds(173, 545, 27, 14);
		panel.add(multMax);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iterationSlider.setValue(iterationInit);
				modSlider.setValue(modInit);
				satSlider.setValue(satInit);
				brightSlider.setValue(brightInit);
				offsetSlider.setValue(offsetInit);
				multSlider.setValue(multInit);
				whiteMandelbrotCB.setSelected(false);
				blackAndWhiteCB.setSelected(false);
				mPanel.resetView();
			}
		});
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(125, 646, 75, 54);
		panel.add(btnNewButton);
		
		JButton undoBtn = new JButton("Undo");
		undoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mandelbrot.undo();
				mPanel.repaint();
			}
		});
		undoBtn.setForeground(Color.WHITE);
		undoBtn.setBackground(Color.BLACK);
		undoBtn.setBounds(10, 646, 75, 54);
		panel.add(undoBtn);
		
	}
}

