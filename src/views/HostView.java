package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.MultiplayerGame;
import models.Player;

public class HostView extends JPanel implements Observer {
	
	private MultiplayerGame mpg;
	private JButton abortBtn = null;
	private JButton startBtn = null;
	private JPanel playerPanel = null;
	private JPanel actionPanel = null;
	
	public HostView(MultiplayerGame mpg) {
		
		this.setLayout(new BorderLayout());
		this.mpg = mpg;
		mpg.addObserver(this);
		
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		JLabel titleLabel = new JLabel("host a game", JLabel.CENTER);
		titleLabel.setBorder(new EmptyBorder(15,0,15,0));
		titleLabel.setFont(new Font("monospaced", Font.PLAIN, 18));
		this.add(titleLabel, BorderLayout.PAGE_START);
		
		generatePlayerPanel();
		generateActionPanel();
		
	}

	private void generatePlayerPanel() {
		if(playerPanel != null) {
			this.remove(playerPanel);
		}
		playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		playerPanel.add(new JLabel("Players: "  + this.mpg.getPlayers().size() + "/" + this.mpg.getNumPlayers()));
		for(Player p : this.mpg.getPlayers()) {
			JLabel l = new JLabel(p.getName());
			l.setForeground(p.getColor());
			playerPanel.add(l);
		}
		playerPanel.setBorder(new EmptyBorder(20,50,20,20));
		this.add(playerPanel, BorderLayout.LINE_START);
		
	}
	
	private void generateActionPanel() {
		if(actionPanel != null) {
			this.remove(actionPanel);
		}
		actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.add(new JLabel("Actions"));
		
		actionPanel.add(this.getAbortButton());
		
		if(this.mpg.getPlayers().size() == this.mpg.getNumPlayers()) {
			actionPanel.add(this.getStartButton());
		}
		
		actionPanel.add(new JLabel("Spectators: " + this.mpg.getSpectators().size()));

		actionPanel.setBorder(new EmptyBorder(20,20,20,50));
		this.add(actionPanel, BorderLayout.LINE_END);
	}
	
	public JButton getAbortButton() {
		if(this.abortBtn == null) {
			this.abortBtn = new JButton("Abort");
		}
		return this.abortBtn;
	}
	
	public JButton getStartButton() {
		if(this.startBtn == null) {
			this.startBtn = new JButton("Start");
		}
		return this.startBtn;
	}
	
	public void update(Observable obs, Object obj) {
		generatePlayerPanel();
		generateActionPanel();
		validate();
		repaint();
	}
	
}
