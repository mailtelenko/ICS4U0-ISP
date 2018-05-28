import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Quiz extends MenuParent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int level;
	int currentQuestion = 0;
	Game game;

	JLabel questionLabel;

	ArrayList<String[]> questions = new ArrayList<String[]>();
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	ArrayList<Boolean> questionUsed = new ArrayList<Boolean>();

	public Quiz(Game g, int l) {
		super(g, "Level " + l + " Quiz");
		JPanel container = new JPanel(new GridBagLayout());
		JPanel wrapper = new JPanel(new BorderLayout());
		JPanel questionContainer = new JPanel();
		game = g;
		level = l;

		questionLabel = new JLabel("Testing string", SwingConstants.CENTER);
		questionLabel.setForeground(Color.WHITE);
		questionLabel.setFont((new Font("Cambria Math", Font.PLAIN, 20)));

		fillQuestions();

		questionContainer.add(Box.createVerticalStrut(100));
		questionContainer.setBackground(backgroundColor);
		questionContainer.add(questionLabel);

		wrapper.add(questionContainer, BorderLayout.NORTH);

		container.add(createButtons());

		wrapper.add(container, BorderLayout.CENTER);

		container.setBackground(backgroundColor);
		wrapper.setBackground(backgroundColor);

		add(wrapper);

		chooseQuestion();
	}

	public void fillQuestions() {
		String temp; // Empty temporary string
		String[] tempArray = new String[] { "", "" }; // Empty temporary array

		try { // Read from file
				// Open input from file
			BufferedReader dataIn = new BufferedReader(
					new FileReader("resources/data/levels/level" + level + "Quiz.txt"));
			// While there are no empty lines in the file add the lines to the ArrayList
			while ((temp = dataIn.readLine()) != null) {

				tempArray[0] = temp.substring(0, temp.indexOf("/")); // Parse button
				tempArray[1] = temp.substring(temp.indexOf("/") + 1); // Parse question
				questions.add(new String[] { tempArray[0], tempArray[1] });
			}
			dataIn.close(); // Close connection to file
		} catch (Exception e) { // Catch exception
			System.out.println(e); // Print out exception
		}
	}

	public JPanel createButtons() {
		JPanel container = new JPanel(new FlowLayout());
		JPanel centredPanel = new JPanel(new GridBagLayout());

		container.setBackground(backgroundColor);

		for (int x = 0; x < questions.size(); x++) {
			buttons.add(new JButton(questions.get(x)[1]));
			setButton(buttons.get(x));
			container.add(buttons.get(x));
			questionUsed.add(false);
		}

		// Set dimensions
		container.setMinimumSize(new Dimension(700, 40 * (questions.size() / 4)));
		container.setPreferredSize(new Dimension(700, 40 * (questions.size() / 4)));
		container.setMaximumSize(new Dimension(700, 40 * (questions.size() / 4)));

		centredPanel.add(container);
		return centredPanel;
	}

	public void chooseQuestion() {
		Boolean[] questionsChecked = new Boolean[questionUsed.size()];
		int checked = 0;
		int temp;

		for (int x = 0; x < questionsChecked.length; x++)
			questionsChecked[x] = false;

		do {
			temp = (int) (Math.random() * (questionUsed.size()));
			if (questionUsed.get(temp)) {
				if (!questionsChecked[temp]) {
					checked++;
					questionsChecked[temp] = true;
				}
			} else {
				changeQuestion(temp);
				questionUsed.set(temp, true);
				currentQuestion = temp;
				return;
			}
		} while (checked < questionUsed.size());
		System.out.println("all questions done");
	}

	public void changeQuestion(int number) {
		questionLabel.setText(questions.get(number)[0]);
	}

	public void buttonClicked(ActionEvent e) {
		if(((JButton)e.getSource()).getText().equals(questions.get(currentQuestion)[1])) {
			System.out.println("CORRECT");
		}else {
			System.out.println("INCORRECT");
		}
		chooseQuestion();
	}

}
