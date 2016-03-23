import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Square extends JPanel {
	int x, y, z;
	int id;
	boolean clicked = false;
	JLabel label = new JLabel();
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		this.add(label);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setBackground(Color.white);
		addMouseListener(new Listener());
	}
}
