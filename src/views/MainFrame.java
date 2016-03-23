package views;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import controllers.menu.MenuController;

public class MainFrame extends JFrame {
	
	private static MainFrame instance = null;
	private MenuController menuController = new MenuController();
	private JPanel curView = null;
	
	protected MainFrame() {
		this.setTitle("AsteroidsGame");
		this.setSize(800, 800);
		this.setResizable(false);
		
		setUI();
        
        this.menuController = new MenuController();
        
        // Makes sure to quit the application when closing the window.
     	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	this.add(this.menuController.getView());
   		this.setVisible(true);   
	}
	
	public void switchToMenu() {
		switchToView(this.menuController.getView());
		this.validate();
		this.repaint();
	}
	
	public void switchToView(JPanel newView){
		this.remove(this.menuController.getView());
		if(curView != null && !curView.equals(this.menuController.getView())) {
			this.remove(curView);
		}
		this.add(newView);
		curView = newView;
		this.validate();
		this.repaint();
	}
	
	public void switchToSingleplayerGame() {
		
	}
	
	private void setUI() {
		setUIFont (new javax.swing.plaf.FontUIResource(new Font("Monospaced",Font.PLAIN, 12)));
		UIDefaults def = UIManager.getLookAndFeelDefaults();
        def.put("Panel.background", Color.black);
        def.put("RadioButton.background", Color.black);
        def.put("Label.foreground", Color.white);
	}
	
	private static void setUIFont(javax.swing.plaf.FontUIResource f)
	{
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements())
	    {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof javax.swing.plaf.FontUIResource)
	        {
	            UIManager.put(key, f);
	        }
	    }
	}
	
	
	/**
	 * Singleton
	 */
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
}
