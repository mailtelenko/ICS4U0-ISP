package com.HealthBytes.executable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * The creates a quiz panel with working buttons and questions. Will not allow
 * user to proceed if their score is less than 75%.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Krasteva, V.
 *
 * @version 1.0
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class Quiz extends MenuParent {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** The level which created the quiz object */
	private int level;
	/** The current question number being displayed */
	private int currentQuestion = 0;
	/** The number of questions correctly answered by the user. */
	private int correctAnswers = 0;
	/** JButtons for use at the end of the quiz */
	private JButton continueBtn, retryBtn;
	/** Reference to game object */
	private Game game;
	/** The label used to display the question to the user */
	private JLabel questionLabel;
	/** Wrapper panel to be updated by multiple methods */
	private JPanel wrapper;
	/** JPanel to house buttons */
	private JPanel buttonContainer;

	/** ArrayLists to store data being used in the quiz */
	private ArrayList<String[]> questions = new ArrayList<String[]>();
	private ArrayList<JButton[]> buttons = new ArrayList<JButton[]>();

	/**
	 * The Quiz constructor correctly lays out a new JPanel (super call to
	 * MenuParent) and proceeds to fill it with a question label and answer buttons.
	 * 
	 * @param gme
	 *            The game object being passed into the constructor.
	 * @param l
	 *            The level number which created the new Quiz object.
	 */
	public Quiz(Game gme, int l) {
		super("Level " + l + " Quiz"); // Call to super with title

		// Set instance variables
		game = gme;
		level = l;

		// JPanel creation
		JPanel container = new JPanel(new GridBagLayout());
		JPanel questionContainer = new JPanel();

		wrapper = new JPanel(new BorderLayout());

		// Create questionLabel
		questionLabel = new JLabel("Testing string", SwingConstants.CENTER);
		questionLabel.setMinimumSize(new Dimension(750, 200));
		questionLabel.setPreferredSize(new Dimension(750, 200));
		questionLabel.setMaximumSize(new Dimension(750, 200));

		// Style questionLabel
		questionLabel.setForeground(Color.WHITE); // Set text colour
		questionLabel.setFont((new Font("Cambria Math", Font.PLAIN, 20))); // Set font
		questionLabel.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Fill questions ArrayList from data (parsed)
		if (l == 1)
			fillQuestions("levelOne");
		else if (l == 2)
			fillQuestions("levelTwo");
		else if (l == 3)
			fillQuestions("levelThree");

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

		changeQuestion(-1); // Set question to 0 (will add one in method)
	}

	/**
	 * fillQuestions inputs data from a file specifically for each quiz, parses it,
	 * and then adds the parsed data correctly to an ArrayList for use in the Quiz
	 * object.
	 * 
	 * @param levelName
	 *            The String representation of the level number.
	 */
	public void fillQuestions(String levelName) {
		String temp; // Empty temporary string

		try { // Read from file
				// Open input from file
			BufferedReader dataIn = new BufferedReader(
					new FileReader("resources/data/" + levelName + "/level" + level + "Quiz.txt"));
			// While there are no empty lines in the file add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {
				// Add to ArrayList
				questions.add(
						new String[] { temp.substring(0, temp.indexOf("/")), temp.substring(temp.indexOf("/") + 1) });
			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
	}

	/**
	 * createButtons method and adds the answer buttons to the JPanel centered on
	 * the screen.
	 * 
	 * @return JPanel complete with all styled and triggerable answer buttons.
	 */
	public JPanel createButtons() {
		// Create JPanels
		buttonContainer = new JPanel(new FlowLayout());
		JPanel centredPanel = new JPanel(new GridBagLayout());

		// Temporary variables for parsing
		String temp;
		ArrayList<JButton> tempArray;
		JButton[] buttonArray;

		buttonContainer.setBackground(backgroundColor); // Set background colour of JPanel

		// For loop to iterate through all questions to add buttons
		for (int x = 0; x < questions.size(); x++) {
			int count = 0; // Set counter to 0
			tempArray = new ArrayList<JButton>(); // Initialize tempArray
			temp = questions.get(x)[1]; // Get the current question

			// Run while temp is not null
			while (temp != null) {
				if (temp.indexOf('/') != -1) { // Check if there is a '/' in the string
					// Add to tempArray (create JButton)
					tempArray.add(new JButton(temp.substring(0, temp.indexOf('/'))));
					temp = temp.substring(temp.indexOf('/') + 1); // Update temp string
				} else {
					// Add JButton of just temp (no parsing)
					tempArray.add(new JButton(temp));
					temp = null; // Set temp to null
				}
				// Set button (styling/ActionListener)
				setButton(tempArray.get(count));
				count++; // Add to count
			}

			// Set the question and answer
			questions.set(x, new String[] { questions.get(x)[0], tempArray.get(0).getText() });

			// Shuffle buttons ArrayList
			java.util.Collections.shuffle(tempArray);

			// Create array of correct length
			buttonArray = new JButton[tempArray.size()];

			// Add each element from ArrayList to array
			for (int y = 0; y < tempArray.size(); y++) {
				buttonArray[y] = tempArray.get(y);
			}

			// Add array to buttons ArrayList
			buttons.add(buttonArray);
		}

		// Set dimensions based off rows
		buttonContainer.setMinimumSize(new Dimension(700, 150 * (questions.size() / 4)));
		buttonContainer.setPreferredSize(new Dimension(700, 150 * (questions.size() / 4)));
		buttonContainer.setMaximumSize(new Dimension(700, 150 * (questions.size() / 4)));

		// Add container to centered panel
		centredPanel.add(buttonContainer);
		return centredPanel; // Return to JPanel
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
		button.setPreferredSize(new Dimension(300, 40));
		button.setMinimumSize(new Dimension(300, 40));
		button.setMaximumSize(new Dimension(300, 40));

		// Style Buttons
		button.setFocusPainted(false);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.decode("#402644"));
		button.setFont(new Font("Cambria Math", Font.PLAIN, 12));

		// Add action listener to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create sound
				try {
					// Open connection to file
					AudioInputStream audioInputStream = AudioSystem
							.getAudioInputStream(new File("resources/sound/buttonClick.wav").getAbsoluteFile());
					// Get audio clip
					Clip clip = AudioSystem.getClip();
					// Open stream and start sound clip
					clip.open(audioInputStream);
					clip.start();
				} catch (Exception excep) {
					// Print error
					System.out.println(excep);
				}

				buttonClicked(e);
			}
		});
	}

	/**
	 * changeQuestion updates the questionLabel and the buttons in buttonContainer.
	 * 
	 * @param number
	 *            The number to update the question to.
	 */
	public void changeQuestion(int number) {
		// Set label
		if (number + 1 >= questions.size()) {
			endQuiz(); // End quiz
			return; // Break
		}
		currentQuestion = number + 1; // Update question number
		// Update label
		questionLabel.setText("<html>" + "<center>" + questions.get(currentQuestion)[0] + "</center>" + "</html>");
		// Remove buttons from container
		buttonContainer.removeAll();
		// Replace buttons in container
		for (int x = 0; x < buttons.get(currentQuestion).length; x++) {
			buttonContainer.add(buttons.get(currentQuestion)[x]);
		}
	}

	/**
	 * endQuiz calculates the final score and displays it to the user. It also
	 * controls where the user can proceed to next.
	 */
	public void endQuiz() {
		JPanel buttonCont = new JPanel(new FlowLayout()); // JPanel for button
		// Label for the percentage of correct answers
		JLabel percent = new JLabel("<html>" + "<center>You correctly answered "
				+ (int) (((double) correctAnswers / (double) questions.size()) * 100) + "% of the questions.</center>",
				SwingConstants.CENTER);
		// Label for explanation of why they cannot proceed.
		JLabel explanation = new JLabel(
				"<html>" + "<center>You correctly answered "
						+ (int) (((double) correctAnswers / (double) questions.size()) * 100)
						+ "% of the questions.<br/>A 75% or greater is required to proceed.</center>" + "</html>",
				SwingConstants.CENTER);

		// Set buttons
		continueBtn = new JButton("Continue"); // Create continue button
		retryBtn = new JButton("Retry"); // Create retry button

		super.setButton(continueBtn); // Set continue button
		super.setButton(retryBtn); // Set retry button

		// Set labels
		percent.setFont(new Font("Cambria Math", Font.PLAIN, 30)); // Set font size
		percent.setForeground(Color.WHITE); // Set colour
		explanation.setFont(new Font("Cambria Math", Font.PLAIN, 20)); // Set font size
		explanation.setForeground(Color.WHITE); // Set colour

		// Remove all elements from wrapper panel
		wrapper.removeAll();
		// Add percent to wrapper panel
		wrapper.add(percent, BorderLayout.CENTER);

		// If the user has lost the quiz (< 75%) display the explanation JLabel
		if (((double) correctAnswers / (double) questions.size()) < 0.75) {
			wrapper.add(explanation, BorderLayout.CENTER); // Replace percent label w/ explanation label
			buttonCont.add(retryBtn); // Add retry button to buttonCont
		} else {
			buttonCont.add(continueBtn); // Add continue button to buttonCont
		}

		// Set background colour
		buttonCont.setBackground(backgroundColor);

		// Add to wrapper
		wrapper.add(buttonCont, BorderLayout.SOUTH);

		// Update panel
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
		JButton compare = (JButton) e.getSource(); // Cast the ActionEvent as a JButton

		// Check button clicks
		if (compare == continueBtn) { // Check if button is continue
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			if (level == 1)
				game.window.getContentPane().add(new LevelTwo(game)); // Add new LevelTwo panel to JFrame
			else if (level == 2)
				game.window.getContentPane().add(new LevelThree(game)); // Add new LevelThree panel to JFrame
			else if (level == 3)
				game.window.getContentPane().add(new Results(game)); // Add new Results panel to JFrame
		} else if (compare == retryBtn) { // Check if button is retry
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			if (level == 1)
				game.window.getContentPane().add(new LevelOne(game)); // Add new LevelOne panel to JFrame
			else if (level == 2)
				game.window.getContentPane().add(new LevelTwo(game)); // Add new LevelTwo panel to JFrame
			else if (level == 3)
				game.window.getContentPane().add(new LevelThree(game)); // Add new LevelThree panel to JFrame
		} else if (compare.getText().equals(questions.get(currentQuestion)[1])) { // Check if title of button matches
																					// the current question answers
			correctAnswers++; // Add one to correct answers
			game.addAnswer(true);
			changeQuestion(currentQuestion); // Choose a new question to display
		} else {
			game.addAnswer(false);
			changeQuestion(currentQuestion); // Choose a new question to display
		}

		// Update JFrame
		game.window.revalidate();
		game.window.repaint();
	}

}
