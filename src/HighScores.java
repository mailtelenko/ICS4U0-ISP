import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.BufferedReader;
import java.io.FileReader;

public class HighScores extends MenuParent{
  
  private Game game;
  private JButton menu, erase;
  ArrayList<String> scores;
  
  public HighScores(Game gme){
    super(gme, "High Scores");
    game = gme;
    
    JPanel scorePanel = new JPanel(new GridBagLayout());
    
    scores = readScores();
    for (int x = 0; x < scores.size(); x++){
      scorePanel.add(new JLabel(scores.get(x)));
    }
    
    add(scorePanel);
    
    // Add to MainMenu panel
    add(createButtons(), BorderLayout.SOUTH);
  }
  
  private JPanel createButtons() {
    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    
    JPanel wrapper = new JPanel(new GridBagLayout());
    
    JButton erase = new JButton("Erase");
    setButton(erase);
    
    menu = createCenterMenu(buttons);
    
    createDualButtons(buttons, menu, erase);
    
    wrapper.add(buttons);
    
    return wrapper;
  }
  
  private ArrayList<String> readScores(){
    ArrayList<String> input = new ArrayList<String>();
    String temp;
    try{
      BufferedReader dataIn = new BufferedReader(new FileReader("../resources/data/highScores.txt"));
      while ((temp = dataIn.readLine()) != null) {
        input.add(temp); 
      }
    }catch(Exception e){
      System.out.println(e);
    }
    return input;
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