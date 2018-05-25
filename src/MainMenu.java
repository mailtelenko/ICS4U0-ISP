import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The MainMenu class presents the user with four options to progress in the program. These options
 * are new game, rules, high scores, or exit. Each button leads to a new panel (except for ext which
 * terminates the program).
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class MainMenu extends MenuParent {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Referance to Game object */
	private Game game;
	/**
	 * Buttons to: Create new game (setup), show rules, show highscores, exit
	 * program
	 */
	private JButton newGame, rules, highScores, exit;

	/**
	 * MainMenu class constructor sets the buttons correctly, and then adds them to
	 * itself (JPanel).
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public MainMenu(Game gme) {
		super(gme, "Cyber Case"); // Call to parent with game object
		game = gme; // Set object's game object

		// Add to MainMenu panel
		add(createButtons(), BorderLayout.CENTER);
	}

	/**
	 * createButtons method creates a the four menu buttons (newGame, rules,
	 * highScores, exit) and then correctly formats and returns a functional JPanel.
	 */
	private JPanel createButtons() {
		JPanel buttons = new JPanel(); // Create button panel
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Set layout of buttons panel to box layout

		JPanel wrapper = new JPanel(new GridBagLayout()); // Create wrapper panel (to house buttons panel)

		// Create new JButtons
		newGame = new JButton("New Game");
		rules = new JButton("Rules");
		highScores = new JButton("High Scores");
		exit = new JButton("Exit");

		// Tweak/format buttons
		setButton(newGame);
		setButton(rules);
		setButton(highScores);
		setButton(exit);

		// Add buttons & vertical spacers to inner panel (buttons)
		buttons.add(newGame);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(rules);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(highScores);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(exit);

		wrapper.setBackground(backgroundColor); // Set background colour

		wrapper.add(buttons); // Add inner panel (buttons) to wrapper panel (wrapper)

		return wrapper; // Return final wrapped panel
	}

	/**
	 * buttonClicked checks if Continue button is clicked
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource(); // Cast the ActionEvent as a JButton
		game.window.getContentPane().removeAll(); // Remove all panels from JFrame
		if (compare == newGame) // Check if the button is the same as newGame
			game.window.getContentPane().add(new Setup(game));
		else if (compare == highScores) // Check if the button is the same as highScores
			game.window.getContentPane().add(new HighScores(game));
		else if (compare == rules) // Check if the button is the same as rules
			game.window.getContentPane().add(new Rules(game));
		else // Only possible event left is exitProgram button
			System.exit(0); // Exit program :(

		// Validate/repaint
		game.window.validate();
		game.window.repaint();
	}

}