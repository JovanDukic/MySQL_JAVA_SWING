package mySQL;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import icons.My_icon_renderer;
import interaface.Table_actions;
import interaface.Refreshing;
import interaface.Search_by_key;
import interaface.Sort;

public class Toolbar_panel extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3779071860978365868L;

	private String tableNameSelect;

	private JCheckBox toSort;

	private JRadioButton asc;
	private JRadioButton desc;
	private JTextField enter_key;
	private JButton search_key;
	private JButton remove_key;
	private JButton refresh;
	private JButton deleteTable;
	private JButton createNewTable;

	private JComboBox<String> tableNames;
	private DefaultComboBoxModel<String> comboBoxModel;

	private Search_by_key by_key;
	private Sort sort;

	private ButtonGroup buttonGroup;

	private Refreshing refreshing;

	private Table_actions table_actions;

	public Toolbar_panel() {

		Border border = BorderFactory.createEtchedBorder();
		setBorder(border);

		setBackground(new Color(47, 121, 183)); // IDI U WORD FONT FONT COLOR --> MORE COLORS --> TRAZI RGB, ALFA
												// FAKTOR ODREDJUJE SVETLINU BOJE, BOJA KOJU UzIMAS MORA DA BUDE
												// NA 255 !!!

		setLayout(new FlowLayout(FlowLayout.LEFT));

		comboBoxModel = new DefaultComboBoxModel<>();

		tableNames = new JComboBox<>();
		tableNames.setModel(comboBoxModel);

		createNewTable = new JButton("Create new table");
		createNewTable.setIcon(My_icon_renderer.createIcon("/images/create table.png"));

		deleteTable = new JButton("Delete table");
		deleteTable.setIcon(My_icon_renderer.createIcon("/images/delete table.png"));

		refresh = new JButton("Refresh");
		refresh.setIcon(My_icon_renderer.createIcon("/images/refresh2.png"));

		enter_key = new JTextField(10);

		search_key = new JButton("Search key");
		search_key.setIcon(My_icon_renderer.createIcon("/images/search.png"));

		remove_key = new JButton("Remove key");
		remove_key.setIcon(My_icon_renderer.createIcon("/images/remove1.png"));

		toSort = new JCheckBox("sort");
		add(toSort);

		asc = new JRadioButton("ascending");
		desc = new JRadioButton("descending");

		asc.setActionCommand("asc");
		desc.setActionCommand("desc");

		asc.setSelected(true);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(asc);
		buttonGroup.add(desc);

		toSort.setBackground(new Color(69, 147, 165));
		toSort.setForeground(new Color(255, 255, 255));

		asc.setBackground(new Color(69, 147, 165));
		asc.setForeground(new Color(255, 255, 255));

		desc.setBackground(new Color(69, 147, 165));
		desc.setForeground(new Color(255, 255, 255));

		search_key.setBackground(new Color(69, 147, 165));
		search_key.setForeground(new Color(255, 255, 255));

		remove_key.setBackground(new Color(69, 147, 165));
		remove_key.setForeground(new Color(255, 255, 255));

		refresh.setBackground(new Color(69, 147, 165));
		refresh.setForeground(new Color(255, 255, 255));

		deleteTable.setBackground(new Color(69, 147, 165));
		deleteTable.setForeground(new Color(255, 255, 255));
		
		createNewTable.setBackground(new Color(69, 147, 165));
		createNewTable.setForeground(new Color(255, 255, 255));

		add(asc);
		add(desc);
		add(enter_key);
		add(search_key);
		add(remove_key);
		add(refresh);
		add(deleteTable);
		add(createNewTable);
		add(tableNames);

		toSort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = toSort.isSelected();

				String command = buttonGroup.getSelection().getActionCommand();

				System.out.println(command);

				if (selected) {
					sort.sort(command);
				} else {
					sort.unsort();
				}
			}
		});

		search_key.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String key = enter_key.getText();
				by_key.search_key(key);
			}
		});

		remove_key.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enter_key.setText("");
				by_key.remove_key();
			}
		});

		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshing.refresh();
			}
		});

		deleteTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				table_actions.deleteTable();
			}
		});

		createNewTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				table_actions.createTable();
			}
		});

		tableNames.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				tableNameSelect = tableNames.getSelectedItem().toString(); // ovde moze refresh ali i ne mora !!!
			}
		});

	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public void setBy_key(Search_by_key by_key) {
		this.by_key = by_key;
	}

	public void setRefreshing(Refreshing refreshing) {
		this.refreshing = refreshing;
	}

	public void setTable_actions(Table_actions table_actions) {
		this.table_actions = table_actions;
	}

	public void setComboBoxModel(LinkedHashSet<String> tableNames) {

		for (String tableName : tableNames) {

			comboBoxModel.addElement(tableName);

		}

		this.tableNames.setSelectedIndex(0);

	}

	public void addTableName(String tableName) {
		comboBoxModel.addElement(tableName);
	}

	public void removeTableName(String tableName) {
		comboBoxModel.removeElement(tableName);
	}

	public String getTableNameSelect() {
		return tableNameSelect;
	}

}
