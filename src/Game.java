import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 * The main Game class for Cyber Case. Controls program flow and JFrame.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Ms.Krasteva
 *
 * @version 0.2
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class Game extends JFrame {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Game window */
	public JFrame window;
	/** Icon image for JFrame icon */
	private ImageIcon iconImg = new ImageIcon("resources/images/Logo.png");
	/** Total and correct amount of clicks */
	private int totalClicks, correctClicks = 0;
	/** Name of player */
	private String name;

	/**
	 * Game class constructor. Creates a new JFrame for the main game window and
	 * sets the parameters of said window.
	 */
	public Game() {
		window = new JFrame("Cyber Case"); // Create JFrame window
		SplashScreen splash = new SplashScreen(this, "");

		// Setup JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
		window.getContentPane().setBackground(new Color(18, 24, 59)); // Set background colour
		window.setIconImage(iconImg.getImage()); // Set the icon image for the JFrame
		window.getContentPane().add(splash); // Add mainMenu to window
		window.setSize(900, 700); // Set size
		window.setLocationRelativeTo(null); // Position in the center
		window.setVisible(true); // Display window
	}
	
	/**
	 * addClick adds one to totalClicks if a click was made, and one to correctClicks
	 * only if a correct click has been made.
	 * 
	 * @param correct
	 *            To check whether the click was correct.
	 */
	public void addClick(boolean correct) {
		if(correct)
			correctClicks++;
		totalClicks++;
	}
	
	/**
	 * resetClicks resets the number of clicks to zero.
	 */
	public void resetClicks() {
		totalClicks = 0;
		correctClicks = 0;
	}
	
	/**
	 * getPercentClicks returns the percentage of correct clicks.
	 * 
	 * @return percent of correct clicks
	 */
	public int getPercentClicks() {
		return (int) (((double) correctClicks / (double) totalClicks) * 100);
	}
	
	/**
	 * setName sets the user's name.
	 * 
	 * @param n	
	 *            To store the user's name.
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * getName returns the user's name.
	 * 
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creates a new Game object. Controls the flow of the program.
	 * 
	 * @param args	
	 *            Java command line arguments
	 */
	public static void main(String[] args) {
		new Game(); // Create new instance of the Game class.
	}

}
