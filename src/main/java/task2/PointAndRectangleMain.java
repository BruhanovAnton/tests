package task2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import task1.StatisticDataMain;

public class PointAndRectangleMain {
	JTextArea ta;
	JTextField tf;
	JTextField tf2;

	public PointAndRectangleMain() {
		JFrame frame = new JFrame("Statistic calculator");

		JPanel panel = new JPanel();

		panel.setLayout(new FlowLayout());

		JLabel label = new JLabel("Выберите файл!");
		JLabel x = new JLabel("X");
		JLabel y = new JLabel("Y");
		JButton button = new JButton();

		button.setText("Выбрать");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getPointPosition(getFilePath());
				} catch (FileNotFoundException e1) {
					System.out.println("Ошибка чтения файла");
				}
			}
		});

		ta = new JTextArea(7, 25);
		tf = new JTextField(3);
		tf2 = new JTextField(3);

		tf.setText("0");
		tf2.setText("0");

		panel.add(x);
		panel.add(tf);
		panel.add(y);
		panel.add(tf2);
		panel.add(ta);

		panel.add(label);

		panel.add(button);

		frame.add(panel);

		frame.setSize(300, 300);

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}

	public String getFilePath() {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Choose a directory to save your file: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				System.out.println("Вы выбрали директорию: " + jfc.getSelectedFile());

			}
			return "" + jfc.getSelectedFile();
		} else {
			System.out.println("Вы ничего не выбрали");

			return null;
		}

	}

	public void getPointPosition(String path) throws FileNotFoundException {

		double x0 = Double.parseDouble(tf.getText());
		double y0 = Double.parseDouble(tf2.getText());

		Scanner scn = new Scanner(new File(path));
		List<Point> valuesList = new ArrayList<Point>();

		while (scn.hasNext()) {
			String[] xy = new String[2];
			String points = scn.nextLine();
			xy = points.split(" ");

			valuesList.add(new Point(Double.parseDouble(xy[0]), Double.parseDouble(xy[1])));
		}

		double Xa = valuesList.get(0).getX();
		double Xb = valuesList.get(1).getX();
		double Xc = valuesList.get(2).getX();
		double Xd = valuesList.get(3).getX();

		double Ya = valuesList.get(0).getY();
		double Yb = valuesList.get(1).getY();
		double Yc = valuesList.get(2).getY();
		double Yd = valuesList.get(3).getY();



		if ((x0 == Xa && y0 == Ya) || (x0 == Xb && y0 == Yb) || (x0 == Xc && y0 == Yc) || (x0 == Xd && y0 == Yd)) {
			System.out.println("точка - вершина четырехугольника");
			ta.setText("точка - вершина четырехугольника");
		} else {
			
			if(isPointOnBoard(x0, y0, Xa, Ya, Xb, Yb) || 
					isPointOnBoard(x0, y0, Xb, Yb, Xc, Yc)||
					isPointOnBoard(x0, y0, Xc, Yc, Xd, Yd)||
					isPointOnBoard(x0, y0, Xd, Yd, Xa, Ya)){
				System.out.println("точка лежит на сторонах четырехугольника");				
				
				
			}else{
				if (isPointInsideofTriangle(x0, y0, Xa, Ya, Xb, Yb, Xc, Yc,isPointOnBoard(x0,y0,Xb, Yb, Xd, Yd))
						|| isPointInsideofTriangle(x0, y0, Xc, Yc, Xd, Yd, Xa, Ya,isPointOnBoard(x0,y0,Xb, Yb, Xd, Yd))) {
					System.out.println("точка внутри четырехугольника");
					ta.setText("точка внутри четырехугольника");
				} else {
					System.out.println("точка снаружи четырехугольника");
					ta.setText("точка снаружи четырехугольника");
				}
			}
			
			
			
		}

		
		System.out.println("Координаты: " + x0+" "+y0+" "+Xa+" "+ Ya+" "+ Xb+" "+ Yb);
		System.out.println("Координаты: " + x0+" "+y0+" "+Xb+" "+ Yb+" "+ Xc+" "+ Yc);
		System.out.println("Координаты: " + x0+" "+y0+" "+Xc+" "+ Yc+" "+ Xd+" "+ Yd);
		System.out.println("Координаты: " + x0+" "+y0+" "+Xd+" "+ Yd+" "+ Xa+" "+ Ya);
		scn.close();
	}

	
	public boolean isPointOnBoard(double x, double y, double x1, double y1, double x2, double y2){
		
		
		double value = (y1 - y2)*x+(x2-x1)*y +(x1*y2 - x2*y1);
		
		System.out.println("Значение:" + value);
		
		if((y1 - y2)*x+(x2-x1)*y +(x1*y2 - x2*y1) == 0){
			return true;
		}else{
			return false;
		}
		
		
		
	}
	
	
	public boolean isPointInsideofTriangle(double x0, double y0, double Xa, double Ya, double Xb, double Yb, double Xc, double Yc, boolean isOnHypotenuse){

		double A = 0;
		double B = 0;
		double C = 0;
		

		A = (Xa - x0) * (Yb - Ya) - (Xb - Xa) * (Ya - y0);
		B = (Xb - x0) * (Yc - Yb) - (Xc - Xb) * (Yb - y0);
		C = (Xc - x0) * (Ya - Yc) - (Xa - Xc) * (Yc - y0);
		
		if (((A < 0 && B < 0 && C < 0) || (A > 0 && B > 0 && C > 0)) || isOnHypotenuse) {
			
			System.out.println(isOnHypotenuse);
			return true;
		} else {
			return false;
		}
		
		
	}
	
	
	public class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

	}

	public static void main(String[] args) {
		PointAndRectangleMain par = new PointAndRectangleMain();

	}
}
