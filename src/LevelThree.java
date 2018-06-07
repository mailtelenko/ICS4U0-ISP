import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The LevelThree class extends LevelParent and is the third level in a series
 * of three. The level focuses on online safety as well as the dangers of
 * interacting with strangers.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class LevelThree extends LevelParent {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	private Game game;
	/** The container for the game image */
	private JPanel centerContainer;
	/** The text container for dialogue */
	private JPanel messageContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JLabel messages = new JLabel(" ");
	/** JButtons for users */
	private JButton user1, user2, user3;
	/** Current user being talked to **/
	private int currentUser = 1;
	/** Correct user to be closed */
	private int correctUser = 0;
	/** Dialog between user and player */
	private String[] dialog = new String[] { "", "", "" };
	/** All prompts for the player */
	private ArrayList<ArrayList<String[]>> prompts = new ArrayList<ArrayList<String[]>>();
	/** Initial greetings for users */
	private String[] greetings = new String[] { "Hello", "Hey", "Yo" };

	/**
	 * LevelOne class constructor sets the game reference to the game object.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public LevelThree(Game gme) {
		super(gme, "levelThree"); // Call to super with level
		game = gme; // Set reference to game object

		// JPanel to contain all elements
		centerContainer = new JPanel(new BorderLayout());

		// Set container
		centerContainer.setBackground(backgroundColor); // Set background colour
		centerContainer.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set border

		//Set messages font
		messages.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		
		// Set messageConatiner
		messageContainer.setBackground(Color.decode("#402644")); // Set background colour
		messageContainer.setBorder(new LineBorder(Color.BLACK, 7)); // Set border

		messages.setForeground(Color.WHITE); // Set text colour

		// Add messages (JLabel) to container
		messageContainer.add(messages);

		// Add all elements to container
		centerContainer.add(createUsers(), BorderLayout.NORTH); // User selection buttons
		centerContainer.add(messageContainer, BorderLayout.CENTER); // Message box
		centerContainer.add(new JPanel(), BorderLayout.SOUTH); // Temp JPanel to be removed

		// Set initial dialogs
		for (int x = 0; x < 3; x++) {
			dialog[x] = "User " + (x + 1) + ": " + greetings[x] + "<br/><br/>"; // Set dialog variable
		}

		// Add to JPanel
		add(createIntroduction("Three"), BorderLayout.CENTER); // Add description
	}

	/**
	 * createUsers creates the buttons at the top of the panel which cycle through
	 * the different conversations.
	 * 
	 * @return JPanel complete with three working JButtons (one for each user).
	 */
	public JPanel createUsers() {
		JPanel container = new JPanel(new FlowLayout()); // Create temporary container JPanel
		container.setBackground(backgroundColor); // Set background colour

		// Create buttons
		user1 = new JButton("User 1");
		user2 = new JButton("User 2");
		user3 = new JButton("User 3");

		// Set buttons (style + action listener)
		setButton(user1);
		setButton(user2);
		setButton(user3);

		// Add buttons to container
		container.add(user1);
		container.add(user2);
		container.add(user3);

		return container; // Return filled JPanel
	}

	/**
	 * fillPrompts randomizes the users and then inputs data from a file 
	 * specifically for each prompt, parses it, and then adds the parsed data 
	 * correctly to an ArrayList for use.
	 */
	public void fillPrompts() {
		int[] shuffleUsers = {1, 2, 3}; // Array of user numbers
		String temp; // Empty temporary string
		
		// Shuffle array of users
		for (int i = shuffleUsers.length - 1 ; i > 0 ; i--) {
			int index = (int) (Math.random() * (i + 1)); // Generate random user index
			int tem = shuffleUsers[i]; // Temporarily store current user
			shuffleUsers[i] = shuffleUsers[index];
			shuffleUsers[index] = tem;
		}
		
		for (int x = 0; x < 3; x++) {
			try { // Read from file
				// To identify the correct user to be closed
				if (shuffleUsers[x] == 2) {
					correctUser = x + 1;
				}
				// Open input from file
				BufferedReader dataIn = new BufferedReader(
						new FileReader("resources/data/levelThree/user" + (shuffleUsers[x]) + ".txt"));
				// While there are no empty lines in the file add the lines to the ArrayList
				prompts.add(new ArrayList<String[]>());
				while ((temp = dataIn.readLine()) != null) {
					// Add to ArrayList
					prompts.get(x).add(new String[] { temp.substring(0, temp.indexOf("/")),
							temp.substring(temp.indexOf("/") + 1) });
				}
				dataIn.close(); // Close connection to file
			} catch (Exception e) { // Catch exception
				System.out.println(e); // Print out exception
			}
		}
	}

	/**
	 * updateMessages updates the current conversation within the JPanel complete
	 * with HTML. It also calls updateButtons().
	 */
	public void updateMessages() {
		messages.setText("<html>" + dialog[currentUser - 1] + "</html>"); // Set current conversation text
		updateButtons(); // Update buttons
	}

	/**
	 * updateDialog adds two lines of dialog to the message window and then sets it
	 * to null.It then calls updateMessages().
	 * 
	 * @param x
	 *            The index of the dialog to be added to the JLabel.
	 */
	public void updateDialog(int x) {
		dialog[currentUser - 1] = dialog[currentUser - 1] + "You: " + prompts.get(currentUser - 1).get(x)[0]
				+ "<br/><br/>"; // Add player message
		dialog[currentUser - 1] = dialog[currentUser - 1] + "User " + (currentUser) + ": "
				+ prompts.get(currentUser - 1).get(x)[1] + "<br/><br/>"; // Add user message
		prompts.get(currentUser - 1).get(x)[0] = null; // Set to null
		updateMessages(); // Update the JLabel with new data
	}

	/**
	 * updateButtons updates the buttons which are displayed at the bottom of the
	 * screen. These allow the user to extract information from the "user" through
	 * conversation.
	 */
	public void updateButtons() {
		JPanel tempPanel = new JPanel(new FlowLayout()); // JPanel container to be returned
		ArrayList<JButton> tempArray = new ArrayList<JButton>(); // ArrayList of JButtons
		JButton exitButton = new JButton("Close User " + currentUser);
		
		for (int x = 0; x < prompts.get(currentUser - 1).size(); x++) { // Iterate through all prompts to fetch buttons
			if (prompts.get(currentUser - 1).get(x)[0] != null) { // Check if the prompt was set to null (question
																	// asked)
				tempArray.add(0, new JButton(prompts.get(currentUser - 1).get(x)[0])); // Add to tempArray of JButtons
				setButton(tempArray.get(0)); // Set button (style + action listener)
				// Set dimension of button (override)
				tempArray.get(0).setMinimumSize(new Dimension(250, 40));
				tempArray.get(0).setPreferredSize(new Dimension(250, 40));
				tempArray.get(0).setMaximumSize(new Dimension(250, 40));
				tempPanel.add(tempArray.get(0)); // Add to tempPanel
			}
		}
		
		setButton(exitButton);
		exitButton.setMinimumSize(new Dimension(250, 40));
		exitButton.setPreferredSize(new Dimension(250, 40));
		exitButton.setMaximumSize(new Dimension(250, 40));
		tempPanel.add(exitButton);
		
		// Set tempPanel size
		tempPanel.setMinimumSize(new Dimension(700, 100 * (prompts.get(currentUser - 1).size() / 3)));
		tempPanel.setPreferredSize(new Dimension(700, 100 * (prompts.get(currentUser - 1).size() / 3)));
		tempPanel.setMaximumSize(new Dimension(700, 100 * (prompts.get(currentUser - 1).size() / 3)));

		// Set tempPanel background colour
		tempPanel.setBackground(backgroundColor);

		// Remove old JPanel
		centerContainer.remove(((BorderLayout) centerContainer.getLayout()).getLayoutComponent(BorderLayout.SOUTH));

		// Add new JPanel to centerContainer
		centerContainer.add(tempPanel, BorderLayout.SOUTH);
	}

	/**
	 * checkButtons determines which button was clicked and then updates the
	 * messages accordingly.
	 * 
	 * @param clickedString
	 *            The text of the JButton clicked.
	 */
	public void checkButtons(String clickedString) {
		// Check if user closes a user's chat window
		if(clickedString.equals("Close User " + correctUser)) {
			finishGame(true);
			showInfoPane(new String[] { "Correct User", "You managed to identify the suspicious individual. Good job!" });
		} else if(clickedString.equals("Close User 1") || clickedString.equals("Close User 2") || clickedString.equals("Close User 3")) {
			showInfoPane(new String[] { "Incorrect User", "The selected user was not the suspicious individual and you let the culprit get away. Please retry the level." });
			finishGame(false);
			fillPrompts();
		}
		
		// Iterate over all prompts
		for (int x = 0; x < prompts.get(currentUser - 1).size(); x++) {
			// Check if prompt is not null (used) and if it is the correct prompt
			if (prompts.get(currentUser - 1).get(x)[0] != null
					&& prompts.get(currentUser - 1).get(x)[0].equals(clickedString))
				updateDialog(x); // Update the dialog with the correct prompt
		}
	}

	/**
	 * startGame removes the introduction and replaces it with the game image. It
	 * also starts the timer.
	 */
	public void startGame() {
		remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER)); // Remove description
		add(centerContainer, BorderLayout.CENTER); // Add game image
		fillPrompts(); // Fill ArrayLists with prompts
		updateMessages(); // Update the messages panel on the frame
		updateButtons(); // Update the buttons (bottom of screen)
		gameRunning = true; // Start timer
		start = new Date(); // Start recording time
	}

	/**
	 * finishGame displays a set of buttons once the level has ended. The method
	 * also stops the timer and controls the flow to the next panel.
	 * 
	 * @param win
	 *            Determines if the continue or retry button is displayed.
	 */
	public void finishGame(boolean win) {
		JPanel buttonCont = new JPanel(new FlowLayout()); // JPanel for button

		if (win)
			buttonCont.add(continueBtn);
		else {
			buttonCont.add(retryBtn);
		}
		
		buttonCont.setBackground(backgroundColor); // Set background colour
		
		centerContainer.remove(((BorderLayout) centerContainer.getLayout()).getLayoutComponent(BorderLayout.SOUTH));
		centerContainer.add(buttonCont, BorderLayout.SOUTH); // Add FlowLayout with buttons to panel

		game.window.validate();
		game.window.repaint();

		gameRunning = false; // Set game to stop running
		end = new Date(); // End recording time
		game.addTime(300000, (int) (end.getTime() - start.getTime())); // Add time difference to totalTime
		timer.cancel(); // Cancel timer
		timer.purge(); // Purge timer
	}

	/**
	 * buttonClicked checks which button has been clicked and responds
	 * appropriately. The method controls the flow of the level.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	@Override
	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource(); // Cast the ActionEvent as a JButton
		if (compare == retryBtn) {
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new LevelThree(game)); // Add mainMenu to panels
		} else if (compare == continueBtn) {
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new Quiz(game, 3)); // Add mainMenu to panels
		} else if (compare == closeDescription) { // If start game is pressed
			startGame(); // Start game
		} else if (compare == user1 && gameRunning) { // If first user button is clicked
			currentUser = 1; // Set user
			updateMessages(); // Update messages
		} else if (compare == user2 && gameRunning) { // If second user button is clicked
			currentUser = 2; // Set user
			updateMessages(); // Update messages
		} else if (compare == user3 && gameRunning) { // If third user button is clicked
			currentUser = 3; // Set user
			updateMessages(); // Update messages
		} else {
			checkButtons(compare.getText()); // Check if one of the prompt buttons was clicked
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

}
