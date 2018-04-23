import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Class creates a frame object. Which calls upon the Panel class. 
 * @author Oscar
 *
 */
public class Frame extends JFrame {
	/** Panel object*/
	private Panel l = new Panel();
	/**
	 * Constructor for a frame
	 */
	public Frame() {
		
		super("Galaxy Conquest");
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(l);
		setVisible(true);
	}
	/**
	 * Method to get the frames panel
	 * @return Panel object that is used for the game.
	 */
	public Panel getPanel () {
		return l;
	}
}
