import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The SplashScreen class creates a screen to introduce the user to the program.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class SplashScreen extends MenuParent implements MouseListener {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	private Game game;

	/**
	 * SplashScreen class constructor creates multiple JPanels to properly display
	 * the information on the window. It also adds a MouseListener to detect any
	 * clicks on the window to proceed to the main menu.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 * @param titleStr
	 *            The title to display on the screen.
	 */
	public SplashScreen(Game gme, String titleStr) {
		super(titleStr); // Call to super with title
		
		// Set instance variables
		game = gme; // Set reference to game object

		// Create JPanels
		JPanel container = new JPanel(new BorderLayout());
		JLabel info = new JLabel("Presented by:", SwingConstants.CENTER);
		JLabel clickContinue = new JLabel("Click anywhere to continue.", SwingConstants.CENTER);

		// Set fonts
		info.setFont(new Font("Cambria Math", Font.PLAIN, 22));
		clickContinue.setFont(new Font("Cambria Math", Font.PLAIN, 22));

		// Set border size
		info.setBorder(new EmptyBorder(70, 70, 70, 70));
		clickContinue.setBorder(new EmptyBorder(70, 0, 70, 0));

		// Set font colour
		info.setForeground(Color.WHITE);
		clickContinue.setForeground(Color.WHITE);

		// Add mouse listener
		this.addMouseListener(this);

		// Create game image
		ImageIcon logo = new ImageIcon(
				new ImageIcon("resources/images/logoSmall.png").getImage().getScaledInstance(174, 194, 10));
		JLabel logoLabel = new JLabel("", logo, JLabel.CENTER); // Centre in JLabel

		// Set background colour
		container.setBackground(backgroundColor);

		// Add items to container JPanel
		container.add(info, BorderLayout.NORTH);
		container.add(logoLabel, BorderLayout.CENTER);
		container.add(clickContinue, BorderLayout.SOUTH);

		// Add container JPanel to the window
		add(container);
	}

	/**
	 * mouseClicked is triggered by the clicking of the mouse within the JPanel
	 * 
	 * @param e	
	 *            Checks if an MouseEvent is made on the mouse.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		game.window.getContentPane().removeAll(); // Remove all panels from JFrame
		game.window.getContentPane().add(new MainMenu(game)); // Add mainMenu to panels
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

	/**
	 * mouseEntered is triggered by the entering of the mouse within the JPanel
	 * 
	 * @param e	
	 *            Checks if an MouseEvent is made on the mouse.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * mouseExited is triggered by the exiting of the mouse within the JPanel
	 * 
	 * @param e	
	 *            Checks if an MouseEvent is made on the mouse.
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * mousePressed is triggered by the clicking and holding of the mouse within the JPanel
	 * 
	 * @param e	
	 *            Checks if an MouseEvent is made on the mouse.
	 */
	@Override
	public void mousePressed(MouseEvent e) {}

	/**
	 * mouseReleased is triggered by the release of the mouse within the JPanel
	 * 
	 * @param e	
	 *            Checks if an MouseEvent is made on the mouse.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}

	/**
	 * buttonClicked is not used in this class.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	@Override
	public void buttonClicked(ActionEvent e) {}

}
