package app;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import mySQL.MainFrame;

public class App {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new JFXPanel();

				try {

					new MainFrame();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
