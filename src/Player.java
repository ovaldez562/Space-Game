import java.util.*;
/**
 * This class creates a Player object.
 */
public class Player implements java.io.Serializable{
	/** ID of the player, will be either 1, 2, or 3.*/
	private int id;
	/** Array list of planets that a player has.*/
	private ArrayList<Planet> planets;
	/**
	 * Constructor for a Player. 
	 * @param i ID determining whether it is a user or computer.
	 */
	public Player (int i) {
		id = i;
		planets = new ArrayList<Planet>();
	}
	/**
	 * Accessor for players ID
	 * @return Integer ID of player
	 */
	public int getId() {
		return id;
	}
	/**
	 * Mutator for player ID
	 * @param i Integer new player ID
	 */
	public void setId(int i) {
		id = i;
	}
	/**
	 * Method determines whether a planet has been claimed. 
	 * Is called in the sendShips method from Planet class.
	 * @param s
	 */
	public void claimPlanet(Planet s) {
			this.planets.add(s);
			s.setOwner(this);
	}
	/**
	 * Accessor for players arrayList of planets. 
	 * @return ArrayList of player's planets.
	 */
	public ArrayList<Planet> getList() {
		return planets;
	}
	
	

}
