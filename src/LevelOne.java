import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The LevelOne class extends LevelParent and is the first level in a series of
 * three. The level focuses on mental health and the affects it has on
 * individuals.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class LevelOne extends LevelParent {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	Game game;
	/** This object */
	LevelOne firstLevel = this;
	/** The container for the game image */
	JPanel imagePanel;

	/**
	 * LevelOne class constructor sets the game reference to the game object.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public LevelOne(Game gme) {
		super(gme, "levelOne");
		game = gme; // Set reference to game object

		// JPanel to centre and house game image
		imagePanel = new JPanel(new GridBagLayout());

		// Create game image
		ImageIcon gameImage = new ImageIcon(
				new ImageIcon("resources/images/levelOne/LevelOne.jpg").getImage().getScaledInstance(750, 500, 10));
		JLabel gameImageLabel = new JLabel("", gameImage, JLabel.CENTER); // Centre in JLabel

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
			 * gameImageLabel
			 */
			public void mouseClicked(java.awt.event.MouseEvent e) {
				System.out.println("Mouse clicked");
				System.out.print("X and Y coords: ");
				System.out.print(e.getX() + ", ");
				System.out.println(e.getY());
				int count = 0; // Counts the amount of objects in the room
				for (String[] location : locations) { // Iterate over all objects in the room
					// Check if the coordinates of the mouse click are within the dimensions of the
					// object
					if (Double.parseDouble(location[1]) <= e.getX() && Double.parseDouble(location[2]) >= e.getX()
							&& Double.parseDouble(location[3]) <= e.getY()
							&& Double.parseDouble(location[4]) >= e.getY() && gameRunning) {
						// Show info panel popup
						showInfoPane(new String[] { location[0], location[5] });
						locationFound.set(count, true); // Set object to found
						// Remove previous panel from SOUTH border layout
						BorderLayout layout = (BorderLayout) firstLevel.getLayout();
						firstLevel.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
						// Add to JPanel
						firstLevel.add(updateObjectCounter(), BorderLayout.SOUTH);
						return;
					}
					count++; // Add to count
				}
			}

			/**
			 * mouseEntered is triggered by the entering of the mouse within the
			 * gameImageLabel
			 */
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
			}

			/**
			 * mouseExited is triggered by the exiting of the mouse within the
			 * gameImageLabel
			 */
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
			}

			/**
			 * mousePressed is triggered by the clicking and holding of the mouse within the
			 * gameImageLabel
			 */
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
			}

			/**
			 * mouseReleased is triggered by the release of the mouse within the
			 * gameImageLabel
			 */
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
			}
		}); // Add a MouseListener to the image

		imagePanel.setBackground(backgroundColor); // Set background colour

		collectLocations(); // Collect data from file

		// Add to JPanel
		add(createIntroduction("One"), BorderLayout.CENTER); // Add description
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
	}

	/**
	 * finishGame displays a set of buttons once the level has ended. The method
	 * also stops the timer and controls the flow to the next panel.
	 * 
	 * @param win
	 *            Determines if the continue or retry button is displayed.
	 */
	public void finishGame(boolean win) {
		gameRunning = false; // Set game to stop running
		timer.cancel(); // Cancel timer
		timer.purge(); // Purge timer
		if (win)
			createDualButtons(this, menu, gameContinue);
		else {
			BorderLayout layout = (BorderLayout) firstLevel.getLayout();
			firstLevel.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
			createDualButtons(this, menu, replay);
		}
		game.window.validate();
		game.window.repaint();
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
		if (compare == menu) { // Check if the clicked button is the same object as menu button
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new MainMenu(game)); // Add mainMenu to panels
		} else if (compare == replay) {
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new LevelOne(game)); // Add mainMenu to panels
		} else if (compare == gameContinue) {
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new Quiz(game, 1)); // Add mainMenu to panels
		} else if (compare == closeDescription) {
			startGame();
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame
	}

}
