package com.HealthBytes.executable;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * The main Game class for Cyber Case. Controls program flow and JFrame.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Krasteva, V.
 *
 * @version 1.0
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class Game extends JFrame {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Game window */
	public JFrame window;
	/** Icon image for JFrame icon */
	private ImageIcon iconImg = new ImageIcon("resources/images/Logo.png");
	/** Total and correct amount of clicks */
	private int totalClicks, correctClicks, totalTime, timeSpent, totalAnswers, correctAnswers = 0;
	/** Name of player */
	private String name;
	/** Path of the high scores file */
	private String scoresPath = System.getenv("SystemDrive") + "\\users\\" + System.getProperty("user.name")
			+ "\\Desktop";

	/**
	 * Game class constructor. Creates a new JFrame for the main game window and
	 * sets the parameters of said window.
	 */
	public Game() {
		window = new JFrame("Cyber Case"); // Create JFrame window
		SplashScreen splash = new SplashScreen(this, "");

		// Setup JFrame
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
		window.getContentPane().setBackground(new Color(18, 24, 59)); // Set background colour
		window.setIconImage(iconImg.getImage()); // Set the icon image for the JFrame
		window.getContentPane().add(splash); // Add mainMenu to window
		window.setSize(900, 700); // Set size
		window.setMinimumSize(new Dimension(900, 700)); // Set size
		window.setLocationRelativeTo(null); // Position in the center
		window.setVisible(true); // Display window

		// Background looping music
		try {
			// Open connection to file
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("resources/sound/backgroundLoop.wav").getAbsoluteFile());
			// Get audio clip
			Clip clip = AudioSystem.getClip();
			// Open stream and start sound clip
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music
		} catch (Exception excep) {
			// Print error
			System.out.println(excep);
		}
	}

	/**
	 * getScores opens a connection to the highScores.txt file and reads the
	 * information into the scores ArrayList.
	 * 
	 * @return ArrayList of scores read from highScores.txt file
	 */
	public ArrayList<String> getScores() {
		ArrayList<String> input = new ArrayList<String>(); // Create empty ArrayList
		String temp; // Empty temporary string
		try {
			// Open input to file
			BufferedReader dataIn = new BufferedReader(
					new FileReader(getHighScoresPath() + "\\CyberCase\\highscores.txt"));
			// While there are no empty lines add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {
				input.add(temp); // Add to ArrayList
			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
		return input; // Return ArrayList
	}

	/**
	 * setName sets the user's name.
	 * 
	 * @param n
	 *            To store the user's name.
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * getName returns the user's name.
	 * 
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * resetClicks resets the number of clicks to zero.
	 */
	public void resetClicks() {
		totalClicks = 0;
		correctClicks = 0;
	}

	/**
	 * addClick adds one to totalClicks if a click was made, and one to
	 * correctClicks only if a correct click has been made.
	 * 
	 * @param correct
	 *            To check whether the click was correct.
	 */
	public void addClick(boolean correct) {
		if (correct)
			correctClicks++;
		totalClicks++;
	}

	/**
	 * getIncorrectClicks() returns the total incorrect clicks.
	 * 
	 * @return number of incorrect clicks
	 */
	public int getIncorrectClicks() {
		return totalClicks - correctClicks;
	}

	/**
	 * getPercentClicks returns the percentage of correct clicks.
	 * 
	 * @return percent of correct clicks
	 */
	public double getPercentClicks() {
		return ((double) correctClicks / (double) totalClicks) * 100;
	}

	/**
	 * resetTime resets the time to zero.
	 */
	public void resetTime() {
		totalTime = 0;
		timeSpent = 0;
	}

	/**
	 * addTime adds time in milliseconds to the total accumulated time from each
	 * level
	 * 
	 * @param totalT
	 *            The total time in milliseconds available (spent or unspent).
	 * @param tSpent
	 *            The time spent in milliseconds.
	 */
	public void addTime(int totalT, int tSpent) {
		totalTime += totalT;
		timeSpent += tSpent;
	}

	/**
	 * getTime returns the total time spent on every level
	 * 
	 * @return total time spent in seconds
	 */
	public int getTime() {
		return (int) ((double) timeSpent / 1000);
	}

	/**
	 * getPercentTime returns the percentage of remaining time.
	 * 
	 * @return percent of remaining time
	 */
	public double getPercentTime() {
		return ((double) (totalTime - timeSpent) / (double) totalTime) * 100;
	}

	/**
	 * resetAnswers resets the number of answers to zero.
	 */
	public void resetAnswers() {
		totalAnswers = 0;
		correctAnswers = 0;
	}

	/**
	 * addAnswer adds one to totalAnswers if a question was answered, and one to
	 * correctAnswers only if a question has been correctly answered.
	 * 
	 * @param correct
	 *            To check whether the answer was correct.
	 * 
	 */
	public void addAnswer(boolean correct) {
		if (correct) // If a correct answer is to be added (ratio stays constant)
			correctAnswers++; // Add to correct answers
		totalAnswers++; // Add to total answers
	}

	/**
	 * getIncorrectAnswers returns the number of incorrectly answered questions
	 * 
	 * @return the number of incorrectly answered questions
	 */
	public int getIncorrectAnswers() {
		return totalAnswers - correctAnswers;
	}

	/**
	 * getPercentAnswers returns the percentage of correct answers.
	 * 
	 * @return percent of correct clicks
	 */
	public double getPercentAnswers() {
		return ((double) correctAnswers / (double) totalAnswers) * 100;
	}

	/**
	 * Accessor method for scoresPath. Returns path of CyberCase/highscores.txt.
	 * 
	 * @return The path to the CyberCase/highscores.txt parent directory.
	 */
	public String getHighScoresPath() {
		return scoresPath;
	}

	/**
	 * Mutator method for scoresPath. Sets the path of CyberCase/highscores.txt.
	 * 
	 * @param path The path to be set in the game object.
	 */
	public void setHighScoresPath(String path) {
		scoresPath = path;
	}

	/**
	 * Creates a new Game object. Controls the flow of the program.
	 * 
	 * @param args
	 *            Java command line arguments
	 */
	public static void main(String[] args) {
		new Game(); // Create new instance of the Game class.
	}

}
