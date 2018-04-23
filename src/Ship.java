import java.awt.*;
/**
 * This class creates a Ship object.
 */
public class Ship {
	/** Planet object that represents the source planet(Planet that will attack).*/
	private Planet source;
	/** Planet object that represents the destination planet(Planet that will be attacked)*/
	private Planet destination;
	/** Point object that represents where the ships will be in the GUI.*/
	private Point location;
	/** Player object that represents the owner.*/
	private Player owner;
	/** Number of ships */
	private int size;
	/** Numbers that will change the ships directory.*/
	private int dx = 1, dy = 1;
	/** Boolean that determines whether ship has reached destination.*/
	private boolean done = false;
	/**
	 * Constructor for a Ship object.
	 * @param so Source Planet
	 * @param d Destination Planet
	 * @param l Point of Planet
	 * @param o Owner of Planet
	 * @param s Size of Planet
	 */
	public Ship(Planet so, Planet d, Point l, Player o, int s) {
		source = so;
		destination = d;
		location = l;
		owner = o;
		size = s;
	}
	/**
	 * Method that draws the ship.
	 * @param g Graphics
	 */
	public void drawShip(Graphics g) {
		if(this.getSource().getOwner().getId() == 1) {
		g.setColor(Color.GREEN);
		}
		else {
			g.setColor(Color.RED);
		}
		g.fillOval(location.x, location.y, 5, 5);
		moveShip();
	}
	/**
	 * Accessor for destination Planet
	 * @return Planet destination.
	 */
	public Planet getDestination() {
		return destination;
	}
	/**
	 * Accessor for whether the ship has reached its intended target/planet.
	 * @return Boolean if done.
	 */
	public boolean destinationDone() {
		return done;
	}
	/**
	 * Mutator for destination Planet
	 * @param d
	 */
	public void setDestination(Planet d) {
		destination = d;
		
	}
	/**
	 * Accessor that gets the source of the ship.
	 * @return Planet soucre
	 */
	public Planet getSource () {
		return source;
	}
	/**
	 * Method that does the moving of ships. Determines what director the ship will travel.
	 * 
	 */
	public synchronized void moveShip() {
		if (destination.getPoint().x == 0) {

		} else {
			if (location.x < destination.getPoint().x + destination.getSize()
					/ 2) {
				dx = Math.abs(dx);
			} else if (location.x > destination.getPoint().x
					+ destination.getSize() / 2) {
				dx = -1;
			} else {
				dx = 0;
			}
			if (location.y < destination.getPoint().y + destination.getSize()
					/ 2 + 10) {
				dy = Math.abs(dy);
			} else if (location.y > destination.getPoint().y
					+ destination.getSize() / 2 + 10) {
				dy = -1;
			} else {
				dy = 0;
			}
			if (location.x == destination.getPoint().x + destination.getSize()
					/ 2
					&& location.y == destination.getPoint().y
							+ destination.getSize() / 2 + 10) {
				done = true;

			}
			location.x = location.x + dx;
			location.y = location.y + dy;
		}
	}
}
