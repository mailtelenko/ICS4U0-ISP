import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class MenuParent extends JPanel{
  
  Game window;
  Dimension buttonDimension = new Dimension(140, 32);
  
  public MenuParent(Game game, String titleStr) {
    window = game;
    this.setLayout(new BorderLayout());
    JLabel title = new JLabel(titleStr);
    
    // Add to MainMenu panel
    add(title, BorderLayout.NORTH);    
  }
  
  
  public void setButton(JButton button) {
    //Set size
    button.setPreferredSize(buttonDimension);
    button.setMinimumSize(buttonDimension);
    button.setMaximumSize(buttonDimension);
    
    //Style Buttons
    button.setFocusPainted(false);
    button.setForeground(Color.BLACK);
    button.setBackground(Color.decode("#3a7ce8"));
    
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        buttonClicked(e);
      }
    });
  }
  
  public JButton createCenterMenu(){
    JButton menu = new JButton("Main Menu");
    setButton(menu);
    return menu;
  }
  
  public JButton createCenterMenu(JPanel panel){
    JButton menu = new JButton("Main Menu");
    panel.add(menu);
    panel.add(Box.createVerticalStrut(10));
    setButton(menu);
    return menu;
  }
  
  public void createDualButtons(JPanel panel, JButton button1, JButton button2){
    JPanel flow = new JPanel(new FlowLayout());
    flow.add(Box.createVerticalStrut(10));
    flow.add(button1);
    flow.add(button2);
    panel.add(flow);
  }
  
  public abstract void buttonClicked(ActionEvent e);
  
}

