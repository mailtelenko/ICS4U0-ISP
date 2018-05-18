import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The Rules class displays the rules of the game.
 * 
 * <h2>Course Info:</h2>
 * ICS4U0 with Krasteva, V.
 * 
 * @version 18.05.18
 * @author Liam Telenko and Russell Leong
 */
public class Rules extends MenuParent {
  
  /** Verify sender/receiver of object. */
  private static final long serialVersionUID = 1L;
  /** Creates menu button */
  private JButton menu;
  /** Creates reference to Game class */
  private Game game;
  
  public Rules(Game gme) {
    super(gme, "Rules"); //Call to super with game object
    game = gme;
    
    JLabel instructions = new JLabel(); //Text label
    instructions.setText("<html>"
                           + "There are three levels in this game, completing the preceding level will allow you to continue to the next. In each level, you are tasked to solve a case before you run out of time. There will be objects around the “room” on screen which should be clicked on to gain the necessary information to solve the case. Click on New Game to begin level one. Have fun!"
                           + "</html>"); //Set text to instructions
    instructions.setBorder(new EmptyBorder(30, 40, 10, 40));
    add(instructions, BorderLayout.CENTER); //Add instructions to JPanel
    add(createButtons(), BorderLayout.SOUTH); //Add buttons to bottom of panel
    
  }
  
  /**
   * createButtons method creates a mainMenu button to return to mainMenu
   */ 
  private JPanel createButtons() {
    JPanel buttons = new JPanel(); //JPanel for buttons
    JPanel wrapper = new JPanel(new GridBagLayout()); //Wrapper for buttons
    
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); //Set JPanel layout
    
    //Create & style buttons
    menu = createCenterMenu(buttons);
    
    //Add buttons to wrapper panel
    wrapper.add(buttons);
    return wrapper; //Return wrapper panels
  }
  /**
   * buttonClicked checks if mainMenu button is clicked
   * 
   * @param e Checks if an ActionEvent is made on the button.
   */ 
  public void buttonClicked(ActionEvent e) {
    JButton compare = (JButton) e.getSource(); //Cast the ActionEvent as a JButton
    if (compare == menu) { //Check if the clicked button is the same object as menu button
      game.window.getContentPane().removeAll(); //Remove all panels from JFrame
      game.window.getContentPane().add(game.mainMenu); //Add mainMenu to panels
    }
    game.window.validate(); //Validate JFrame
    game.window.repaint(); //Repaint JFrame
  }
  
}