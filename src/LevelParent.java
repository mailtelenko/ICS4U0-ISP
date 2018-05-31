import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The LevelParent class is the parent to all 3 levels of the game. It contains
 * essential methods which are used in each level.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public abstract class LevelParent extends JPanel {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Reference to Game object */
	Game game;
	/** Reference to level name */
	String levelName;
	/** Dimensions for buttons */
	Dimension buttonDimension = new Dimension(140, 32);
	/** Buttons to be used in all levels */
	public JButton menu, gameContinue, replay, exitPanel, closeDescription;
	/** Icon image for JFrame icon **/
	ImageIcon iconImg = new ImageIcon("resources/images/Logo.png");
	/** Background Colour */
	Color backgroundColor = new Color(18, 24, 59);
	/** Timer panel (top left) */
	JPanel timerPanel;
	/** Timer label (digits) */
	JLabel timerLabel;
	/** Timer object used to control counting */
	Timer timer = new Timer();
	/** Boolean value for if game is running (no menu screen showing) */
	Boolean gameRunning = false;

	/** JFrame for popup (when object is clicked on screen) */
	JFrame infoPane;
	/** Title of infoPane */
	JLabel infoTitle;
	/** Description of object in panel */
	JLabel infoContent;
	/** Close up of object (image at top of panel) */
	JLabel imageLabel;
	/** Font for use as title */
	Font pixelFont;

	/** Dimensions for timer and info JFrame */
	Dimension timerDimension = new Dimension(100, 60);
	Dimension infoDimension = new Dimension(350, 450);

	/** Locations and coordinates for all of the hidden objects */
	ArrayList<String[]> locations = new ArrayList<String[]>();
	ArrayList<Boolean> locationFound = new ArrayList<Boolean>(); // ArrayList for if each item has been found

	/**
	 * LevelParent class constructor creates the buttons for use in both the main
	 * JPanel (game.window) as well as the object information frame (infoPane).
	 * 
	 * @param g
	 *            The game reference to be used by the object.
	 */
	public LevelParent(Game g, String l) {
		game = g;
		levelName = l;
		createButtons(); // Create buttons for use in panels
		this.setLayout(new BorderLayout()); // Set panel layout to BorderLayout
		// Create border for panel
		this.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(Color.BLACK, 7)));
		this.add(createTimer(), BorderLayout.NORTH); // Add the timer to the panel

		infoPane = createInfoPane(); // Create a generic infoPane

		timer.schedule(new RemindTask(), 0, 1000); // Set the timer to update every second
	}

	/**
	 * The RemindTask class is used for the timing of the level. The method "run" is
	 * executed every 1000 milliseconds.
	 * 
	 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
	 * 
	 * @version 0.2
	 * @author Liam Telenko and Russell Leong
	 */
	class RemindTask extends TimerTask {
		double timeRem = 300; // 5 minutes (300 seconds)

		/**
		 * run method is continuously run every second (timer.schedule(new RemindTas(),
		 * 0, 1000)). The method updates the timer by removind one from timeRem each
		 * second. The timer will stop and call finishGame(false) (lose game) when the
		 * timer runs out.
		 */
		public void run() {
			if (gameRunning)
				timeRem--; // Subtract one from time remaining
			if (timeRem < 0) { // If there is no time remaining
				finishGame(false); // End game (lose)
			} else { // Time still remaining
				if (((int) timeRem / 60) <= 0) // If there is less than a minute remaining
					timerLabel.setText("" + (int) ((timeRem % 60) / 10) + (int) (timeRem % 60 % 10)); // Set label
				else // More than a minute
						// Set label
					timerLabel.setText(
							(int) ((timeRem / 60.0)) + ":" + (int) ((timeRem % 60) / 10) + (int) (timeRem % 60 % 10));
			}
		}
	}

	/**
	 * createTimer creates a label to be displayed onto the main panel which will
	 * display the remaining time.
	 * 
	 * @return JPanel containing completed timer unit.
	 */
	public JPanel createTimer() {
		timerPanel = new JPanel(); // Create new panel to house timer
		timerPanel.add(timerLabel = new JLabel("")); // Set timer label to nothing and add to timerPanel
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align centre (horizontally)

		// Set size of label
		timerLabel.setMinimumSize(timerDimension);
		timerLabel.setPreferredSize(timerDimension);
		timerLabel.setMaximumSize(timerDimension);

		// Add coloured border to label
		timerLabel.setBorder(
				new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.decode("#402644"), 4)));
		timerLabel.setForeground(Color.WHITE); // Set text colour

		// Attempt to create a new custom font
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/VT323-Regular.ttf"))
					.deriveFont(29f); // Set font
			timerLabel.setFont(pixelFont); // Add font to label
		} catch (FontFormatException e) { // Catch exception
			e.printStackTrace();
		} catch (IOException e) { // Catch exception
			e.printStackTrace();
		}

		timerPanel.setBackground(backgroundColor); // Set background colour

		return timerPanel; // Return completed panel
	}

	/**
	 * createButtons method creates a mainMenu button to return to mainMenu
	 */
	private void createButtons() {
		// Create new JButtons
		menu = new JButton("Main Menu");
		gameContinue = new JButton("Continue");
		replay = new JButton("Retry");
		exitPanel = new JButton("Continue");

		// Tweak/format buttons
		setButton(menu);
		setButton(gameContinue);
		setButton(replay);
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
		button.setPreferredSize(buttonDimension);
		button.setMinimumSize(buttonDimension);
		button.setMaximumSize(buttonDimension);

		// Style Buttons
		button.setFocusPainted(false);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.decode("#402644"));
		button.setFont(new Font("Cambria Math", Font.PLAIN, 14));

		// Add action listener to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClicked(e);
			}
		});
	}

	/**
	 * createDualButtons method adds two buttons to a JPanel in order to keep them
	 * in line on the JFrame.
	 * 
	 * @param panel
	 *            The panel to be added to (The new FlowLayout containing the
	 *            buttons).
	 * @param button1
	 *            The first button to be added to the FlowLayout (Left).
	 * @param button2
	 *            The second button to be added to the FlowLayout (Right).
	 */
	public void createDualButtons(JPanel panel, JButton button1, JButton button2) {
		JPanel flow = new JPanel(new FlowLayout()); // Create FlowLayout panel
		flow.add(button1); // Add buttons
		flow.add(button2); // Add buttons
		flow.setBackground(backgroundColor); // Set background colour
		panel.add(flow, BorderLayout.SOUTH); // Add FlowLayout with buttons to panel
	}

	/**
	 * createInfoPane returns a JFrame completed with the layout of the info panel.
	 * The info panel displays information about the clicked object, along with an
	 * image.
	 * 
	 * @return
	 */
	private JFrame createInfoPane() {
		JFrame tempFrame = new JFrame(); // Create temporary frame
		JPanel content = new JPanel(new BorderLayout()); // New panel to house content (text)
		JPanel text = new JPanel(); // New JPanel to house block text in content
		JPanel bottomButton = new JPanel(new FlowLayout()); // New JPanel to house button at bottom of screen
		Dimension infoContentDimension = new Dimension(300, 160); // Dimension used for the inner content

		setButton(exitPanel); // Set/style button

		// Add custom action listener to button
		exitPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tempFrame.setVisible(false); // Hide frame when button is clicked
				game.window.requestFocus();
			}
		});

		// Set the JFrame
		tempFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Default close operation
		tempFrame.setIconImage(iconImg.getImage()); // Set the icon image for the JFrame
		tempFrame.setSize(infoDimension); // Set size
		tempFrame.setResizable(false); // Turn off user resizing
		tempFrame.setLocationRelativeTo(null); // Position in the center

		// Set the content style
		// Create coloured border around content
		content.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(Color.BLACK, 7)));
		content.setBackground(backgroundColor); // Background colour to white

		text.add(infoTitle = new JLabel("Title")); // Add the JLabel for title
		text.add(infoContent = new JLabel("")); // Add the JLabel for block content

		// Set the text colours
		infoContent.setForeground(Color.WHITE);
		infoTitle.setForeground(Color.WHITE);
		text.setBackground(Color.decode("#2b1a2d")); // Set the background colour to white
		// Set border colour of text panel
		text.setBorder(new CompoundBorder(new LineBorder(Color.decode("#402644"), 5), new EmptyBorder(10, 10, 10, 10)));

		infoTitle.setFont(pixelFont); // Set the font for the text (pixels)
		infoContent.setFont(new Font("Cambria Math", Font.PLAIN, 14)); // Set font for block text

		// Set the size of the content
		infoContent.setMinimumSize(infoContentDimension);
		infoContent.setPreferredSize(infoContentDimension);
		infoContent.setMaximumSize(infoContentDimension);

		// Create a new image
		ImageIcon image = new ImageIcon(
				new ImageIcon("resources/images/" + levelName + "/items/book.png").getImage().getScaledInstance(100, 100, 10));
		imageLabel = new JLabel("", image, JLabel.CENTER); // Put image in centred label
		imageLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to image

		bottomButton.add(exitPanel); // Add exit button to bottomButton panel
		bottomButton.setBackground(backgroundColor); // Set background to white

		content.add(imageLabel, BorderLayout.NORTH); // Add image
		content.add(text); // Add block text
		content.add(bottomButton, BorderLayout.SOUTH); // Add button

		tempFrame.add(content, BorderLayout.CENTER); // Add content to JFrame

		return tempFrame; // Return JFrame
	}

	/**
	 * showInfoPane sets the content of the infoPane and then displays it to the
	 * user.
	 * 
	 * @param data
	 *            The title and content to be displayed in the panel.
	 */
	public void showInfoPane(String[] data) {
		infoTitle.setText(data[0]); // Set title
		infoContent.setText("<html>" + "<center>" + data[1] + "</center>" + "</html>"); // Set block text
		// Create new image
		imageLabel.setIcon(new ImageIcon(new ImageIcon("resources/images/" + levelName + "/items/" + data[0] + ".png").getImage()
				.getScaledInstance(100, 100, 10)));
		infoPane.setVisible(true); // Set to visible
	}

	/**
	 * collectLocations reads from a file and imports the data to an ArrayList of
	 * String arrays. The method also initialises the locationFound ArrayList to
	 * false.
	 * 
	 * @param levelName
	 *            The name of the current level to be matched to the correct file in
	 *            resources/data.
	 */
	public void collectLocations() {
		String temp; // Empty temporary string
		String tempData[] = { "", "", "", "", "", "" }; // Empty temporary array

		try { // Read from file
				// Open input from file
			BufferedReader dataIn = new BufferedReader(new FileReader("resources/data/" + levelName + "/" + levelName + ".txt"));
			// While there are no empty lines in the file add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {

				// Parsing the data:
				tempData[0] = temp.substring(0, temp.indexOf("/"));
				temp = temp.substring(temp.indexOf("/") + 1);
				tempData[1] = temp.substring(0, temp.indexOf("/"));
				temp = temp.substring(temp.indexOf("/") + 1);
				tempData[2] = temp.substring(0, temp.indexOf("/"));
				temp = temp.substring(temp.indexOf("/") + 1);
				tempData[3] = temp.substring(0, temp.indexOf("/"));
				temp = temp.substring(temp.indexOf("/") + 1);
				tempData[4] = temp.substring(0, temp.indexOf("/"));
				tempData[5] = temp.substring(temp.indexOf("/") + 1);

				// Add parsed data to ArrayList
				// Name, X-coordinate, X-coordinate 2, Y-coordinate, Y-coordinate 2, Description
				locations.add(
						new String[] { tempData[0], tempData[1], tempData[2], tempData[3], tempData[4], tempData[5] });
				locationFound.add(false); // Initialise to false

			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
	}

	/**
	 * updateObjectCounter updates the green and red icons at the bottom of the
	 * panel. The dots represent which objects have been found/still need to be
	 * found to proceed in the level.
	 * 
	 * @return JPanel complete with the updated object counter comprising of red and
	 *         green dots. A red dot represents a still unfound object, green dots
	 *         represent found objects.
	 */
	public JPanel updateObjectCounter() {
		JPanel container = new JPanel(new FlowLayout()); // Create new JPanel (container)
		boolean gameWin = true; // Used to check if the game is over (won)
		for (Boolean found : locationFound) { // Iterate through each object in the level
			if (found) { // Check if the object has been found
				// Create new green dot
				ImageIcon image = new ImageIcon(
						new ImageIcon("resources/images/greenDot.png").getImage().getScaledInstance(20, 20, 10));
				JLabel tempLabel = new JLabel(image); // Add to label
				tempLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to image
				container.add(tempLabel); // Add to FlowLayout
			} else { // Object has hot been found
				// Create a new red dot
				ImageIcon image = new ImageIcon(
						new ImageIcon("resources/images/redDot.png").getImage().getScaledInstance(20, 20, 10));
				JLabel tempLabel = new JLabel(image); // Add to label
				tempLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to image
				container.add(tempLabel); // Add to FlowLayout
				gameWin = false; // Game has not been won
			}
		}

		// Check if the game is over (all objects found)
		if (gameWin)
			finishGame(true); // Display buttons

		container.setBackground(backgroundColor); // Set background colour
		return container; // Return JPanel
	}

	/**
	 * createIntroduction creates a JPanel complete with a description read from a
	 * text file.
	 * 
	 * @param level The level number (string) to be created.
	 * @return JPanel complete with description, title, and start game button.
	 */
	public JPanel createIntroduction(String level) {
		//JPanel containers
		JPanel container = new JPanel(new BorderLayout());
		JPanel buttonContainer = new JPanel(new FlowLayout());
		JLabel description = new JLabel();
		//Title label
		JLabel title = new JLabel("Level " + level + " Description", SwingConstants.CENTER);

		//Create JButton
		closeDescription = new JButton("Start Game");

		//Get description
		try { // Read from file
				// Open input from file
			BufferedReader dataIn = new BufferedReader(
					new FileReader("resources/data/level" + level + "/level" + level + "Description.txt"));
			// While there are no empty lines in the file add the lines to the ArrayList
			description.setText("<html>" + "<center>" + dataIn.readLine() + "</center>" + "</html>");
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}

		//Set fonts
		title.setFont(pixelFont);
		description.setFont(new Font("Cambria Math", Font.PLAIN, 14));

		//Set borders
		title.setBorder(new EmptyBorder(50, 50, 50, 50));
		description.setBorder(new EmptyBorder(50, 50, 50, 50));
		container.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 6), new EmptyBorder(10, 10, 10, 10)));

		//Set button (style + action listener)
		setButton(closeDescription);

		//Set foreground (text) colours
		title.setForeground(Color.WHITE);
		description.setForeground(Color.WHITE);
		
		//Set background colours
		buttonContainer.setBackground(Color.decode("#402644"));
		container.setBackground(Color.decode("#402644"));

		//Add elements to panels
		buttonContainer.add(closeDescription); //Button panel
		container.add(title, BorderLayout.NORTH); //Container panel
		container.add(description, BorderLayout.CENTER); //Container panel
		container.add(buttonContainer, BorderLayout.SOUTH); //Container panel
		
		return container; //Return panel
	}

	/**
	 * startGame adds the correct game element to the screen and starts the timer.
	 */
	public abstract void startGame();

	/**
	 * buttonClicked checks if mainMenu button is clicked
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public abstract void buttonClicked(ActionEvent e);

	/**
	 * finishGame displays a set of buttons once the level has ended. The method
	 * also stops the timer and controls the flow to the next panel.
	 * 
	 * @param win
	 *            Determines if the continue or retry button is displayed.
	 */
	public abstract void finishGame(boolean win);

}