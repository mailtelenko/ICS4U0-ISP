import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * The main Game class for Cyber Case. Controls program flow and JFrame.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Ms.Krasteva
 *
 *
 * @version 0.2
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class Game extends JFrame {

	/**
	 * Verify sender/receiver of object
	 */
	private static final long serialVersionUID = 1L;

	/** Panels for menus */
	public MainMenu mainMenu;
	/** JFrame */
	public JFrame window;
	/** Icon image for JFrame icon **/
	ImageIcon iconImg = new ImageIcon("resources/images/Logo.png");

	/**
	 * {@link Game} constructor. Creates a new JFrame for the main game window and
	 * sets the parameters of said window.
	 */
	public Game() {
		window = new JFrame("Cyber Case"); // Create JFrame window
		mainMenu = new MainMenu(this); // Create MainMenu JPanel

		// Setup JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
		window.getContentPane().setBackground(new Color(18, 24, 59)); // Set background colour
		window.setIconImage(iconImg.getImage()); // Set the icon image for the JFrame
		window.getContentPane().add(mainMenu); // Add mainMenu to window
		window.setSize(900, 700); // Set size
		window.setLocationRelativeTo(null); // Position in the center
		window.setVisible(true); // Display window
	}

	/**
	 * Creates a new Game object. Controls the flow of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Game(); // Create new instance of the Game class.
	}

}
