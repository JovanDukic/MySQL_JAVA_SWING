package mySQL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import icons.My_icon_renderer;
import interaface.GetEventObject;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField first_name;
	private JTextField last_name;
	private JTextField email;
	private JSpinner age;
	private JComboBox<Object> year_group;
	private JRadioButton male;
	private JRadioButton female;
	private JComboBox<Object> employed;
	private JCheckBox citizen;

	private JButton submit;

	private SpinnerModel spinnerModel;
	private DefaultComboBoxModel<Object> comboBoxModel;
	private DefaultComboBoxModel<Object> comboBoxModel1;
	private ButtonGroup buttonGroup;

	private GetEventObject eventObject;

	public MainPanel() {

		Border border = BorderFactory.createEtchedBorder();
		Border border2 = BorderFactory.createTitledBorder(border, "My_SQL");
		setBorder(border2);

		setBackground(new Color(176, 213, 222));

		Dimension dimension = getPreferredSize();
		dimension.width = 480;
		setPreferredSize(dimension);
		setMinimumSize(dimension);

		first_name = new JTextField(10);
		last_name = new JTextField(10);
		email = new JTextField(10);
		
		spinnerModel = new SpinnerNumberModel(10, 10, 120, 1);
		age = new JSpinner(spinnerModel);

		year_group = new JComboBox<>();
		comboBoxModel = new DefaultComboBoxModel<>();

		comboBoxModel.addElement("Under 18");
		comboBoxModel.addElement("adult");
		comboBoxModel.addElement("over 65");

		year_group.setModel(comboBoxModel);

		buttonGroup = new ButtonGroup();

		male = new JRadioButton("male");
		female = new JRadioButton("female");

		male.setActionCommand("male");
		female.setActionCommand("female");

		male.setSelected(true);

		buttonGroup.add(male);
		buttonGroup.add(female);

		employed = new JComboBox<>();
		comboBoxModel1 = new DefaultComboBoxModel<>();

		comboBoxModel1.addElement("unemployed");
		comboBoxModel1.addElement("self-employed");
		comboBoxModel1.addElement("employed");

		employed.setModel(comboBoxModel1);

		citizen = new JCheckBox("citizen");

		submit = new JButton("SUBMIT");

		submit.setIcon(My_icon_renderer.createIcon("/images/submit.png"));

		male.setBackground(new Color(176, 213, 222));
		female.setBackground(new Color(176, 213, 222));

		citizen.setBackground(new Color(176, 213, 222));

		submit.setBackground(new Color(69, 147, 165));
		submit.setForeground(new Color(255, 255, 255));

		submit.setPreferredSize(new Dimension(100, 50)); // PRVO PODESI VELICINU PA ONDA BORDER INACE CE DA SMANJI DUGME
															// NA NAJMANJU MOGUCU VELICINU !!!

		submit.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 3, true));

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String firstName = first_name.getText();
				String lastName = last_name.getText();
				String eMail = email.getText();
				int Age = (int) age.getValue();
				int year = year_group.getSelectedIndex();
				String Gender = buttonGroup.getSelection().getActionCommand();
				int Employed = employed.getSelectedIndex();
				boolean Citizen = citizen.isSelected();

				EventListener eventListezner = new EventListener(this, firstName, lastName, eMail, Age, year, Gender,
						Employed, Citizen);

				eventObject.eventListener(eventListezner);
			}
		});

		setLayout();

	}

	private void setLayout() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.NONE;

		gc.weighty = 0.1;

		gc.gridy = 0;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 10);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.weightx = 0.5;
		add(new JLabel("First name: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 0.5;
		add(first_name, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Last name: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(15, 0, 0, 0);
		add(last_name, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Enter your e-mail: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(15, 0, 0, 20);
		add(email, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Enter your age: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(15, 0, 0, 0);
		add(age, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Chooser your year-group: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(15, 0, 0, 0);
		add(year_group, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Chooser your gender: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(13, 0, 0, 0);
		add(male, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(10, 0, 0, 0);
		add(female, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Chooser your employment status: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(13, 0, 0, 0);
		add(employed, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(15, 0, 0, 10);
		add(new JLabel("Select if you are a citizen of Serbia: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(12, 0, 0, 0);
		add(citizen, gc);

		////////////////////////////// ++++

		gc.gridy++;

		gc.weightx = 10;
		gc.weighty = 10;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(90, 0, 0, 0);
		add(submit, gc);

	}

	public void setEventObject(GetEventObject eventObject) {
		this.eventObject = eventObject;
	}

}
