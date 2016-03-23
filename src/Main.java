import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class Main {
	
	static int GRIDSIZE = 0;
	
	static ArrayList<Square> clicked = new ArrayList<Square>();
	static ArrayList<Point> lines = new ArrayList<Point>();
	static int id = 1;
	static boolean lineMode = false;
	static boolean is3d = false;
	static JToggleButton line = new JToggleButton("Points mode");
	static boolean firstClick = true;
	static Square center;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		String value = (String)JOptionPane.showInputDialog(
                frame,
                "Enter size of grid: ");
		try {
			int size = Integer.parseInt(value);
			GRIDSIZE = size;
		}
		catch (Exception e) {
			System.exit(0);
		}
		frame.setTitle("Points Creator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		JPanel border = new JPanel(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		int top = GRIDSIZE;
		for(int x=0;x<GRIDSIZE;x++) {
			for(int y=0;y<GRIDSIZE;y++) {
				Square square = new Square(y, top);
				panel.add(square);
			}
			top--;
		}
		
		JPanel buttons = new JPanel();
		JCheckBox enable3d = new JCheckBox("3D");
		enable3d.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				is3d = !is3d;
			}
		});
		
		line.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(lineMode)
					line.setText("Points mode");
				else
					line.setText("Line mode");
				lineMode = !lineMode;
			}
		});
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					save();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Square s: clicked) {
					s.setBackground(Color.white);
					s.label.setText("");
				}
				center.setBackground(Color.white);
				center = null;
				firstClick = true;
				clicked.clear();
				lines.clear();
				id = 1;
			}
		});
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				undo();
			}
		});
		buttons.add(enable3d);
		buttons.add(line);
		buttons.add(save);
		buttons.add(clear);
		buttons.add(undo);
		
		border.add(panel, BorderLayout.CENTER);
		border.add(buttons, BorderLayout.PAGE_START);
		frame.add(border);
		frame.setVisible(true);
	}
	
	public static void save() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("new.points", "UTF-8");
		writer.println(center.x + " " + center.y + " " + '0'); //print middle point
		for(Square s : clicked) { //print each point
			writer.println(s.x + " " + s.y + " " + s.z);
		}
		
		if(is3d) {
			for(Square s : clicked) { //print each 3D point
				writer.println(s.x + " " + s.y + " " + s.z*-1);
			}
		}
		writer.write("-1");
		writer.flush();
		writer.close();
		
		writer = new PrintWriter("new.lines", "UTF-8");
		for(Point p : lines) { //print each line
			writer.println(p.x + " " + p.y);
		}
		
		if(is3d) {
			for(Point p : lines) { //print second shape
				writer.println((int)(p.x+clicked.size()) + " " + (int)(p.y+clicked.size()));
			}
		
			for(int i=1;i<clicked.size()+1;i++) { //print lines connecting shapes
				writer.println(i + " " + (int)(clicked.size()+i));
			}
		}
		writer.write("-1");
		writer.flush();
		writer.close();
		
	}
	
	public static void addLine(int p1, int p2) {
		Point p = new Point(p1, p2);
		lines.add(p);
	}
	
	public static void lineMode() {
		lineMode = !lineMode;
	}
	
	public static void undo() {
		if(clicked.size() > 0) {
			clicked.get(clicked.size()-1).setBackground(Color.white);
			clicked.remove(clicked.size()-1);
		}
	}
}