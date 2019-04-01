import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Plot extends Canvas{
	JFrame window;
	//Graphics gr;
	double[] domain;
	double[] range;
	int nSplits;
	String relation;
	
	public Plot(String name, int width, int height, double[] xValues, double[] yValues, int nDivisions, String rel){
		window = new JFrame(name);
		window.add(this);
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		domain = xValues;
		range = yValues;
		nSplits = nDivisions;
		relation = rel;
	}
	
	public void setPlane(Graphics gr){
		int length_X_Axis = window.getWidth() - 100;
		int length_Y_Axis = window.getHeight() - 100;
		
		
		for(int k = 0; k<=nSplits; k++){
			//Vertical lines
			gr.drawLine(50 + k*(length_X_Axis/nSplits), 30, 50 + k*(length_X_Axis/nSplits), length_Y_Axis+30);
			gr.drawString("" + Math.round(100*(domain[0] + k*((domain[1]-domain[0])/nSplits)))/100.0, 42 + k*(length_X_Axis/nSplits), length_Y_Axis+50);
		}
		for(int k = 0; k<=nSplits; k++){
			//Horizontal lines
			gr.drawLine(50, 30 + k*(length_Y_Axis/nSplits), length_Y_Axis+50, 30 + k*(length_Y_Axis/nSplits));
			gr.drawString("" + Math.round(100*(range[1] - k*((range[1]-range[0])/nSplits)))/100.0, 25, 35 + k*(length_Y_Axis/nSplits));
		}
		gr.drawLine(50 + length_X_Axis, 30, 50 + length_X_Axis, length_Y_Axis+30);
		gr.drawLine(50, 30 + length_Y_Axis, length_X_Axis+50, 30 + length_Y_Axis);
		
		//Center of Screen:
		gr.drawLine(window.getWidth()/2 -5, window.getHeight()/2 -20, window.getWidth()/2, window.getHeight()/2 -25);
		gr.drawLine(window.getWidth()/2, window.getHeight()/2 -25, window.getWidth()/2 + 5, window.getHeight()/2 -20);
		gr.drawLine(window.getWidth()/2 +5, window.getHeight()/2 -20, window.getWidth()/2, window.getHeight()/2 -15);
		gr.drawLine(window.getWidth()/2, window.getHeight()/2 -15, window.getWidth()/2 -5, window.getHeight()/2 -20);
	}
	
	public void drawPoint(Graphics gr, double[] X_Y){
		if(X_Y[0] < domain[1] && X_Y[0] > domain[0] && X_Y[1] > range[0] && X_Y[1] < range[1])
			gr.fillOval((int)(50+ (X_Y[0]-domain[0])*(window.getWidth()-100)/(double)(domain[1]-domain[0]))-2, (int)(30+ (X_Y[1]-range[1])*(window.getHeight()-100)/(double)(range[0]-range[1]))-2, 4, 4);
	}
	
	public void connectPoints(Graphics gr, double[] X1_Y1, double[] X2_Y2){
		if(X1_Y1[0] <= domain[1] && X1_Y1[0] >= domain[0] && X1_Y1[1] >= range[0] && X1_Y1[1] <= range[1]){
			if(X2_Y2[0] <= domain[1] && X2_Y2[0] >= domain[0] && X2_Y2[1] >= range[0] && X2_Y2[1] <= range[1]){
				int[] x1_y1 = {(int)(50+ (X1_Y1[0]-domain[0])*(window.getWidth()-100)/(double)(domain[1]-domain[0]))-2, (int)(30+ (X1_Y1[1]-range[1])*(window.getHeight()-100)/(double)(range[0]-range[1]))-2};
				int[] x2_y2 = {(int)(50+ (X2_Y2[0]-domain[0])*(window.getWidth()-100)/(double)(domain[1]-domain[0]))-2, (int)(30+ (X2_Y2[1]-range[1])*(window.getHeight()-100)/(double)(range[0]-range[1]))-2};
				//gr.drawLine(x1_y1[0], x1_y1[1], x2_y2[0], x2_y2[1]);
				gr.drawLine(x1_y1[0]+1, x1_y1[1], x2_y2[0]+1, x2_y2[1]);
				gr.drawLine(x1_y1[0]+2, x1_y1[1], x2_y2[0]+2, x2_y2[1]);
			}
		}
	}
	
	public void graphFunction(Graphics gr){
		if(relation != null){
			double[][] setCoordinates = FunctionInterpreter.generateCoordinates(this.domain, this.relation, "x");
			for(int i = 0; i<1000; i++){
				drawPoint(gr, setCoordinates[i]);
				if(i != 0){
					connectPoints(gr, setCoordinates[i-1], setCoordinates[i]);
				}
			}
		}
	}
	
	/*public void graphRelation(Graphics gr, String depen1, String depen2){
		String LHS = relation.split("=")[0];
		String RHS = relation.split("=")[1];
		
		LHS = FunctionInterpreter.infixToPostfix(LHS);
		RHS = FunctionInterpreter.infixToPostfix(RHS);
		double LHSVal;
		double RHSVal;
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put(depen2, range[0]);
		
		for(double i = domain[0]; i <=domain[1]; i += (domain[1]-domain[0])/1000.0){
			map.put(depen1, i);
			
			for(double j = range[0]; j <=range[1]; j += (range[1]-range[0])/1000.0){
				
				map.put(depen2, j);
				LHSVal = FunctionInterpreter.evaluateExpression(map, LHS);
				RHSVal = FunctionInterpreter.evaluateExpression(map, RHS);
				//System.out.println(RHSVal);
				
				if(Math.abs(LHSVal - RHSVal) <= 0.01){
					System.out.println(LHSVal + " " + RHSVal);
					double[] coordinates = {i, j};
					drawPoint(gr, coordinates);
				}
			}
		}
		System.out.println("done");
	}*/
	
	public void paint(Graphics gr){
		try{
			setPlane(gr);
			//double[] coordinates = {5.0, 4.0};
			//drawPoint(gr, coordinates);
			graphFunction(gr);
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), "Invalid Input", "Dialog", JOptionPane.ERROR_MESSAGE);
			this.window.dispose();
		}
	}
}
