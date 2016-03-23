package views.menu;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public  abstract class SingleFormView extends JPanel {

	private JLabel msgLabel = new JLabel();
	private String successMsg = "Form saved correctly!";
	
	public SingleFormView(String successMsg) {
		this.successMsg = successMsg;
		this.formatMessageLabel();
	}
	
	public abstract void formatMessageLabel();

	private void showColoredMessage(String msg, Color color) {
		this.add(this.getMessageLabel());
		this.msgLabel.setText(msg);
		this.msgLabel.setForeground(color);
	}
	
	public void clearMessage() {
		this.msgLabel.setText("");
	}
	
	public void showSuccessMessage() {
		showColoredMessage(this.successMsg, Color.GREEN);
	}
	
	public void showSuccessMessage(String msg) {
		showColoredMessage(msg, Color.GREEN);
	}
	
	public void showErrorMessage(String error) {
		showColoredMessage(error, Color.RED);
	}
	
	public void showWarningMessage(String msg) {
		showColoredMessage(msg, Color.ORANGE);
	}
	
	public JLabel getMessageLabel() {
		return this.msgLabel;
	}
	
}
