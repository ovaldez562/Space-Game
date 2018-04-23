import java.net.*;
import java.util.ArrayList;
import java.io.*;
/**
 * Class that creates a Client.
 * @author Oscar
 *
 */
public class Client {
	/**
	 * This main connects client to a server. Also creates a net thread client
	 * which allows the client to write and read objects.
	 */
	public static void main(String[] args) {
		Socket s = null;
		try {
			s = new Socket("localhost", 1025);
			System.out.println("Connected");
			NetThreadClient c = new NetThreadClient(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
