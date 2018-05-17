import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Liam Telenko, Russell Leong
 *
 */
public class Game extends JFrame{

 /**
  * 
  */
 private static final long serialVersionUID = 1L;
 
 public MainMenu m;
 public Rules r;
 public JFrame window;
 
 public Game() {
   window = new JFrame("Cyber Case");
   m  = new MainMenu(this);
   r = new Rules(this); 
   
   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   window.add(r);
   window.setSize(600,400);
   window.setVisible(true);
 }
 
 /**
  * @param args
  */
 public static void main(String[] args) {
  Game g = new Game();
 }

}
