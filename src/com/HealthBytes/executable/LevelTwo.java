package com.HealthBytes.executable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The LevelTwo class extends LevelParent and is the second level in a series of
 * three. The level focuses on physical health and the effects it has on
 * individuals.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Krasteva, V.
 * 
 * @version 1.0
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public class LevelTwo extends LevelParent {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	private Game game;
	/** This object */
	private LevelTwo secondLevel = this;
	/** The container for the game image */
	private JPanel imagePanel;

	/**
	 * LevelTwo class constructor sets the game reference to the game object.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public LevelTwo(Game gme) {
		super(gme, "levelTwo");

		// Set instance variables
		game = gme; // Set reference to game object

		// JPanel to center and house game image
		imagePanel = new JPanel(new GridBagLayout());

		// Create game image
		ImageIcon gameImage = new ImageIcon(
				new ImageIcon("resources/images/levelTwo/LevelTwo.jpg").getImage().getScaledInstance(750, 500, 10));
		JLabel gameImageLabel = new JLabel("", gameImage, JLabel.CENTER); // Center in JLabel

		// Style image
		gameImageLabel
				.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(Color.decode("#402644"), 5))); // Border
		// Image Dimensions
		gameImageLabel.setMinimumSize(new Dimension(750, 500));
		gameImageLabel.setPreferredSize(new Dimension(750, 500));
		gameImageLabel.setMaximumSize(new Dimension(750, 500));

		// Add game image to wrapper panel
		imagePanel.add(gameImageLabel);

		// Add MouseListener to gameImageLabel
		gameImageLabel.addMouseListener(new MouseListener() {

			@Override
			/**
			 * mouseClicked is triggered by the clicking of the mouse within the
			 * gameImageLabel.
			 * 
			 * @param e
			 *            Checks if an MouseEvent is made on the mouse.
			 */
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int count = 0; // Counts the amount of objects in the room
				for (String[] location : locations) { // Iterate over all objects in the room
					// Check if the coordinates of the mouse click are within the dimensions of the
					// object
					if (Double.parseDouble(location[1]) <= e.getX() && Double.parseDouble(location[2]) >= e.getX()
							&& Double.parseDouble(location[3]) <= e.getY()
							&& Double.parseDouble(location[4]) >= e.getY() && gameRunning) {
						// Show info panel pop-up
						showInfoPane(new String[] { location[0], location[5] });
						locationFound.set(count, true); // Set object to found
						// Remove previous panel from SOUTH border layout
						BorderLayout layout = (BorderLayout) secondLevel.getLayout();
						secondLevel.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
						// Add to JPanel
						secondLevel.add(updateObjectCounter(), BorderLayout.SOUTH);
						game.addClick(true); // Add correct click
						return;
					}
					count++; // Add to count
				}

				// If game is still active
				if (gameRunning)
					game.addClick(false); // Add false click
			}

			/**
			 * mouseEntered is triggered by the entering of the mouse within the
			 * gameImageLabel.
			 * 
			 * @param e
			 *            Checks if an MouseEvent is made on the mouse.
			 */
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
			}

			/**
			 * mouseExited is triggered by the exiting of the mouse within the
			 * gameImageLabel.
			 * 
			 * @param e
			 *            Checks if an MouseEvent is made on the mouse.
			 */
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
			}

			/**
			 * mousePressed is triggered by the clicking and holding of the mouse within the
			 * gameImageLabel.
			 * 
			 * @param e
			 *            Checks if an MouseEvent is made on the mouse.
			 */
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
			}

			/**
			 * mouseReleased is triggered by the release of the mouse within the
			 * gameImageLabel.
			 * 
			 * @param e
			 *            Checks if an MouseEvent is made on the mouse.
			 */
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
			}
		}); // Add a MouseListener to the image

		imagePanel.setBackground(backgroundColor); // Set background colour

		collectLocations(); // Collect data from file

		// Add to JPanel
		add(createIntroduction("Two"), BorderLayout.CENTER); // Add description
		add(updateObjectCounter(), BorderLayout.SOUTH); // Add object counter
	}

	/**
	 * startGame removes the introduction and replaces it with the game image. It
	 * also starts the timer.
	 */
	public void startGame() {
		remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER)); // Remove description
		add(imagePanel, BorderLayout.CENTER); // Add game image
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
			BorderLayout layout = (BorderLayout) secondLevel.getLayout();
			secondLevel.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
			buttonCont.add(retryBtn);
		}
		buttonCont.setBackground(backgroundColor); // Set background colour
		this.add(buttonCont, BorderLayout.SOUTH); // Add FlowLayout with buttons to panel

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
		if (compare == retryBtn) { // If retry is clicked
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new LevelTwo(game)); // Add LevelTwo to panels
		} else if (compare == continueBtn) { // If continue is clicked
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new Quiz(game, 2)); // Add Quiz to panels
		} else if (compare == closeDescription) { // If start game is clicked
			startGame();
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

}