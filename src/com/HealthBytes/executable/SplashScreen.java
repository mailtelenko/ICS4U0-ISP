package com.HealthBytes.executable;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * The SplashScreen class creates a screen to introduce the user to the program.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Krasteva, V.
 * 
 * @version 1.0
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class SplashScreen extends MenuParent implements MouseListener {

	/**
	 * Verify sender/receiver of object
	 */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	Game game;

	/**
	 * SplashScreen class constructor creates multiple JPanels to properly display
	 * the information on the window. It also adds a MouseListener to detect any
	 * clicks on the window to proceed to the main menu.
	 * 
	 * @param g
	 *            The game object to be used
	 * @param titleStr
	 *            The string to display on the screen
	 */
	public SplashScreen(Game g, String titleStr) {
		super(""); // Call to super with title and game
		game = g; // Set reference to game object

		// Add mouse listener
		this.addMouseListener(this);

		// Create game image
		ImageIcon logo = new ImageIcon(
				new ImageIcon("resources/images/splashscreen.png").getImage().getScaledInstance(800, 600, 10));
		JLabel logoLabel = new JLabel("", logo, JLabel.CENTER); // Centre in JLabel

		// Add container JPanel to the window
		add(logoLabel);
	}

	/**
	 * mouseClicked is triggered by the clicking of the mouse within the JPanel
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		game.window.getContentPane().removeAll(); // Remove all panels from JFrame
		game.window.getContentPane().add(new MainMenu(game)); // Add mainMenu to panels
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

	/**
	 * mouseEntered is triggered by the entering of the mouse within the JPanel
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	/**
	 * mouseExited is triggered by the exiting of the mouse within the JPanel
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	/**
	 * mousePressed is triggered by the clicking and holding of the mouse within the
	 * JPanel
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	/**
	 * mouseReleased is triggered by the release of the mouse within the JPanel
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	/**
	 * buttonClicked is not used in this class.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	@Override
	public void buttonClicked(ActionEvent e) {

	}

}
