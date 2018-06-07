import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The Setup class prompts the user to enter a name input.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class Setup extends MenuParent {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Stores name of user */
	private String name;
	/** Button to continue to Level One */
	private JButton cont;
	/** Textfield to receive user input on screen */
	private JTextField textField;
	/** Reference to Game object */
	private Game game;

	/**
	 * Setup class constructor creates layout of setup screen.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public Setup(Game gme) {
		super("Setup"); // Call to parent with title

		// Set instance variables
		game = gme; // Set object's game object

		game.resetClicks();

		JPanel container = new JPanel(new GridBagLayout()); // Create container for text field

		JLabel prompt = new JLabel(); // Create input label
		prompt.setText("<html>" + "Please enter your name:<br/>" + "</html>"); // Add text to label
		prompt.setFont(new Font("Cambria Math", Font.PLAIN, 14)); // Set font of prompt
		prompt.setForeground(Color.WHITE); // Set text colour
		container.add(prompt); // Add to container
		container.add(Box.createHorizontalStrut(10)); // Add horizontal spacing between label/input

		textField = new JTextField(30); // Set text field
		// Create coloured border
		textField
				.setBorder(new CompoundBorder(new LineBorder(Color.decode("#402644"), 3), new EmptyBorder(5, 5, 5, 5)));
		textField.setFont(new Font("Cambria Math", Font.PLAIN, 14)); // Set font

		container.add(textField); // Add text field to container

		container.setBackground(backgroundColor); // Set background colour

		// Add to Setup panel
		add(container, BorderLayout.CENTER);
		add(createButtons(), BorderLayout.SOUTH);

		// Add cursor to text field (invoke later required or else skipped over)
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textField.requestFocusInWindow(); // Request focus
			}
		});

		// Validate/repaint due to possible popup
		game.window.validate();
		game.window.repaint();
		
		checkHighscores(); // Check if high scores file is valid
	}

	/**
	 * checkHighscores determines if the current directory set in the @link{Game}
	 * class is a valid path to a highscores.txt file.
	 */
	private void checkHighscores() {
		// Check if the CyberCase\highscores.txt file exists in the directory specified
		if (!(new File(game.getHighScoresPath() + "\\CyberCase\\highscores.txt")).exists()) {
			// Check if the Game class still has the default directory (Desktop) set
			if (game.getHighScoresPath().equals(
					System.getenv("SystemDrive") + "\\users\\" + System.getProperty("user.name") + "\\Desktop")) {
				// Set buttons to specific text
				Object[] buttons = { "Create default", "Choose new location / select existing location" };
				// Show warning message with options
				if (JOptionPane.showOptionDialog(game.window,
						"The highscores file was not located in it's default location:\n'" + game.getHighScoresPath()
								+ "'\nWould you like to generate the file in the default directory?",
						"Highscores Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons,
						null) == 0) {
					// If the user decides to generate in the default location
					generateFiles();
				} else {
					// If the user decides to choose their own location
					changeHighscoresLocation();
				}
			}
		}

	}

	/**
	 * changeHighscoresLocation changes the path of the highscores.txt parent
	 * directory within the @link{Game} class using a GUI file chooser.
	 */
	private void changeHighscoresLocation() {
		JFileChooser fileChooser = new JFileChooser(); // Create new file chooser

		// Setup file chooser
		fileChooser.setDialogTitle("Choose CyberCase directory:"); // Title bar
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only select directories
		fileChooser.setAcceptAllFileFilterUsed(false); // Cannot change what is selected

		// Path to be grabbed
		int path = fileChooser.showOpenDialog(null);

		// Check if the path is a valid option
		if (path == JFileChooser.APPROVE_OPTION) {
			// Set the path in the Game class
			game.setHighScoresPath(fileChooser.getSelectedFile().getAbsolutePath());
		}

		// Generate structure in specified directory
		generateFiles();
	}

	/**
	 * generateFiles creates the CyberCase/highscores.txt structure inside the
	 * specified folder within the @link{Game} class.
	 */
	private void generateFiles() {
		File path; // Path to be generated in

		// Check if the specified path already has a CyberCase folder in it
		if (game.getHighScoresPath().substring(game.getHighScoresPath().length() - 9).equals("CyberCase"))
			path = new File(game.getHighScoresPath() + "\\highscores.txt"); // Add only text file to path
		else
			// Add both text file and folder to path
			path = new File(game.getHighScoresPath() + "\\CyberCase\\highscores.txt");

		// Create directories missing within the path (CyberCase)
		path.getParentFile().mkdirs();

		// Try to generate the highscores.txt file.
		try {
			path.createNewFile(); // Create file
		} catch (IOException e) {
			// If no write permissions are allowed show error message
			JOptionPane.showMessageDialog(game.window,
					"The required files could not be generated in the chosen directory.\nPlease select a different location.",
					"Highscores Error", JOptionPane.ERROR_MESSAGE); // Display error message to user
			e.printStackTrace(); // Print stack
			changeHighscoresLocation(); // Force user to choose different directory
		}
	}

	/**
	 * createButtons method creates a Continue button to Level One
	 */
	private JPanel createButtons() {
		JPanel buttons = new JPanel(); // Create button panel
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Set layout of buttons panel to box layout

		JPanel wrapper = new JPanel(new GridBagLayout()); // Create wrapper panel (to house buttons panel)

		cont = new JButton("Continue"); // Set cont button to "Continue"

		setButton(cont); // Tweak button

		// Add buttons to inner panel (buttons)
		buttons.add(cont);
		buttons.add(Box.createVerticalStrut(10));

		wrapper.setBackground(backgroundColor); // Set background colour
		buttons.setBackground(backgroundColor); // Set background colour

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
		JButton compare = (JButton) e.getSource(); // Cast event to button
		if (compare == cont) { // Check if the button is the same as continue
			if ((name = textField.getText()).length() < 1 || textField.getText().equals(" ")) { // Check if name is
																								// valid
				JOptionPane.showMessageDialog(game.window, "Please enter a name before continuing.", "Enter name",
						JOptionPane.ERROR_MESSAGE); // Display error message to user
			} else {
				game.setName(name);
				game.resetClicks();
				game.resetTime();
				game.resetAnswers();
				game.window.getContentPane().removeAll(); // Remove all panels from frame
				game.window.getContentPane().add(new LevelOne(game)); // Add level to frame
			}
		}
		// Validate/repaint
		game.window.validate();
		game.window.repaint();
	}
}