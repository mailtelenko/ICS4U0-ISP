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
 * <h2>Course Info:</h2> ICS4U0 - Ms.Krasteva
 *
 *
 * @version 0.1
 * @author (Project Manager) Russell Leong, (Project Member) Liam Telenko
 */
public abstract class MenuParent extends JPanel {

	/**
	 * Verify sender/receiver of object
	 */
	private static final long serialVersionUID = 1L;

	/** Holds Game object */
	Game window;
	/** Dimensions for buttons */
	Dimension buttonDimension = new Dimension(140, 32);
	public JLabel title; //Title of panel
	public Color backgroundColor = Color.WHITE;

	public MenuParent(Game game, String titleStr) {
		window = game; //Set window variable to game object
		JPanel titlePanel = new JPanel (new FlowLayout());
		title = new JLabel(titleStr); //Create new JLabel
		titlePanel.setBackground(backgroundColor);
		
		this.setLayout(new BorderLayout()); //Set JPanel layout to BorderLayout
		this.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new LineBorder(new Color(1, 16, 63), 7)));
		this.setBackground(backgroundColor);
		
		Font pixelFont;
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/VT323-Regular.ttf")).deriveFont(30f);
			title.setFont(pixelFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.setBorder(new EmptyBorder(30, 0, 0, 0));
		
		titlePanel.add(title); //Add title to panel
		add(titlePanel, BorderLayout.NORTH);		
	}

	public void setButton(JButton button) {
		// Set size
		button.setPreferredSize(buttonDimension);
		button.setMinimumSize(buttonDimension);
		button.setMaximumSize(buttonDimension);

		// Style Buttons
		button.setFocusPainted(false);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.decode("#3a7ce8"));

		//Add action listener to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClicked(e);
			}
		});
	}

	public JButton createCenterMenu() {
		JButton menu = new JButton("Main Menu"); //Create new button
		setButton(menu); //Add styling & ActionListener
		return menu; //Return JButton
	}

	public JButton createCenterMenu(JPanel panel) {
		JButton menu = new JButton("Main Menu"); //Create mainMenu button
		panel.add(menu); //Add button to panel
		panel.add(Box.createVerticalStrut(10)); //Add spacing
		setButton(menu); //Style & ActionListener
		return menu; //Return button
	}

	public void createDualButtons(JPanel panel, JButton button1, JButton button2) {
		JPanel flow = new JPanel(new FlowLayout()); //Create FlowLayout panel
		flow.add(button1); //Add buttons
		flow.add(button2); //Add buttons
		flow.setBackground(backgroundColor);
		panel.add(flow); //Add FlowLayout with buttons to panel
		panel.add(Box.createVerticalStrut(8)); //Create spacing
	}

	public abstract void buttonClicked(ActionEvent e); //Abstract buttonClicked method

}
