import java.net.*;
/**
 * Class that creates a Server
 * @author Oscar
 *
 */
public class Server {
	/**
	 * Main the creates a socket and connects to client. Also creates a net thread
	 * server that allows computer to read and write objects. 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket server;
		Socket sock = null;
		try {
			server = new ServerSocket(1025);
			System.out.println("Waiting...");
			sock = server.accept();
			System.out.println("Connected: " + sock.getInetAddress());
			NetThreadServer s = new NetThreadServer(sock);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}