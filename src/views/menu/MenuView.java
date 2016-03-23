package views.menu;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controllers.menu.MenuItemController;

public class MenuView extends JTabbedPane {
	
	private MenuItemController[] menuItems;
	
	/**
	 * Create a menu view, which is a JTabbedPane with MenuItemController's as tabs.
	 * @param items the menu items.
	 */
	public MenuView(MenuItemController[] items) {
		this.menuItems = items;
		this.setup();
	}
	
	public Color getForegroundAt(int index){  
		if(getSelectedIndex() == index) return Color.BLACK;  
    	return Color.WHITE;  
	}  
	
	/**
	 * Set the UI settings and inflate the Tabbed Pane.
	 */
	public void setup() {
		
		setUISettings();
	    
	    for(MenuItemController item : menuItems) {
	    	this.addTab(item.getName(), item.getView());
	    }
	    
	    /**
	     * Is there a message from a form at the view? Clear it!
	     */
	    this.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e) {
	    		if(getSelectedComponent() instanceof SingleFormView){
	    			( (SingleFormView) getSelectedComponent()).clearMessage();
	    		}
	    	}
	    });
	}
	
	private void setUISettings() {
		UIDefaults def = UIManager.getLookAndFeelDefaults();
        def.put( "Panel.background", Color.black);
        def.put( "TabbedPane.foreground", Color.WHITE );
        def.put( "TabbedPane.background", Color.BLACK );
        def.put( "TabbedPane.tabInsets", new Insets(10,30,10,30) );
        def.put( "TabbedPane.selectedTabPadInsets", new Insets(10,20,10,20) );
        def.put( "TabbedPane.selected", Color.WHITE);
	}

	
}
