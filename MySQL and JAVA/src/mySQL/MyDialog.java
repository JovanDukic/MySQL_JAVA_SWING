package mySQL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import icons.My_icon_renderer;
import interaface.My_dialog_listener;

public class MyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 679399420919841383L;

	private JButton okButton;
	private JButton cancleButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField username;
	private JPasswordField password;

	private My_dialog_listener dialog_listener;

	public MyDialog(JFrame parent) {
		super(parent, "Preferences", true); // TRUE mora da se iskljucivo radi na dialogu neda prelazak na druge
											// prozore!!!
		setSize(300, 200);
		setIconImage(My_icon_renderer.createIcon("/images/preferences.png").getImage());
		okButton = new JButton("OK");
		cancleButton = new JButton("Cancle");

		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);

		username = new JTextField(10);
		password = new JPasswordField(10);

		password.setEchoChar('*'); // Podesavas znak koji ce biti umesto slova!!!

		setLocationRelativeTo(parent);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer port = (Integer) portSpinner.getValue();
				String user = username.getText();

				char[] pass = password.getPassword(); // VRACA NIZ ZNAKOVA

				if (dialog_listener != null) {
					dialog_listener.dialogSet(user, new String(pass), port);
				}

				setVisible(false);
			}
		});

		cancleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setLayout();

	}

	private void setLayout() {

		JPanel controlPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		Border spaceBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);

		controlPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

		controlPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.NONE;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridy = 0;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.EAST; // NE GURA DO VRHA KAO FIRST LINE END ILI START !!!
		controlPanel.add(new JLabel("User: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		controlPanel.add(username, gc);

		/////////////////////// NEXT ROW ++++++++++
		gc.gridy++;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.EAST;
		controlPanel.add(new JLabel("Password: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		controlPanel.add(password, gc);

		/////////////////////// NEXT ROW ++++++++++
		gc.gridy++;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.EAST;
		controlPanel.add(new JLabel("Port: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		controlPanel.add(portSpinner, gc);

		/////////////////////// SETTING UP BUTTON PANEL !!!
		buttonPanel.setLayout(new GridBagLayout());

		gc.gridy = 0;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 15, 5);
		gc.anchor = GridBagConstraints.EAST;
		buttonPanel.add(okButton, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 5, 15, 0);
		gc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(cancleButton, gc);

		Dimension buttonSize = cancleButton.getPreferredSize();

		okButton.setPreferredSize(buttonSize);

		//////////////////////// ADDING STUFF TO J-DIALOG !!!
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	public void setDialog_listener(My_dialog_listener dialog_listener) {
		this.dialog_listener = dialog_listener;
	}

	public void setDefaults(String user, String pass, int port) {
		username.setText(user);
		password.setText(pass);
		portSpinner.setValue(port);
	}

}
