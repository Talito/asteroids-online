package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TitleView extends JPanel {
	
	private JComponent innerView;
	
	/**
	 * Setup the title panel and add innerView beneath it. Acts as a Decorator.
	 * @param innerView the innerView.
	 */
	public TitleView(JComponent innerView) {
		this.setBorder(new EmptyBorder(100, 100, 200, 100) );
		this.setBackground(Color.black);
		this.setLayout( new BorderLayout() );
		this.add( getTitlePanel(), BorderLayout.PAGE_START );
		this.innerView = innerView;
		this.add(innerView, BorderLayout.CENTER);
	}
	
	/**
	 * Return the title panel, a JPanel with the "asteroids" printed with much grandeur.
	 * @return JPanel
	 */
	public JPanel getTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(25, 0, 25, 0) );
		titlePanel.setBackground(Color.black);
		titlePanel.setForeground(Color.white);
		JLabel label = new JLabel("A S T E R O I D S", JLabel.CENTER);
		label.setFont(new Font("Monospaced", Font.BOLD, 28));
		label.setForeground(Color.white);
		titlePanel.add(label);
		return titlePanel;
	}
	
}
