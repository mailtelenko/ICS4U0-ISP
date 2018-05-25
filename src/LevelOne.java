import java.awt.event.ActionEvent;

import javax.swing.JButton;

/**
 * The LevelOne class extends LevelParent and is the first level in a series of three. The 
 * level focuses on mental health and the affects it has on individuals.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 0.2
 * @author Liam Telenko and Russell Leong
 */
public class LevelOne extends LevelParent{

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Referance to Game object */
	Game game;

	/**
	 * LevelOne class constructor sets the game reference to the game object.
	 * 
	 * @param gme
	 *            To create a reference to the Game class.
	 */
	public LevelOne(Game gme) {
		game = gme;
	}
	
	public void finishGame(boolean win) {
		if(win) 
			createDualButtons(this, menu, gameContinue);
		else
			createDualButtons(this, menu, replay);
		game.window.validate();
		game.window.repaint();
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		System.out.println("Mouse clicked");
		System.out.print("X and Y coords: ");
		System.out.print(arg0.getX() + ", ");
		System.out.println(arg0.getY());
		showInfoPane(new String[]{"Book", "This is a book which is a book in a room with a book book book."});
	}

	@Override
	public void buttonClicked(ActionEvent e) {
		JButton compare = (JButton) e.getSource(); // Cast the ActionEvent as a JButton
		if (compare == menu) { // Check if the clicked button is the same object as menu button
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(game.mainMenu); // Add mainMenu to panels
		}else if(compare == replay) {
			game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			game.window.getContentPane().add(new LevelOne(game)); // Add mainMenu to panels
		}else{
			//game.window.getContentPane().removeAll(); // Remove all panels from JFrame
			//game.window.getContentPane().add(new LevelOne(game)); // Add mainMenu to panels
			System.out.println("To quiz");
		}
		game.window.validate(); // Validate JFrame
		game.window.repaint(); // Repaint JFrame		
	}
	
}
