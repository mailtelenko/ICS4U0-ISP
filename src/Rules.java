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

public class Rules extends MenuParent {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  JButton menu;
  Game game;
  
  public Rules(Game gme) {
    super(gme, "Rules");
    game = gme;
    
    JLabel instructions = new JLabel();
    instructions.setText("<html>"+ "There are three levels in this game, completing the preceding level will allow you to continue to the next. In each level, you are tasked to solve a case before you run out of time. There will be objects around the “room” on screen which should be clicked on to gain the necessary information to solve the case. Click on New Game to begin level one. Have fun!" +"</html>");
    
    // Add to MainMenu panel
    add(instructions, BorderLayout.CENTER);
    add(createButtons(), BorderLayout.SOUTH);
    
  }
  
  private JPanel createButtons() {
    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    
    JPanel wrapper = new JPanel(new GridBagLayout());
    
    menu = new JButton("Main Menu");
    
    setButton(menu);
    
    buttons.add(menu);
    buttons.add(Box.createVerticalStrut(10));
    
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
    if(compare == menu) {
      game.window.getContentPane().removeAll();
      game.window.getContentPane().add(game.mainMenu);
    }
    
    game.window.validate();
    game.window.repaint();
  }
  
}