import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends MenuParent {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  Dimension buttonDimension = new Dimension(140, 32);
  Game window;
  JButton newGame, rules, highScores, exit;
  
  public MainMenu(Game game) {
    super(game, "Cyber Case");
    
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
  
    public void createListener(JButton button){
     //Create action listeners
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        buttonClicked(e);
      }
    });
  }
  
  
  public void buttonClicked(ActionEvent e) {
    JButton compare = (JButton)e.getSource();
    if(compare == newGame)
      System.out.println("New Game");
    else if(compare == highScores)
      System.out.println("High Scores");
    else if(compare == rules)
      System.out.println("Rules");
    else
      System.exit(0); //Exit program :(
  }
  
}