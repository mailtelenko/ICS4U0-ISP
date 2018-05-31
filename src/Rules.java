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

	/** Verify sender/receiver of object. */
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
		super(gme, "Rules"); // Call to super with game object
		game = gme; // Set object's game object

		JLabel instructions = new JLabel(); // Text label
		instructions.setText("<html>"
				+ "<center>There are three levels in this game, completing the preceding level will allow you to continue to the next. In each level, you are tasked to solve a case before you run out of time. There will be objects around the “room” on screen which should be clicked on to gain the necessary information to solve the case.<br/><br/>When the time runs out, you will be presented with a quiz which must be answered using the information you gathered from the room. A minimum of 75% is required on the quiz to proceed to the next level.<br/><br/>When exploring the rooms, be careful where you click, as incorrect clicks will negatively affect your score.<br/><br/> Click on New Game to begin level one. Have fun!</center>"
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