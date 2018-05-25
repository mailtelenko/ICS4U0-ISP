import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	/** Referance to Game object */
	private Game game;

	/**
	 * Setup class constructor creates layout of setup screen.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public Setup(Game gme) {
		super(gme, "Setup"); // Call to parent with game object
		game = gme; // Set object's game object

		JPanel container = new JPanel(new GridBagLayout()); // Create container for text field

		JLabel prompt = new JLabel(); // Create input label
		prompt.setText("<html>" + "Please enter your name:<br/>" + "</html>"); // Add text to label
		container.add(prompt); // Add to continaer
		container.add(Box.createHorizontalStrut(10)); // Add horizontal spacing betwen label/input

		textField = new JTextField(30); // Set text field
		container.add(textField); // Add text field to containter

		container.setBackground(backgroundColor); // Set background colour

		// Add to Setup panel
		add(container, BorderLayout.CENTER);
		add(createButtons(), BorderLayout.SOUTH);
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
			if ((name = textField.getText()).length() < 1) { // Check if name is valid
				JOptionPane.showMessageDialog(game.window, "Please enter a name before continuing.", "Enter name",
						JOptionPane.ERROR_MESSAGE); // Display error message to user
			} else {
				game.window.getContentPane().removeAll(); // Remove all panels from frame
				game.window.getContentPane().add(new LevelOne(game)); // Add level to frame
			}
		}
		// Validate/repaint
		game.window.validate();
		game.window.repaint();
	}
}