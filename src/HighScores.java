import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * The leaderboards of the game. Displays the highest ranking players from a
 * list stored in resources.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Ms.Krasteva
 *
 *
 * @version 0.2
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class HighScores extends MenuParent {

	/**
	 * Verify sender/receiver of object
	 */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	private Game game;
	/** Label to be filled with names and scores */
	private JLabel scoresLabel;
	/** Menu and erase high scores button */
	private JButton menu, erase;
	/** ArrayList containing unparsed data */
	ArrayList<String> scores;

	/**
	 * Class constructor set up the JPanel and stores the unparsed data in scores.
	 * 
	 * @param gme The game refernce to be used by the object.
	 */
	public HighScores(Game gme) {
		super(gme, "High Scores"); // Call to super with game object
		game = gme; // Store reference to game object

		JPanel scorePanel = new JPanel(new GridBagLayout()); // Create centred container for scores

		scores = readScores(); // Read scores into ArrayList
		String allScores = ""; // All scores string
		if (scores.size() > 0) { // Check if there are scores within the ArrayList
			for (int x = 0; x < scores.size(); x++) { // Iterate over each name
				// Add each name & score to allScores
				allScores += (x + 1) + ". " + scores.get(x).substring(0, scores.get(x).indexOf(",")) + " .......... "
						+ scores.get(x).substring(scores.get(x).indexOf(",") + 1) + "<br/>";
			}
		} else { // Nothing in file
			allScores = "No highscores available."; // Change string to no scores available
		}

		scorePanel.add(scoresLabel = new JLabel("<html>" + allScores + "</html>")); // Add JLabel to panel
		scoresLabel.setFont(new Font("Cambria Math", Font.PLAIN, 18)); // Change font
		scoresLabel.setForeground(Color.WHITE); // Set font colour

		scorePanel.setBackground(backgroundColor); // Set background colour
		add(scorePanel); // Add scorePanel to object

		// Add to MainMenu panel
		add(createButtons(), BorderLayout.SOUTH); // Add bottom buttons to Panel
	}

	/**
	 * createButtons method creates a mainMenu button to return to mainMenu
	 */
	private JPanel createButtons() {
		JPanel buttons = new JPanel(); // Panel for buttons
		erase = new JButton("Erase"); // Erase button
		JPanel wrapper = new JPanel(new GridBagLayout()); // Wrapper panel for buttons

		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Set JPanel layout
		buttons.setBackground(backgroundColor); // Set background colour

		// Create & style buttons
		setButton(erase);
		menu = createCenterMenu(buttons);

		// Add buttons to dual button JPanel
		createDualButtons(buttons, menu, erase);

		wrapper.setBackground(backgroundColor);// Set background colour

		// Add buttons to wrapper panel
		wrapper.add(buttons);
		return wrapper; // Return wrapper panel
	}

	/**
	 * readScores opens a connection to the highScores.txt file and reads the information into the 
	 * scores ArrayList.
	 * 
	 * @return
	 */
	private ArrayList<String> readScores() {
		ArrayList<String> input = new ArrayList<String>(); // Create empty ArrayList
		String temp; // Empty temporary string
		try {
			//Open input to file
			BufferedReader dataIn = new BufferedReader(new FileReader("resources/data/highScores.txt"));
			// While there are no empty lines add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {
				input.add(temp); //Add to ArrayList
			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
		return input; // Return ArrayList
	}

	/**
	 * eraseScores opens two connections to the highScores.txt file, one read and one write. The method reads until it
	 * finds no more lines. As it reads it erases the line afterwards.
	 */
	private void eraseScores() {
		try {
			//Open input to file
			BufferedReader dataIn = new BufferedReader(new FileReader("resources/data/highScores.txt"));
			//Open output to file
			PrintWriter dataOut = new PrintWriter(new FileWriter("resources/data/highScores.txt")); 
			
			// While there are no empty lines erase the lines
			while ((dataIn.readLine()) != null) {
				dataOut.println(""); // Output nothing
			}
			dataIn.close(); // Close data in
			dataOut.close(); // Close data out
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
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
			game.window.getContentPane().add(game.mainMenu); // Add mainMenu to panels
		} else {
			if (JOptionPane.showConfirmDialog(game.window, "Are you sure you want to erase all high scores?",
					"Confirm Erase", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) { // Confirm erase
																										// with user
				eraseScores(); // Erase scores
				game.window.getContentPane().removeAll(); // Remove all panels from JFrame
				game.window.getContentPane().add(new HighScores(game)); // Add new HighScores panel to JFrame
			}
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

}