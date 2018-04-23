import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class that creates a thread for the Server to use.
 * @author Oscar
 *
 */
public class NetThreadServer implements Runnable {
	Thread t;
	Socket sock;
	/**
	 * Constructor for the net thread server.
	 * @param s Socket
	 */
	NetThreadServer(Socket s) {
		sock = s;
		t = new Thread(this);
		t.start();
	}
	/**
	 * Run method that reads and write from and to the client.
	 * This run method also call a method that makes a move for the AI.
	 */
	@Override
	public void run() {
		try {
			
			while (true) {
				ObjectInputStream inStream = new ObjectInputStream
						(sock.getInputStream());
				ArrayList<Planet> pl = new ArrayList<Planet>();
				pl = ((ArrayList<Planet>) inStream.readObject());// received object
				ArrayList<Integer> send = new ArrayList<Integer>();
				send = getAiAttack(pl);
				ObjectOutputStream outStream = new ObjectOutputStream
						(sock.getOutputStream());
				outStream.writeObject(send);
			}
			
			
		}
		catch (Exception e) {
			
		}
		
		
	}
	/**
	 * Method that sets up a move for the computer. It randomly gets a computer planet
	 * and if the computer that less than 3 planets, it will attack the biggest empty planet. Else 
	 * it will randomly attack a planet in the galaxy.
	 * @param l Array list of planets in the galaxy.
	 * @return Array list of what the computer will attack.
	 */
	public  ArrayList<Integer> getAiAttack(ArrayList<Planet> galaxy) {
		Planet Ai = null;
		Planet max = null;
		int maxPlanetIndex = 0;
		int randomIndex;
		int count = 0;
		Random rand = new Random();
		ArrayList<Integer> move = new ArrayList<Integer>(); 
		randomIndex = rand.nextInt(10);
		Ai = galaxy.get(randomIndex);
		while (Ai.getOwner().getId() != 2) {// find computer planet
			randomIndex = rand.nextInt(10);
			Ai = galaxy.get(randomIndex);
		}
		for (int i = 0; i < 10; i++) {// gets for many planets the computer owns
			if (galaxy.get(i).getOwner().getId()  == 2) {
				count ++;
			}
		}
		move.add(randomIndex);
		System.out.println("Number of Computer planets: " + count);
		if (count < 3) {// if comp owns less than 3 planets, attack biggest empty
			for (int i = 0; i < 10; i++) {
				if (galaxy.get(i).getOwner().getId() == 3) {
					if (max == null) {
						max = galaxy.get(i);
						maxPlanetIndex = i;
					} else if (galaxy.get(i).getSize() > max.getSize()) {
						max = galaxy.get(i);
						maxPlanetIndex = i;
						
					}

				}
				
			}
			
		} 
		else {
			randomIndex = rand.nextInt(10);
			max = galaxy.get(randomIndex);
			maxPlanetIndex = randomIndex;
			while (max.getOwner().getId() == 2) {// looks for a empty or user
				randomIndex = rand.nextInt(10);
				max = galaxy.get(randomIndex);
				maxPlanetIndex = randomIndex;
			}
		}
		move.add(maxPlanetIndex);
		return move;

	}
}



