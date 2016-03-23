package views.menu;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import models.Player;

public class SettingsMenuView extends SingleFormView implements Observer {
	
	public static String[] COLOR_NAMES 	= {	"blue", "cyan", "gray", "green", "magenta", 
											"orange", "pink", "red", "white", "yellow"};
	public static Color[] COLORS 		= { Color.blue, Color.cyan, Color.gray, Color.green,
											Color.magenta, Color.orange, Color.pink, 
											Color.red, Color.white, Color.yellow };
	
	private JButton saveBtn;
	private JTextField nameField;
	private JRadioButton[] colorBtns;
	private JPanel colorPanel;
	
	private Player p;
	
	public SettingsMenuView(Player p) {
		super("The settings are saved.");
		
		this.p = p;
		p.addObserver(this);
		
		this.setLayout(null);

		JLabel label1 = new JLabel( "Username:" );
		label1.setBounds( 10, 15, 150, 20 );
		this.add( label1 );

		this.nameField = new JTextField();
		this.nameField.setText(p.getName());
		this.nameField.setBounds( 10, 45, 250, 20 );
		this.add( nameField );

		JLabel label2 = new JLabel( "Color:" );
		label2.setBounds( 290, 15, 150, 20 );
		this.add( label2 );
		
		this.colorPanel = getColorPicker();
		this.add(this.colorPanel);
		
		this.saveBtn = new JButton("Save");
		this.saveBtn.setBounds(220,235, 100, 30);
		this.add(this.saveBtn);
	}
	
	public void formatMessageLabel() {
		this.getMessageLabel().setHorizontalAlignment(JLabel.CENTER);
		this.getMessageLabel().setBounds(0,210, 600, 15);
	}
	
	private JPanel getColorPicker() {
		JPanel cp = new JPanel();
		colorBtns = new JRadioButton[COLORS.length];
		ButtonGroup group = new ButtonGroup();
		for(int i = 0; i < COLORS.length; i++) {
			JRadioButton rb = new JRadioButton(COLOR_NAMES[i]);
			rb.setActionCommand(new Integer(i).toString());
			rb.setSelected(COLORS[i].equals(p.getColor()));
			rb.setForeground(COLORS[i]);
			colorBtns[i] = rb;
			group.add(rb);
			cp.add(rb);
		}
		cp.setBounds(290, 45, 200, 150);
		return cp;
	}
	
	public JButton getSaveButton() {
		return saveBtn;
	}
	
	public String getUsername() {
		return nameField.getText();
	}
	
	public Color getColor() {
		for(JRadioButton btn : colorBtns) {
			if(btn.isSelected()) {
				return COLORS[(int) Integer.parseInt(btn.getActionCommand())];
			}
		}
		return null;
	}
	
	public void update(Observable obs, Object o) {
		this.nameField.setText(p.getName());
		this.remove(colorPanel);
		this.colorPanel = getColorPicker();
		this.add(this.colorPanel);
	}
	
	public void update() {
		update(null, null);
	}
	
}