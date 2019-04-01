import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame implements ActionListener{

	JTextField function, domain1, domain2, range1, range2;
	JLabel prefix, x, y, author, date;
    JButton graphButton;
    static String version = "1.0.2"; 
	
	public Main(){
		setLayout(null);
		prefix = new JLabel("function: ");
		prefix.setBounds(125, 15, 100, 20);
		add(prefix);
        function = new JTextField(5);
        function.setText("y=x^2");
        function.setBounds(100,35,100,20);
        add(function);
        
        domain1 = new JTextField(5);
        domain1.setBounds(50, 100, 70, 20);
        domain1.setText("-5");
        add(domain1);
        x = new JLabel(" < x < ");
        x.setBounds(135, 100, 100, 20);
        add(x);
        domain2 = new JTextField(5);
        domain2.setText("5");
        domain2.setBounds(185, 100, 70, 20);
        add(domain2);
        
        range1 = new JTextField(5);
        range1.setBounds(50, 140, 70, 20);
        range1.setText("-5");
        add(range1);
        y = new JLabel(" < y < ");
        y.setBounds(135, 140, 100, 20);
        add(y);
        range2 = new JTextField(5);
        range2.setText("5");
        range2.setBounds(185, 140, 70, 20);
        add(range2);
     
        graphButton = new JButton("Graph");
        graphButton.setBounds(100,200,100,20);
        graphButton.addActionListener(this);
        add(graphButton);
        
        author = new JLabel("Made by Allan DoLago");
        date = new JLabel("2016-2017");
        author.setBounds(88, 235, 150, 20);
        date.setBounds(120, 250, 100, 20);
        add(author);
        add(date);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()== graphButton ){
			double[] domain = {Double.parseDouble(domain1.getText()), Double.parseDouble(domain2.getText())};
			double[] range = {Double.parseDouble(range1.getText()), Double.parseDouble(range2.getText())};
			Plot main_window = new Plot("Graphing Calculator", 640, 640, domain, range, 10, function.getText());
        }
	}
	
	public static void main(String[] args){
		Main MainWindow = new Main();
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainWindow.setResizable(false);
        MainWindow.setVisible(true);
        MainWindow.setLocationRelativeTo(null);
        MainWindow.setSize(300, 300);
        MainWindow.setTitle("Grapher");
	}
}