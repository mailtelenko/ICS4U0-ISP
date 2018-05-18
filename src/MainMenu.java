import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends MenuParent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;
	private JButton newGame, rules, highScores, exit;

	public MainMenu(Game gme) {
		super(gme, "Cyber Case");
		game = gme;

		
		// Add to MainMenu panel
		add(createButtons(), BorderLayout.CENTER);

	}

	private JPanel createButtons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

		JPanel wrapper = new JPanel(new GridBagLayout());

		newGame = new JButton("New Game");
		rules = new JButton("Rules");
		highScores = new JButton("High Scores");
		exit = new JButton("Exit");

		setButton(newGame);
		setButton(rules);
		setButton(highScores);
		setButton(exit);

		buttons.add(newGame);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(rules);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(highScores);
		buttons.add(Box.createVerticalStrut(10));
		buttons.add(exit);

		wrapper.add(buttons);

		return wrapper;
	}

	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource();
		game.window.getContentPane().removeAll();
		if (compare == newGame)
			game.window.getContentPane().add(new Setup(game));
		else if (compare == highScores)
			game.window.getContentPane().add(new HighScores(game));
		else if (compare == rules)
			game.window.getContentPane().add(new Rules(game));
		else
			System.exit(0); // Exit program :(

		game.window.validate();
		game.window.repaint();
	}

}