package views.menu;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class HostMenuView extends SingleFormView {
	
	private JButton startBtn;
	private JSpinner playersSpinner;
	private JTextField portField;
	
	public HostMenuView() {
		super("The hosting of the game has started."); 
		
		setLayout(null);
		
		JLabel label1 = new JLabel( "Number of users:" );
		label1.setBounds( 10, 15, 150, 20 );
		this.add( label1 );

		this.playersSpinner = new JSpinner(new SpinnerNumberModel(2,2,9,1));
		this.playersSpinner.setBounds( 10, 45, 75, 20 );
		this.add( this.playersSpinner );
		
		JLabel label2 = new JLabel( "Port:" );
		label2.setBounds( 200, 15, 150, 20 );
		this.add( label2 );

		this.portField = new JTextField();
		this.portField.setBounds( 200, 45, 75, 20 );
		this.add( this.portField );
		
		
		this.startBtn = new JButton("Start hosting");
		this.startBtn.setBounds(10, 130, 250, 25);
		add(this.startBtn);
	}
	
	public void formatMessageLabel() {
		this.getMessageLabel().setBounds(10,90, 500, 20);
	}
	
	public void showSuccessMessage() {
		showSuccessMessage("Hosting of a game started!");
	}
	
	public JButton getStartButton() {
		return startBtn;
	}
	
	public int getNumberOfPlayers() {
		return (int) this.playersSpinner.getValue();
	}
	
	public Integer getPort() {
		try {
			return Integer.parseInt(this.portField.getText());
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
}
