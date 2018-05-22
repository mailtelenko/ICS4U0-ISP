import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Rules class displays the rules of the game.
 * 
 * <h2>Course Info:</h2> ICS4U0 with Krasteva, V.
 * 
 * @version 18.05.18
 * @author Liam Telenko and Russell Leong
 */
public abstract class LevelParent extends JPanel implements MouseListener {

	/** Verify sender/receiver of object. */
	private static final long serialVersionUID = 1L;
	/** Dimensions for buttons */
	Dimension buttonDimension = new Dimension(140, 32);
	/** Creates menu button */
	public JButton menu, gameContinue, replay;
	/** Creates reference to Game class */
	private Game game;
	/** Timer panel (top left) */
	JPanel timerPanel;
	JLabel timerLabel;
	Timer timer = new Timer();

	public LevelParent(Game gme) {
		game = gme;

		createButtons();
		this.setLayout(new BorderLayout());
		this.addMouseListener(this);
		this.add(createTimer());

		timer.schedule(new RemindTask(), 0, 1000);
	}

	class RemindTask extends TimerTask {
		double timeRem = 10; //5 minutes (300 seconds)

		public void run() {
			timeRem--;
			if (timeRem < 0) {
				timer.cancel();
				timer.purge();
				finishGame(false);
			}else
				timerLabel.setText((int)((timeRem / 60.0)) + ":" + (int)(timeRem % 60));
		}
	}

	public JPanel createTimer() {
		timerPanel = new JPanel();
		timerPanel.add(timerLabel = new JLabel(""));
		return timerPanel;
	}
	
	/**
	 * createButtons method creates a mainMenu button to return to mainMenu
	 */
	private void createButtons() {
		menu = new JButton("Main Menu");
		gameContinue = new JButton("Continue");
		replay = new JButton("Retry");

		setButton(menu);
		setButton(gameContinue);
		setButton(replay);
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

		// Add action listener to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClicked(e);
			}
		});
	}

	public void createDualButtons(JPanel panel, JButton button1, JButton button2) {
		JPanel flow = new JPanel(new FlowLayout()); // Create FlowLayout panel
		flow.add(button1); // Add buttons
		flow.add(button2); // Add buttons
		panel.add(flow, BorderLayout.SOUTH); // Add FlowLayout with buttons to panel
	}

	/**
	 * buttonClicked checks if mainMenu button is clicked
	 * 
	 * @param e
	 *            Checks if an ActionEvent is made on the button.
	 */
	public abstract void buttonClicked(ActionEvent e);

	public abstract void finishGame(boolean win);
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		System.out.println("Mouse clicked");
		System.out.print("X and Y coords: ");
		System.out.print(arg0.getX() + ", ");
		System.out.println(arg0.getY());

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		System.out.println("Mouse entered");

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		System.out.println("Mouse exit");

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		System.out.println("Mouse pressed");

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		System.out.println("Mouse released");

	}

}