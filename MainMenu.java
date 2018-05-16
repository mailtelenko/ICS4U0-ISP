import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import java.awt.Dimension;

public class MainMenu extends JPanel{
  
  public MainMenu(){
    JPanel buttons = new JPanel();
    Dimension buttonDimension = new Dimension(40,40);
    
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    
    this.setLayout(new BorderLayout());
    
    JLabel title = new JLabel("Cyber Case");
    title.setAlignmentX(JLabel.CENTER);
    
    JButton newGame = new JButton("New Game");
    JButton rules = new JButton("Rules");
    JButton highScores = new JButton("High Scores");
    JButton exit = new JButton("Exit");
    
    
    //Horizontally center buttons
    newGame.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    rules.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    highScores.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    exit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    
    newGame.setPreferredSize(buttonDimension);
    
    //Add to buttons panel
    buttons.add(newGame);
    buttons.add(rules);
    buttons.add(highScores);
    buttons.add(exit);
    
    //Add to MainMenu panel
    add(title, BorderLayout.NORTH);
    add(buttons, BorderLayout.CENTER);
       
  }
  
}