import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Class that creates a Planet object.
 * @author Oscar
 *
 */
public class Planet implements java.io.Serializable {
	/** Number of population that a Planet will have.*/
	private int population;
	/** Point object that represents where the Planet is in the galaxy(GUI panel).*/
	private Point location = new Point(); 
	/** Member integer variable that represents the size of the planet.*/
	private int size;
	/** Player object that represents who owns the planet.*/
	private Player owner;
	/** Number of ships that a planet will send to another*/
	private int ships;
	/**
	 * Constructor for a planet
	 * @param p Population of planet
	 * @param l Location/Point in "space"
	 * @param s Size of planet
	 * @param o Player that owns the planet
	 */
	public Planet(int p, Point l, int s, Player o) {
		population = p;
		location = l;
		size = s;
		owner = o;
	}
	/**
	 * This methods purpose is to set x location of planet to 0.
	 * Is used to help with the sending of ships.
	 */
	public Planet() {
		location.x = 0;
	}
	/**
	 * Accessor for owner of planet.
	 * @return Player of the planet.
	 */	
	public Player getOwner() {
		return owner;
	}
	/**
	 * Mutator of planet's owner
	 * @param p Owner of planet
	 */
	public void setOwner(Player p) {
		owner = p;
	}
	/**
	 * Method that sends ships to planet. Either attacks or assist other planet.
	 * @param ships Number of ships/population
	 * @param p Owner of planet
	 */
	public boolean sendShips(int ships, Player p) {
		if (owner.getId() == p.getId()) {
			population = population + ships;
			return false;
		}
		else {
			if (population > ships) {
				population = population - ships;
				return false;			}
			else {
				ships = ships - population;
				p.claimPlanet(this);
				population = ships;
				return true;
			}
		}
	}
	/**
	 * Accessor for population of planet.
	 * @return Integer population.
	 */	
	public int getPopulation() {
		return population;
	}
	/**
	 * Mutator for population on planet.
	 * @param p Integer population.
	 */
	public void setPopulation(int p) {
		population = p;
	}
	/**
	 * Accessor for planets location as a Point object.
	 * @return Point location.
	 */
	public Point getPoint() {
		return location;
	}
	/**
	 * Accessor for planets size.
	 * @return Integer planet size.
	 */
	public int getSize() {
		return size;
	}
	/**
	 * Method that draws and fills in a planet.
	 * Light_Gray being alien planet, Green being user planet, Red being computer
	 * planet.
	 * @param g Graphics
	 */
	public void drawPlanet(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(location.x, location.y, size, size);
		g.setColor(Color.LIGHT_GRAY);
		if (owner.getId() == 1) {
			g.setColor(Color.GREEN);
			
		}
		else if (owner.getId() == 2) {
			g.setColor(Color.RED);
		}
		g.fillOval(location.x, location.y, size, size);
		g.setColor(Color.BLACK);
		g.drawString("" + population, location.x + size/2-7, location.y + size/2+7);
	}
	/**
	 * Method that regenerates pop depending if the size has been capped or not.
	 */
	public void regen() {
		if(this.getPopulation() < size) {
			population = population + size/5;
		}
		if(this.getPopulation() > size) {
			population = size;
		}
	}
	/**
	 * Method that gets the number of ships that a planet will send.
	 * @return Integer number of ships/population.
	 */
	public int getShips() {
		ships = population*75/100;
		population = population - ships;
		return ships;
	}
	/**
	 * Method that returns the 'final' amount of ships that will be sent.
	 * The thread call upon getShips() method multiple times, this method
	 * helps return the correct number of ships. 
	 * @return Integer number of ships that will be sent
	 */
	public int totalShips() {
		return ships;
	}
	

}
