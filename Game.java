import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;

public class Game extends JFrame{
  MainMenu m = new MainMenu();
  
  
  public Game(){
    JFrame window = new JFrame("Cyber Case");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(m);
    window.setSize(600,400);
    window.setVisible(true);
    
  }
  
  
  public static void main(String[] args){
    Game g = new Game();
  }
  
}
  