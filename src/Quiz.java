import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The parent for all menu panels for the program.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Ms.Krasteva
 *
 *
 * @version 0.2
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class Quiz extends MenuParent {

	/**
	 * Verify sender/receiver of object
	 */
	private static final long serialVersionUID = 1L;
	/** The level which created the quiz object */
	int level;
	/** The current question number being displayed */
	int currentQuestion = 0;
	/** The amount of correct questions answered by the user. */
	int correctQuestions = 0;
	/** JButtons for use at the end of the quiz */
	JButton continueBtn, menu;
	/** Reference to game object */
	Game game;
	/** The label used to display the question to the user */
	JLabel questionLabel;
	/** Wrapper panel to be updated by multiple methods */
	JPanel wrapper;

	/** ArrayLists to store data being used in the quiz */
	ArrayList<String[]> questions = new ArrayList<String[]>();
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	ArrayList<Boolean> questionUsed = new ArrayList<Boolean>();

	/**
	 * The Quiz constructor correctly lays out a new JPanel (super call to
	 * MenuParent) and proceeds to fill it with a question label and answer buttons.
	 * 
	 * @param g
	 *            The game object being passed into the constructor.
	 * @param l
	 *            The level number which created the new Quiz object.
	 */
	public Quiz(Game g, int l) {
		super(g, "Level " + l + " Quiz"); // Call to super

		// Set instance variables
		game = g;
		level = l;

		// JPanel creation
		JPanel container = new JPanel(new GridBagLayout());
		JPanel questionContainer = new JPanel();
		wrapper = new JPanel(new BorderLayout());

		// Create questionLabel
		questionLabel = new JLabel("Testing string", SwingConstants.CENTER);

		// Style questionLabel
		questionLabel.setForeground(Color.WHITE); // Set text colour
		questionLabel.setFont((new Font("Cambria Math", Font.PLAIN, 20))); // Set font
		questionLabel.setBorder(new EmptyBorder(20,10,20,10));

		// Fill questions ArrayList from data (parsed)
		fillQuestions();

		questionContainer.setBackground(backgroundColor); // Set background colour of questionContainer
		questionContainer.add(questionLabel); // Add questionLabel to questionContainer

		// Add answer buttons to container
		container.add(createButtons());

		// Set background colours
		container.setBackground(backgroundColor);
		wrapper.setBackground(backgroundColor);

		// Add elements to wrapper
		wrapper.add(questionContainer, BorderLayout.NORTH);
		wrapper.add(container, BorderLayout.CENTER);

		add(wrapper); // Add wrapper to JPanel

		chooseQuestion(); // Choose first question
	}

	/**
	 * fillQuestions inputs data from a file specifically for each quiz, parses it,
	 * and then adds the parsed data correctly to an ArrayList for use in the Quiz
	 * object.
	 */
	public void fillQuestions() {
		String temp; // Empty temporary string
		String[] tempArray = new String[] { "", "" }; // Empty temporary array

		try { // Read from file
				// Open input from file
			BufferedReader dataIn = new BufferedReader(
					new FileReader("resources/data/levels/level" + level + "Quiz.txt"));
			// While there are no empty lines in the file add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {
				// Check if the first character is a '/'
				if (!temp.substring(0, 1).equals("/")) {
					tempArray[0] = temp.substring(0, temp.indexOf("/")); // Parse button
					tempArray[1] = temp.substring(temp.indexOf("/") + 1); // Parse question
					questions.add(new String[] { tempArray[0], tempArray[1] }); // Add to ArrayList
				} else { // If first character is '/'
					// Create 'fake' (no answer linked) button
					buttons.add(0, new JButton(temp.substring(1))); // Add to buttons (first element in ArrayList)
					setButton(buttons.get(0)); // Set button
				}
			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
	}

	/**
	 * createButtons method and adds the answer buttons to the JPanel centred on the
	 * screen.
	 * 
	 * @return JPanel complete with all styled and triggerable answer buttons.
	 */
	public JPanel createButtons() {
		// Create JPanels
		JPanel container = new JPanel(new FlowLayout());
		JPanel centredPanel = new JPanel(new GridBagLayout());

		container.setBackground(backgroundColor); // Set background colour of JPanel

		// For loop to iterate through all questions to add buttons
		for (int x = 0; x < questions.size(); x++) {
			buttons.add(x, new JButton(questions.get(x)[1])); // Add new button to ArrayList
			setButton(buttons.get(x)); // Set button
			questionUsed.add(false); // Create questionUsed value for answer.
		}

		// Shuffle buttons ArrayList
		java.util.Collections.shuffle(buttons);

		// Iterate over all buttons
		for (int x = 0; x < buttons.size(); x++) {
			container.add(buttons.get(x)); // Add button to container
		}

		// Set dimensions based off rows
		container.setMinimumSize(new Dimension(700, 150 * (questions.size() / 4)));
		container.setPreferredSize(new Dimension(700, 150 * (questions.size() / 4)));
		container.setMaximumSize(new Dimension(700, 150 * (questions.size() / 4)));

		// Add container to centred panel
		centredPanel.add(container);
		return centredPanel; // Return to JPanel
	}

	/**
	 * chooseQuestion finds a new, unused question to present to the user.
	 */
	public void chooseQuestion() {
		Boolean[] questionsChecked = new Boolean[questionUsed.size()]; // Array of used questions
		int checked = 0; // Amount of questions checked.
		int temp; // Temporary variable

		// Initialise questionsChecked to false.
		for (int x = 0; x < questionsChecked.length; x++)
			questionsChecked[x] = false;

		// Do-while to run every scenario (every question checked randomly)
		do {
			temp = (int) (Math.random() * (questionUsed.size())); // Set temp int to new question number (random)
			if (questionUsed.get(temp)) { // Check if that question has been used
				if (!questionsChecked[temp]) { // Add to checked amount and set to true if not already
					checked++; // Add one
					questionsChecked[temp] = true; // Set true
				}
			} else { // Question hasn't been used
				changeQuestion(temp); // Change the current question to the new question
				questionUsed.set(temp, true); // Set the new question to used
				currentQuestion = temp; // Update currentQuestion
				return; // Exit loop
			}
		} while (checked < questionUsed.size()); // While there are still elements left in the loop
		endQuiz(); // End quiz if no questions remain
	}

	/**
	 * setButton sets the correct size for the buttons and modifies their
	 * appearance. This method keeps consistency between the buttons of the program.
	 * 
	 * @param button
	 *            The button which is to be styled and attached to an action
	 *            listener.
	 */
	public void setButton(JButton button) {
		// Set size
		button.setPreferredSize(new Dimension(200, 40));
		button.setMinimumSize(new Dimension(200, 40));
		button.setMaximumSize(new Dimension(200, 40));

		// Style Buttons
		button.setFocusPainted(false);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.decode("#402644"));
		button.setFont(new Font("Cambria Math", Font.PLAIN, 12));

		// Add action listener to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClicked(e);
			}
		});
	}

	/**
	 * changeQuestion updates the questionLabel.
	 * 
	 * @param number
	 *            The number to update the question to.
	 */
	public void changeQuestion(int number) {
		questionLabel.setText(questions.get(number)[0]);
	}

	/**
	 * endQuiz calculates the final score and displays it to the user. It also
	 * controls where the user can proceed to next.
	 */
	public void endQuiz() {
		JPanel buttonCont = new JPanel(new FlowLayout()); //JPanel for button
		//Label for the percentage of correct answers
		JLabel percent = new JLabel("<html>" + "<center>You correctly answered "
				+ (int) (((double) correctQuestions / (double) questionUsed.size()) * 100)
				+ "% of the questions.</center>", SwingConstants.CENTER);
		//Label for explanation of why they cannot proceed.
		JLabel explanation = new JLabel(
				"<html>" + "<center>You correctly answered "
						+ (int) (((double) correctQuestions / (double) questionUsed.size()) * 100)
						+ "% of the questions.<br/>A 75% or greater is required to proceed.</center>" + "</html>",
				SwingConstants.CENTER);

		//Set buttons
		menu = new JButton("Main Menu"); //Create menu button
		continueBtn = new JButton("Continue"); //Create continue button
		super.setButton(continueBtn); //Set continue button
		super.setButton(menu); //Set menu button

		//Set labels
		percent.setFont(new Font("Cambria Math", Font.PLAIN, 30)); //Set font size
		percent.setForeground(Color.WHITE); //Set colour
		explanation.setFont(new Font("Cambria Math", Font.PLAIN, 20)); //Set font size
		explanation.setForeground(Color.WHITE); //Set colour

		//Remove all elements from wrapper panel
		wrapper.removeAll();
		//Add percent to wrapper panel
		wrapper.add(percent, BorderLayout.CENTER);

		//If the user has lost the quiz (< 75%) display the explanation JLabel
		if (((double) correctQuestions / (double) questionUsed.size()) < 0.75) {
			wrapper.add(explanation, BorderLayout.CENTER); //Replace percent label w/ explanation label
			buttonCont.add(menu); //Add main menu button to buttonCont
		} else {
			buttonCont.add(continueBtn); //Add continue button to buttonCont
		}

		//Set background colour
		buttonCont.setBackground(backgroundColor);

		//Add to wrapper
		wrapper.add(buttonCont, BorderLayout.SOUTH);
		
		//Update panel
		wrapper.revalidate();
		wrapper.repaint();

	}

	/**
	 * buttonClicked checks which button has been clicked and responds
	 * appropriately. The method controls the flow of the quiz.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public void buttonClicked(ActionEvent e) {
		//Check if title of button matches the current question answers
		if (((JButton) e.getSource()).getText().equals(questions.get(currentQuestion)[1])) {
			correctQuestions++; //Add one to correct answers
		} else if (((JButton) e.getSource()) == continueBtn) { //Check if button is continue
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new LevelTwo(game)); // Add mainMenu to panels
			//Update JFrame
			game.window.revalidate();
			game.window.repaint();
		} else if (((JButton) e.getSource()) == menu) { //Check if button is main menu
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new MainMenu(game)); // Add new MainMenu panel to JFrame
			//Update JFrame
			game.window.revalidate();
			game.window.repaint();
		}
		chooseQuestion(); //Choose a new question to display
	}

}
