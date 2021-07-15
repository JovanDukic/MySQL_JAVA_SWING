package mySQL;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class TabbedPaneCloseBtn extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3788478348330463579L;

	private JButton closeButton;
	private JLabel title;

	public TabbedPaneCloseBtn(JTabbedPane tabbedPane, Component component, String text, Icon icon, Icon icon1) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setOpaque(false);

		title = new JLabel(text);
		title.setIcon(icon);

		// DA SE VIDI SAMO SLIKA NA JBUTTON --> POSTAVKE !!!
		closeButton = new JButton();
		closeButton.setBorder(null);
		closeButton.setContentAreaFilled(false);
		closeButton.setIcon(icon1);
		closeButton.setFocusPainted(false);
		closeButton.setToolTipText("Click to close the tab");

		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setPreferredSize(new Dimension(16, 16));

		add(title);
		add(closeButton);

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.remove(component);

			}
		});

	}

}
