import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Class that creates a thread for the client to use.
 * @author Oscar
 *
 */
public class NetThreadClient implements Runnable {
	/** Frame objects that creates a new game. Starts the space game.*/
	private Frame game = new Frame();
	ArrayList<Planet> galaxy = new ArrayList<Planet>();
	Thread t;
	Socket sock;
	/**
	 * Constructor for net thread client.
	 * @param s Socket
	 */
	NetThreadClient(Socket s) {
		sock = s;
		t = new Thread(this);
		t.start();
	}
	/**
	 * Run method that has read and write objects from and to the server.
	 * 
	 */
	@Override
	public void run() {
		try {
			while (true) {
				ObjectOutputStream outputStream = new ObjectOutputStream(
						sock.getOutputStream());
				galaxy = game.getPanel().getList();
				outputStream.writeObject(galaxy);// sends object
				int count = 0;
				for (int i = 0; i < 10; i ++)  {
					if(galaxy.get(i).getOwner().getId() == 1)
						count ++;
				}
				System.out.println("Number of User planets: " + count);
				ObjectInputStream inStream = new ObjectInputStream(
						sock.getInputStream());
				ArrayList<Integer> input = new ArrayList<Integer>();
				input = (ArrayList<Integer>) inStream.readObject();// receives object
				if (game.getPanel().getShipsList().isEmpty()) {// needs to send ships
					int xPos = galaxy.get(input.get(0)).getPoint().x
							+ galaxy.get(input.get(0)).getSize() / 2;
					int yPos = galaxy.get(input.get(0)).getPoint().y
							+ galaxy.get(input.get(0)).getSize() / 2 + 10;
					Point pos = new Point(xPos, yPos);
					Ship ship = new Ship(galaxy.get(input.get(0)), galaxy.get(input.get(1)),
							pos, galaxy.get(input.get(0)).getOwner(), galaxy.get(input.get(0))
									.getShips());
					game.getPanel().getShipsList().add(ship);
				}
				t.sleep(10000);// 10 sec

			}

		} catch (Exception e) {

		}

	}
}
                                        	
