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
 * The Results class displays the results of the game.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class Results extends MenuParent {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Creates menu and high scores button */
	private JButton menu, highScores;
	/** Reference to Game object */
	private Game game;
	/** Label to be filled with final results */
	private JLabel resultsLabel;
	/** The total accumulated time from each level */
	private int totalTime;
	/** The total accumulated incorrect answers from each level */
	private int incorrectAnswers;
	/** The total accumulated incorrect clicks from each level */
	private int incorrectClicks;

	/**
	 * Results class constructor creates layout of results screen.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public Results(Game gme, int t, int ia, int ic) {
		super("Results"); // Call to super with title
		
		// Set instance variables
		game = gme; // Set reference to game object
		totalTime = t;
		incorrectAnswers = ia;
		incorrectClicks = ic;
		
		JPanel resultsPanel = new JPanel(new GridBagLayout()); // Create centered container for results
		
		resultsPanel.add(resultsLabel = new JLabel("<html>"
				+ "<center>Final Score: XXX<br/><br/><br/><br/>Total Time: " + totalTime + " seconds<br/><br/>" + incorrectAnswers + " Incorrect Answers<br/><br/>" + incorrectClicks + " Incorrect Clicks</center>"
				+ "</html>"));
		
		resultsLabel.setBorder(new EmptyBorder(30, 80, 10, 80)); // Set border
		resultsLabel.setFont(new Font("Cambria Math", Font.PLAIN, 18));// Set font
		resultsLabel.setForeground(Color.WHITE);// Set font colour
		
		resultsPanel.setBackground(backgroundColor); // Set background colour
		
		add(resultsPanel); // Add instructions to JPanel
		add(createButtons(), BorderLayout.SOUTH); // Add buttons to bottom of panel
	}

	/**
	 * createButtons method creates a mainMenu button to return to mainMenu,
	 * and a highScores button to view high scores.
	 */
	private JPanel createButtons() {
		JPanel buttons = new JPanel(); // Panel for buttons
		highScores = new JButton("High Scores"); // High Scores button
		JPanel wrapper = new JPanel(new GridBagLayout()); // Wrapper panel for buttons

		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Set JPanel layout
		buttons.setBackground(backgroundColor); // Set background colour

		// Create & style buttons
		setButton(highScores);
		menu = createCenterMenu(buttons);

		// Add buttons to dual button JPanel
		createDualButtons(buttons, menu, highScores);

		wrapper.setBackground(backgroundColor);// Set background colour

		// Add buttons to wrapper panel
		wrapper.add(buttons);
		return wrapper; // Return wrapper panel
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
		else if (compare == highScores) { // Check if the clicked button is the same object as highScores button
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new HighScores(game)); // Add highScores to panels
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}
	
}