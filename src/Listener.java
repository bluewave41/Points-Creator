import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;


public class Listener implements MouseListener {
	
	static int id = -1;
	static Square temp;

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseButton = e.getButton();
		Square square = (Square)e.getComponent();
		if(Main.firstClick) { //on first click
			square.setBackground(Color.green); //set to green for center
			Main.center = square;
			Main.firstClick = false;
		}
		
		else if(!Main.lineMode) {
			if(square.clicked && mouseButton == 1) {
				square.z++;
				square.label.setText(Integer.toString(square.z));
			}
			else if(square.clicked && mouseButton == 3) {
				square.z--;
				square.label.setText(Integer.toString(square.z));
			}
			else {
				square.id = Main.id++;
				square.label.setText("0");
				square.label.setFont(new Font("Serif", Font.BOLD, 16));
				square.label.setLayout(new BorderLayout());
				square.setBackground(Color.red);
				Main.clicked.add(square);
				square.clicked = true;
			}
		}
		
		else {
			if(id == -1) {
				if(square.clicked) {
					id = square.id;
					square.setBackground(Color.blue);
					temp = square;
				}
			}
			else {
				if(square.clicked ){
					temp.setBackground(Color.red);
					Main.addLine(id, square.id);
					id = -1;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
