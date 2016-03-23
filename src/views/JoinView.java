package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JoinView extends JPanel {
	
	private JButton leaveBtn;
	private JLabel msg;
	
	public JoinView() {
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		JLabel titleLabel = new JLabel("Waiting for the game to start", JLabel.CENTER);
		titleLabel.setBorder(new EmptyBorder(15,0,15,0));
		titleLabel.setFont(new Font("monospaced", Font.PLAIN, 18));
		
		this.add(titleLabel, BorderLayout.PAGE_START);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		msg = new JLabel("Waiting for the server.....");
		p.add(msg);
		
		this.leaveBtn = new JButton("Leave the game");
		leaveBtn.setVisible(false);
		p.add(leaveBtn);
		this.add(p, BorderLayout.CENTER);
		
	}
	
	public JButton getLeaveButton() {
		return leaveBtn;
	}
	
	public void setAccepted() {
		msg.setText("Accepted to the server. Waiting for starting...");
		msg.setForeground(Color.green);
		leaveBtn.setVisible(true);
	}
	
}
