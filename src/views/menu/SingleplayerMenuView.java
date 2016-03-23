package views.menu;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SingleplayerMenuView extends JPanel {

	private JButton startBtn;
	
	public SingleplayerMenuView() {
		startBtn = new JButton("Start a SinglePlayer game");
		this.add(startBtn);
	}
	
	public JButton getStartButton() {
		return startBtn;
	}
	
}
