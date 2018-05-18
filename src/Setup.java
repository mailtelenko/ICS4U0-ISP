import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Setup class prompts the user to enter a name input.
 * 
 * <h2>Course Info:</h2>
 * ICS4U0 with Krasteva, V.
 * 
 * @version 18.05.18
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
  /** Creates instance of Game class */
  private Game game;
  
  /**
   * Setup class constructor creates layout of setup screen.
   * 
   * @param gme To create a reference to the Game class.
   */ 
  public Setup(Game gme) {
    super(gme, "Setup");
    game = gme;
    
    JPanel container = new JPanel(new GridBagLayout());
    
    JLabel prompt = new JLabel();
    prompt.setText("<html>" + "Please enter your name:<br/>" + "</html>");
    container.add(prompt);
    container.add(Box.createHorizontalStrut(10));
    
    textField = new JTextField(30);
    container.add(textField);
    textField.requestFocusInWindow();
    
    // Add to Setup panel
    add(container, BorderLayout.CENTER);
    add(createButtons(), BorderLayout.SOUTH);
    
  }
  
  /**
   * createButtons method creates a Continue button to Level One
   */ 
  private JPanel createButtons() {
    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    
    JPanel wrapper = new JPanel(new GridBagLayout());
    
    cont = new JButton("Continue");
    
    setButton(cont);
    
    buttons.add(cont);
    buttons.add(Box.createVerticalStrut(10));
    
    wrapper.add(buttons);
    
    return wrapper;
  }
  
  /**
   * buttonClicked checks if Continue button is clicked
   * 
   * @param e Checks if an ActionEvent is made on the button.
   */ 
  public void buttonClicked(ActionEvent e) {
    JButton compare = (JButton) e.getSource();
    if (compare == cont) {
      // game.window.getContentPane().removeAll();
      // game.window.getContentPane().add(game.levelOne);
      name = textField.getText();
      System.out.println("Continue" + name);
    }
    
    // game.window.validate();
    // game.window.repaint();
  }
}