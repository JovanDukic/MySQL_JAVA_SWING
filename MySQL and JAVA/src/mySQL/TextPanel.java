package mySQL;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4853621421821957575L;

	private JTextArea paper;

	public TextPanel() {
		setLayout(new BorderLayout());

		paper = new JTextArea();

		add(paper, BorderLayout.CENTER);
	}

	public void saveText(File file, Component component) {

		if (file.exists()) {

			try {

				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true)); // AKO JE TRUE tada ce
																								// da dodaje tekst na
																								// postojeci, a ako je
																								// false onda brise i
																								// ONDA UPISUJE -- >
																								// BITNO
																								// JER JE MOPGUCI
																								// GUBITAK PODATAKA!!!

				System.out.println(paper.getText().toString());

				bufferedWriter.write(paper.getText());

				bufferedWriter.close();

				JOptionPane.showMessageDialog(component, "Text has been successfully saved!", "Save",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(component, "File doesn't exist!", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
