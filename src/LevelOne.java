import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class LevelOne extends LevelParent{

	/**
	 * 
	 */
	Game game;
	
	private static final long serialVersionUID = 1L;

	public LevelOne(Game gme) {
		super(gme);
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
		finishGame(false);

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
