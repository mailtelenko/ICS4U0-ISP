import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HighScores extends MenuParent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;
	private JButton menu, erase;
	ArrayList<String> scores;

	public HighScores(Game gme) {
		super(gme, "High Scores"); //Call to super with game object
		game = gme;

		JPanel scorePanel = new JPanel(new GridBagLayout()); //Create centered container for scores

		scores = readScores(); //Read scores into ArrayList
		String allScores = ""; //All scores string
		if (scores.size() > 0) { //Check if there are scores within the ArrayList
			for (int x = 0; x < scores.size(); x++) { //Iterate over each name
				allScores += (x + 1) + ". " + scores.get(x).substring(0, scores.get(x).indexOf(",")) + " .......... "
						+ scores.get(x).substring(scores.get(x).indexOf(",") + 1) + "<br/>"; //Add each name to allScores string
			}
		} else {
			allScores = "No highscores available."; //Change string to no scores available
		}

		scorePanel.add(new JLabel("<html>" + allScores + "</html>")); //Add JLabel to panel
		
		scorePanel.setBackground(backgroundColor);
		add(scorePanel); //Add scorePanel to object

		// Add to MainMenu panel
		add(createButtons(), BorderLayout.SOUTH); //Add bottom buttons to Panel
	}

	private JPanel createButtons() {
		JPanel buttons = new JPanel(); //Panel for buttons
		erase = new JButton("Erase"); //Erase button
		JPanel wrapper = new JPanel(new GridBagLayout()); //Wrapper panel for buttons
		
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); //Set JPanel layout
		buttons.setBackground(backgroundColor);
		
		//Create & style buttons
		setButton(erase);
		menu = createCenterMenu(buttons);

		//Add buttons to dual button JPanel
		createDualButtons(buttons, menu, erase);

		wrapper.setBackground(backgroundColor);
		
		//Add buttons to wrapper panel
		wrapper.add(buttons);
		return wrapper; //Return wrapper panel
	}

	private ArrayList<String> readScores() {
		ArrayList<String> input = new ArrayList<String>(); //Create empty ArrayList
		String temp; //Empty temporary string
		try {
			BufferedReader dataIn = new BufferedReader(new FileReader("resources/data/highScores.txt")); //Open input to file
			while ((temp = dataIn.readLine()) != null) { //While there are no empty lines add the lines to the ArrayList
				input.add(temp);
			}
			dataIn.close(); //Close connection to file
		} catch (Exception e) { //Catch exception
			System.out.println(e); //Print out exception
		}
		return input; //Return ArrayList
	}

	private void eraseScores() {
		try {
			BufferedReader dataIn = new BufferedReader(new FileReader("resources/data/highScores.txt")); //Open input to file
			PrintWriter dataOut = new PrintWriter(new FileWriter("resources/data/highScores.txt")); //Open output to file
			while ((dataIn.readLine()) != null) { //While there are no empty lines erase the lines
				dataOut.println(""); //Output nothing
			}
			dataIn.close(); //Close data in
			dataOut.close(); //Close data out
		} catch (Exception e) { //Catch exception
			System.out.println(e); //Print out exception
		}
	}

	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource(); //Cast the ActionEvent as a JButton
		if (compare == menu) { //Check if the clicked button is the same object as menu button
			game.window.getContentPane().removeAll(); //Remove all panels from JFrame
			game.window.getContentPane().add(game.mainMenu); //Add mainMenu to panels
		} else {
			if (JOptionPane.showConfirmDialog(game.window, "Are you sure you want to erase all high scores?",
					"Confirm Erase", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) { //Confirm erase with user
				eraseScores(); //Erase scores
				game.window.getContentPane().removeAll(); //Remove all panels from JFrame
				game.window.getContentPane().add(new HighScores(game)); //Add new HighScores panel to JFrame
			}
		}
		game.window.validate(); //Validate JFrame
		game.window.repaint(); //Repaint JFrame
	}

}