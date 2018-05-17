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
import javax.swing.JTextField;

public class Setup extends MenuParent {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  JButton cont;
  Game game;
  
  public Setup(Game gme) {
    super(gme, "Setup");
    game = gme;
    
    JLabel prompt = new JLabel();
    prompt.setText("<html>"+ "Please enter your name: " +"</html>");
    
    JTextField textField = new JTextField(30);
    setVisible(true);
    textField.requestFocusInWindow();
    String name = textField.getText();
    textField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        System.out.println("The entered text is: " + textField.getText());
      }
    });
    
    // Add to Setup panel
    add(prompt);
    add(textField, BorderLayout.CENTER);
    add(createButtons(), BorderLayout.SOUTH);
    
  }
  
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
    if(compare == cont) {
//      game.window.getContentPane().removeAll();
//      game.window.getContentPane().add(game.levelOne);
      System.out.println("Continue");
    }
    
//    game.window.validate();
//    game.window.repaint();
  }
}