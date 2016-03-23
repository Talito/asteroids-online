package views.menu;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JoinMenuView extends SingleFormView {

	private JTextField hostField;
	private JTextField portField;
	private JButton joinBtn;
	private JButton spectateBtn;
	
	public JoinMenuView() {
		super("Action successfull.");
		setLayout(null);
	
		JLabel label1 = new JLabel( "Host:" );
		label1.setBounds( 10, 15, 100, 20 );
		this.add( label1 );

		this.hostField = new JTextField();
		hostField.setText("localhost");
		this.hostField.setBounds( 10, 45, 250, 20 );
		this.add( this.hostField );
		
		JLabel label2 = new JLabel( "Port:" );
		label2.setBounds( 280, 15, 150, 20 );
		this.add( label2 );

		this.portField = new JTextField();
		this.portField.setBounds( 280, 45, 75, 20 );
		portField.setText("8800");
		this.add( this.portField );
		
		
		this.joinBtn = new JButton("Join");
		this.joinBtn.setActionCommand("join");
		this.joinBtn.setBounds(10, 130, 150, 25);
		add(this.joinBtn);
		
		this.spectateBtn = new JButton("Spectate");
		this.spectateBtn.setActionCommand("spectate");
		this.spectateBtn.setBounds(175, 130, 150, 25);
		add(this.spectateBtn);
	
	}
	
	public void formatMessageLabel() {
		this.getMessageLabel().setBounds(10,90, 500, 20);
	}
	
	public JButton getSpectateButton() {
		return this.spectateBtn;
	}
	
	public JButton getJoinButton() {
		return this.joinBtn;
	}
	
	public String getHost() {
		return this.hostField.getText();
	}
	
	public Integer getPort() {
		try {
			return Integer.parseInt(this.portField.getText());
		} catch(NumberFormatException e) {
			return null;
		}
	}

}
