import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The Rules class displays the rules of the game.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class Rules extends MenuParent {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Creates menu button */
	private JButton menu;
	/** Reference to Game object */
	private Game game;

	/**
	 * Setup class constructor creates layout of setup screen.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public Rules(Game gme) {
		super("Rules"); // Call to super with title
		game = gme; // Set object's game object

		JLabel instructions = new JLabel(); // Text label
		instructions.setText("<html>"
				+ "<center>Welcome to Cyber Case presented by Health Bytes!<br/><br/>There are three levels in this game. There will be a detailed description provided before you are tasked to play each level. Only by completing the preceding level will you be able to continue to the next. In each level, you are tasked to solve a case before 5 minutes is up. If time runs out, you will be forced to restart the level, which will negatively affect your score.<br/><br/>In each level, there will be objects on screen which should be clicked on, which will provide you with the necessary information to solve the case. Only after completing the task, you will be presented with a quiz which must be answered using the information you gathered from the level. A minimum of 75% is required on the quiz to proceed to the next level. If the standard is not achieved, you will be forced to replay the level, which will affect your score negatively.<br/><br/>When exploring the rooms, be careful where you click, as incorrect clicks will negatively affect your score.<br/><br/>Click on New Game to begin level one. Have fun!</center>"
				+ "</html>"); // Set text to instructions
		instructions.setBorder(new EmptyBorder(30, 80, 10, 80)); // Set border
		instructions.setFont(new Font("Cambria Math", Font.PLAIN, 14));// Set font
		instructions.setForeground(Color.WHITE);// Set font colour
		add(instructions, BorderLayout.CENTER); // Add instructions to JPanel
		add(createButtons(), BorderLayout.SOUTH); // Add buttons to bottom of panel
	}

	/**
	 * createButtons method creates a mainMenu button to return to mainMenu
	 */
	private JPanel createButtons() {
		JPanel buttons = new JPanel(); // JPanel for buttons
		JPanel wrapper = new JPanel(new GridBagLayout()); // Wrapper for buttons

		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Set JPanel layout

		// Create & style buttons
		menu = createCenterMenu(buttons);

		wrapper.setBackground(backgroundColor); // Set background colour

		// Add buttons to wrapper panel
		wrapper.add(buttons);
		return wrapper; // Return wrapper panels
	}

	/**
	 * buttonClicked checks if a button has been clicked and responds appropriately.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource(); // Cast the ActionEvent as a JButton
		if (compare == menu) { // Check if the clicked button is the same object as menu button
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new MainMenu(game)); // Add mainMenu to panels
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

}