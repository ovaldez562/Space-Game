/**
 * Class that makes the panel
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Panel extends JPanel implements MouseListener, Runnable {
	/** Player object that represents the user/human */
	private Player human = new Player(1);
	/** Player object that represents the computer */
	private Player comp = new Player(2);
	/** Player object that represents neither a user or computer */
	private Player empty = new Player(3);
	/** Planet object */
	private Planet planet = new Planet();
	/** Array list of planets that represents all the planets in the galaxy */
	private ArrayList<Planet> galaxy = new ArrayList<Planet>();
	/**
	 * Array list of ships that represent how many ship are to be sent to a
	 * planet
	 */
	private ArrayList<Ship> shipsSend = new ArrayList<Ship>();
	/** Number of clicks done by the user */
	private int clicks = 0;
	/** Number of ships that will be sent from and to a planet */
	private int ships;
	/** Thread for redrawing the GUI */
	private Thread reDraw;
	/** Thread for the regeneration of planets HP */
	private Thread regen;

	/**
	 * Panel constructor; creates the entire galaxy, making sure that planets do
	 * no over lap. It also creates threads for the regeneration of planets HP
	 * and for the re-drawing of the GUI (re-draws galaxy).
	 */
	public Panel() {
		setBackground(Color.black);
		Random size = new Random();
		Point point = new Point(size.nextInt(576), size.nextInt(576));
		Planet p = new Planet(100, point, 5 * 25, human);
		galaxy.add(p);
		point = new Point(size.nextInt(576), size.nextInt(576));
		Planet pComp = new Planet(100, point, 5 * 25, comp);
		while (pComp.getPoint().x <= (galaxy.get(0).getPoint().x + galaxy.get(0)
				.getSize())
				&& (pComp.getPoint().x >= (galaxy.get(0).getPoint().x)
						- pComp.getSize())
				&& pComp.getPoint().y <= (galaxy.get(0).getPoint().y + galaxy
						.get(0).getSize())
				&& (pComp.getPoint().y >= (galaxy.get(0).getPoint().y - pComp
						.getSize()))) {
			point = new Point(size.nextInt(576), size.nextInt(576));
			pComp = new Planet(100, point, 5 * 25, comp);

		}
		galaxy.add(pComp);
		/** Logic that makes sure planets do not overlap */
		for (int i = 0; i < 8; i++) {
			point = new Point(size.nextInt(576), size.nextInt(576));
			p = new Planet(20, point, ((size.nextInt(5) + 1) * 25), empty);
			for (int j = 0; j < galaxy.size(); j++) {
				while (p.getPoint().x <= (galaxy.get(j).getPoint().x + galaxy
						.get(j).getSize())
						&& (p.getPoint().x >= (galaxy.get(j).getPoint().x)
								- p.getSize())
						&& p.getPoint().y <= (galaxy.get(j).getPoint().y + galaxy
								.get(j).getSize())
						&& (p.getPoint().y >= (galaxy.get(j).getPoint().y - p
								.getSize()))) {
					point = new Point(size.nextInt(576), size.nextInt(576));
					p = new Planet(20, point, ((size.nextInt(5) + 1) * 25), empty);
					j = 0;
				}
			}
			galaxy.add(p);
		}
		p = galaxy.get(0);
		human.claimPlanet(p);
		p = galaxy.get(1);
		comp.claimPlanet(p);
		addMouseListener(this);
		reDraw = new Thread(this);
		regen = new Thread() {
			public void run() {
				while (true) {
					regenPop();
					try {
						Thread.sleep(5000);// regenerates HP every 5 sec
					} catch (InterruptedException e) {

					}
				}
			}
		};
		reDraw.start();
		regen.start();

	}

	/**
	 * This method draws the planets in the galaxy. Will also check to see
	 * whether the user or computer has won.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Planet p : galaxy) {
			p.drawPlanet(g);
		}
		for (int i = 0; i < shipsSend.size(); i++) {
			shipsSend.get(0).drawShip(g);
			if (shipsSend.get(i).destinationDone()) {
				if (shipsSend.get(0).getSource().getOwner().getId() == 1) {// user
																								// attacking
					if (shipsSend
							.get(i)
							.getDestination()
							.sendShips(shipsSend.get(i).getSource().totalShips(),
									human) == true) {
						if (comp.getList()
								.contains(shipsSend.get(i).getDestination())) {
							comp.getList().remove(shipsSend.get(i).getDestination());

						}
					}
					shipsSend.remove(i);

				} else {// comp animation
					shipsSend.get(i).getDestination().sendShips(shipsSend.get(0).
							getSource().totalShips(), comp);
					shipsSend.remove(i);
				}

			}
		}
		if (human.getList().size() == galaxy.size()) {
			human.getList().remove(0);
			JOptionPane.showMessageDialog(null, "You Win!");
			System.exit(0);
		} else if (comp.getList().size() == galaxy.size()) {
			comp.getList().remove(0);
			JOptionPane.showMessageDialog(null, "The computer has won. You lost");
			System.exit(0);
		}

	}

	/**
	 * This method regenerates the planets HP; Will only regenerate user/computer
	 * planets.
	 */
	public void regenPop() {
		for (Planet p : galaxy) {
			if (p.getOwner().getId() == 1 || p.getOwner().getId() == 2) {
				p.regen();
			}
		}
	}

	/**
	 * This method returns all the planets in the galaxy in an array list.
	 * 
	 * @return Array List of the galaxy.
	 */
	public ArrayList<Planet> getList() {
		return galaxy;
	}

	/**
	 * This method returns the ships that will need to be sent.
	 * 
	 * @return Array List of ships.
	 */
	public ArrayList<Ship> getShipsList() {
		return shipsSend;
	}

	/**
	 * This method returns the Player object that represents the user .
	 * 
	 * @return Player object that represents a user/human
	 */
	public Player getP1() {
		return human;
	}

	/**
	 * This method returns the Player object that represents the computer
	 * 
	 * @return
	 */
	public Player getP2() {
		return comp;
	}

	@Override
	/**
	 * Thread that re-draws the planets every 6 m/s.
	 */
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	/**
	 * Method that determines the users "move". Determines what planet the user 
	 * has clicked and which planet the user wants to send ships to. 
	 */
	public void mouseClicked(MouseEvent evnt) {
		int xpos = evnt.getX();
		int ypos = evnt.getY();
		if (clicks == 0) {
			for (Planet p : galaxy) {
				if (p.getOwner().getId() == 1) {
					if (xpos >= p.getPoint().x
							&& xpos <= (p.getPoint().x + p.getSize())
							&& ypos >= p.getPoint().y
							&& ypos <= (p.getPoint().y + p.getSize())) {
						if (shipsSend.isEmpty()) {
							clicks++;
							ships = p.getShips();
							xpos = p.getPoint().x + p.getSize() / 2;
							ypos = p.getPoint().y + p.getSize() / 2 + 10;
							Point po = new Point(xpos, ypos);
							Ship ship = new Ship(p, planet, po, human, ships);
							shipsSend.add(ship);
						}
					}
				}
			}
		} else if (clicks == 1) {
			for (Planet r : galaxy) {
				if (xpos >= r.getPoint().x
						&& xpos <= (r.getPoint().x + r.getSize())
						&& ypos >= r.getPoint().y
						&& ypos <= (r.getPoint().y + r.getSize())) {
					if (!shipsSend.isEmpty()) {
						clicks++;
						shipsSend.get(0).setDestination(r);
					}
				}
			}
		}
		if (clicks >= 2) {
			clicks = 0;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
