package com.HealthBytes.executable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The parent for all menu panels for the program.
 * 
 * <h2>Course Info:</h2> ICS4U0 - Krasteva, V.
 *
 * @version 1.0
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public abstract class MenuParent extends JPanel {

	/** Verify sender/receiver of object */
	private static final long serialVersionUID = 1L;
	/** Dimensions for buttons */
	private Dimension buttonDimension = new Dimension(140, 32);
	/** Title of panel */
	private JLabel title;
	/** Background colour of panel */
	public Color backgroundColor = new Color(18, 24, 59);
	/** ArrayList containing unparsed data */
	public ArrayList<String> scores;

	/**
	 * MenuParent class constructor creates a title and generic layout for use in
	 * the program menus.
	 * 
	 * @param titleStr
	 *            The string to use as the title of the panel (top text).
	 */
	public MenuParent(String titleStr) {
		JPanel titlePanel = new JPanel(new FlowLayout()); // Create JPanel for title
		title = new JLabel(titleStr); // Create new JLabel

		// Set JPanel specifications
		this.setLayout(new BorderLayout()); // Set JPanel layout to BorderLayout
		this.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(Color.BLACK, 5))); // Create
																											// border
		this.setBackground(backgroundColor); // Set background colour

		titlePanel.setBackground(backgroundColor); // Set background colour

		// Create and set title font
		try {
			// Create font
			Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/VT323-Regular.ttf"))
					.deriveFont(50f);
			title.setFont(pixelFont); // Set font
		} catch (FontFormatException e) { // Catch exception
			e.printStackTrace();
		} catch (IOException e) { // Catch exception
			e.printStackTrace();
		}

		title.setForeground(Color.WHITE); // Set text colour
		title.setBorder(new EmptyBorder(30, 0, 0, 0)); // Add padding to title

		titlePanel.add(title); // Add title to panel
		add(titlePanel, BorderLayout.NORTH); // Add titlePanel to JPanel
	}

	/**
	 * setButton sets the correct size for the buttons and modifies their
	 * appearance. This method keeps consistency between the buttons of the program.
	 * 
	 * @param button
	 *            The button which will be styled and will have an action listener
	 *            attached.
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
				//Create sound
				try {
					//Open connection to file
					AudioInputStream audioInputStream = AudioSystem
							.getAudioInputStream(new File("resources/sound/buttonClick.wav").getAbsoluteFile());
					//Get audio clip
					Clip clip = AudioSystem.getClip();
					//Open stream and start sound clip
					clip.open(audioInputStream);
					clip.start();
				} catch (Exception excep) {
					//Print error
					System.out.println(excep);
				}

				buttonClicked(e);
			}
		});

	}

	/**
	 * createCenterMenu generates a new JButton with the correct name and
	 * actionListener.
	 * 
	 * @return JButton correctly styled and ready for use.
	 */
	public JButton createCenterMenu() {
		JButton menu = new JButton("Main Menu"); // Create new button
		setButton(menu); // Add styling & ActionListener
		return menu; // Return JButton
	}

	/**
	 * createCenterMenu creates a fills a JPanel with a mainMenu button properly
	 * aligned and formatted.
	 * 
	 * @param panel
	 *            The JPanel to add the menu button to.
	 * @return JButton which was added to the panel.
	 */
	public JButton createCenterMenu(JPanel panel) {
		JButton menu = new JButton("Main Menu"); // Create mainMenu button
		panel.add(menu); // Add button to panel
		panel.setBackground(backgroundColor);
		panel.add(Box.createVerticalStrut(10)); // Add spacing
		setButton(menu); // Style & ActionListener
		return menu; // Return button
	}

	/**
	 * createDualButtons method adds two buttons to a JPanel in order to keep them
	 * in line on the JFrame.
	 * 
	 * @param panel
	 *            The JPanel to add the buttons to
	 * @param button1
	 *            JButton on the left of the dual button setup
	 * @param button2
	 *            JButton on the right of the dual button setup
	 */
	public void createDualButtons(JPanel panel, JButton button1, JButton button2) {
		JPanel flow = new JPanel(new FlowLayout()); // Create FlowLayout panel
		flow.add(button1); // Add buttons
		flow.add(button2); // Add buttons
		flow.setBackground(backgroundColor);
		panel.add(flow); // Add FlowLayout with buttons to panel
		panel.add(Box.createVerticalStrut(8)); // Create spacing
	}

	/**
	 * buttonClicked checks if a button has been clicked and responds appropriately.
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public abstract void buttonClicked(ActionEvent e); // Abstract buttonClicked method

}
