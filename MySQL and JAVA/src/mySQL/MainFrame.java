package mySQL;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import comparator.MyComparator;
import controller.Controller;
import icons.My_icon_renderer;
import interaface.Command_interface;
import interaface.Table_actions;
import interaface.GetEventObject;
import interaface.My_dialog_listener;
import interaface.Refreshing;
import interaface.RemovePerson;
import interaface.Search_by_key;
import interaface.SetTableNames;
import interaface.Sort;
import interaface.Warning_empty_key;
import interaface.Warning_panel;
import interaface.Warnings;
import javafx.application.Platform;
import myTable.TablePanel;
import sql_commands.SQL_Commands;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017246909330410203L;

	private Controller controller;
	private TablePanel tablePanel;
	private MainPanel mainPanel;
	private Toolbar_panel toolbar_panel;
	private MyDialog myDialog;
	private TextPanel textPanel;
	private MusicPanel musicPanel;
	

	private JSplitPane splitPane;

	private JTabbedPane tabbedPane; 

	private SQL_Commands commands;

	private MyComparator myComparator;

	private JFileChooser fileChooser;

	private Preferences preferences;

	public MainFrame() throws SQLException {

		setTitle("My_SQL_Table");
		setVisible(true);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setIconImage(My_icon_renderer.createIcon("/images/table.png").getImage()); // DOBIJANJE IMAGE OD ICON !!!

		setJMenuBar(setMenuBar());

		setLayout(new BorderLayout());

		textPanel = new TextPanel();
		
		musicPanel = new MusicPanel();

		preferences = Preferences.userRoot().node("database");

		toolbar_panel = new Toolbar_panel();

		myDialog = new MyDialog(this);

		fileChooser = new JFileChooser();

		myComparator = new MyComparator();

		commands = new SQL_Commands();

		controller = new Controller();

		mainPanel = new MainPanel();

		tablePanel = new TablePanel();

		tablePanel.getPersonList(controller.getPersonList());

		tabbedPane = new JTabbedPane(); 

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, tabbedPane); 
		splitPane.setOneTouchExpandable(true); 

		tabbedPane.add(tablePanel); 
		tabbedPane.add(textPanel); 
		tabbedPane.add(musicPanel);

		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(tablePanel),
				new TabbedPaneCloseBtn(tabbedPane, tablePanel, "Table",
						My_icon_renderer.createIcon("/images/table1.png"),
						My_icon_renderer.createIcon("/images/exit1.png")));

		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(textPanel),
				new TabbedPaneCloseBtn(tabbedPane, textPanel, "Notepad",
						My_icon_renderer.createIcon("/images/notepad.png"),
						My_icon_renderer.createIcon("/images/exit1.png")));
		
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(musicPanel),
				new TabbedPaneCloseBtn(tabbedPane, musicPanel, "Mp3 player",
						My_icon_renderer.createIcon("/images/notepad.png"),
						My_icon_renderer.createIcon("/images/exit1.png")));

		tabbedPane.setBorder(BorderFactory.createEtchedBorder());

		add(splitPane, BorderLayout.CENTER);
		add(toolbar_panel, BorderLayout.NORTH);

		mainPanel.setEventObject(new GetEventObject() {

			@Override
			public void eventListener(EventListener eventListener) {
				controller.setConnection(commands.getConnection());
				controller.setPerons(eventListener, toolbar_panel.getTableNameSelect());
				tablePanel.refreshTable();
			}
		});

		controller.setRefreshing(new Refreshing() {

			@Override
			public void refresh() {
				controller.clearPersonList();
				controller.setList(commands.selectAll(toolbar_panel.getTableNameSelect()));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}
		});

		tablePanel.setRemovePerson(new RemovePerson() {

			@Override
			public void removePerson(int ID) {
				commands.deletePerson(toolbar_panel.getTableNameSelect(), ID, controller.getPersonList());
				controller.removePerson(ID);
			}
		});

		tablePanel.setCommand_interface(new Command_interface() {

			@Override
			public void updateSQL(String columnName, Object aValue, int rowIndex) {
				commands.updateMySQL(columnName, aValue, rowIndex, controller.getPersonList(),
						toolbar_panel.getTableNameSelect());
				System.out.println("RADI");
			}
		});

		toolbar_panel.setSort(new Sort() {

			@Override
			public void unsort() {
				controller.clearPersonList();
				controller.setList(commands.selectAll(toolbar_panel.getTableNameSelect()));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}

			@Override
			public void sort(String command) {
				controller.clearPersonList();
				int columnToSort = tablePanel.getColumnToSort();
				List<Person> personList = commands.sortTable(columnToSort, controller.getPersonList(), command,
						toolbar_panel.getTableNameSelect());
				controller.setList(personList);
				tablePanel.getPersonList(personList);
				tablePanel.refreshTable();
			}
		});

		toolbar_panel.setTable_actions(new Table_actions() {

			@Override
			public void deleteTable() {
				String tableName = JOptionPane.showInputDialog(MainFrame.this,
						"Insert a name of tabe which you want to delete:", "Delete table", JOptionPane.WARNING_MESSAGE);
				commands.deleteTable(tableName);
			}

			@Override
			public void createTable() {
				String tableName = JOptionPane.showInputDialog(MainFrame.this, "Insert a table name:", "Create table",
						JOptionPane.WARNING_MESSAGE);
				commands.createTable(tableName);
			}
		});

		toolbar_panel.setBy_key(new Search_by_key() {

			@Override
			public void search_key(String key) {
				List<Person> personList = controller.getPersonList().stream().collect(Collectors.toList());
				controller.clearPersonList();
				controller.setList(myComparator.compare_by_key(key, tablePanel.getColumnToSort(), personList));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}

			@Override
			public void remove_key() {
				controller.clearPersonList();
				controller.setList(commands.selectAll(toolbar_panel.getTableNameSelect()));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}
		});

		toolbar_panel.setRefreshing(new Refreshing() {

			@Override
			public void refresh() {
				controller.clearPersonList();
				controller.setList(commands.selectAll(toolbar_panel.getTableNameSelect()));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}
		});

		myComparator.setWarning_panel(new Warning_panel() {

			@Override
			public void warning(int column) {
				switch (column) {
				case 0:
					JOptionPane.showMessageDialog(MainFrame.this, "You cant use search key in ID column!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				case 8:
					JOptionPane.showMessageDialog(MainFrame.this, "Table is empty, you need to connect to your DB !",
							"Warning", JOptionPane.INFORMATION_MESSAGE);
					break;
				case 9:
					JOptionPane.showMessageDialog(MainFrame.this, "You cant use search key in Date column!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				case 10:
					JOptionPane.showMessageDialog(MainFrame.this, "You cant use search key in Time column!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					break;

				}
			}
		});

		myComparator.setWarning_empty_key(new Warning_empty_key() {

			@Override
			public void warning_no_key() {
				JOptionPane.showMessageDialog(MainFrame.this, "You can't search an empty key !", "Search_key Warning",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		commands.setWarnings(new Warnings() {

			@Override
			public void warningDB() {
				JOptionPane.showMessageDialog(MainFrame.this, "Connection doesn't exist !", "WarningDB",
						JOptionPane.INFORMATION_MESSAGE);
			}

			@Override
			public void SameTableName(String tableName) {
				JOptionPane.showMessageDialog(MainFrame.this, "Table with name " + tableName + " already exists !",
						"WarningDB", JOptionPane.INFORMATION_MESSAGE);
			}

			@Override
			public void noTableName(String tableName) {
				JOptionPane.showMessageDialog(MainFrame.this, "Table with name " + tableName + " doesn't exist !",
						"WarningDB", JOptionPane.INFORMATION_MESSAGE);
			}

		});

		commands.setSetTableNames(new SetTableNames() {

			@Override
			public void setTableNames(LinkedHashSet<String> tableNames) {
				toolbar_panel.setComboBoxModel(tableNames);
			}

			@Override
			public void addTableNames(String tableName) {
				toolbar_panel.addTableName(tableName);
			}

			@Override
			public void removeTableNames(String tableName) {
				toolbar_panel.removeTableName(tableName);
			}
		});

		myDialog.setDialog_listener(new My_dialog_listener() {

			@Override
			public void dialogSet(String user, String password, int port) {
				preferences.put("user", user);
				preferences.put("password", password);
				preferences.putInt("port", port);

				commands.setUsername(user);
				commands.setPassword(password);
				commands.setPort(port);

				commands.createConnection();
			}
		});

		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		Integer port = preferences.getInt("port", 3306);

		myDialog.setDefaults(user, password, port);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to exit ?", "Exit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					dispose();
					System.gc();
					Platform.exit();
				}
			}

		});

	}

	public JMenuBar setMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu window = new JMenu("Window");
		JMenu tools = new JMenu("Tools");

		JMenu editor = new JMenu("Editor");

		JMenuItem save = new JMenuItem("Save");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem deleteTable = new JMenuItem("Delete table");
		JMenuItem createTable = new JMenuItem("Create table");
		JMenuItem refresh = new JMenuItem("Refresh");
		JMenuItem defaultWindow = new JMenuItem("Reset tabs");

		JMenuItem dialog = new JMenuItem("Preferences..."); // JDIALOG po dogovoru idu ... posle naziva!!!

		JCheckBoxMenuItem showTablePanel = new JCheckBoxMenuItem("show table");
		JCheckBoxMenuItem showUserInput = new JCheckBoxMenuItem("show user input");

		showTablePanel.setSelected(true);
		showUserInput.setSelected(true);

		editor.add(showUserInput);
		editor.addSeparator();
		editor.add(showTablePanel);

		window.add(editor);
		window.addSeparator();
		window.add(dialog);
		window.add(defaultWindow);

		file.add(save);
		file.add(open);
		file.addSeparator();
		file.add(exit);

		deleteTable.setIcon(My_icon_renderer.createIcon("/images/delete table.png"));
		createTable.setIcon(My_icon_renderer.createIcon("/images/create table.png"));
		defaultWindow.setIcon(My_icon_renderer.createIcon("/images/tab.png"));
		dialog.setIcon(My_icon_renderer.createIcon("/images/preferences.png"));

		tools.add(createTable);
		tools.addSeparator();
		tools.add(deleteTable);
		tools.addSeparator();
		tools.add(refresh);

		menuBar.add(file);
		menuBar.add(window);
		menuBar.add(tools);

		exit.setIcon(My_icon_renderer.createIcon("/images/exit.png"));
		save.setIcon(My_icon_renderer.createIcon("/images/save.png"));
		open.setIcon(My_icon_renderer.createIcon("/images/open.png"));
		refresh.setIcon(My_icon_renderer.createIcon("/images/refresh2.png"));

		showTablePanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = showTablePanel.isSelected();

				if (selected) {
					tablePanel.setVisible(true);
				} else {
					tablePanel.setVisible(false);
				}
			}
		});

		showUserInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = showUserInput.isSelected();

				if (selected) {
					splitPane.setDividerLocation((int) mainPanel.getMinimumSize().getWidth());
					mainPanel.setVisible(true);
				} else {
					mainPanel.setVisible(false);
				}
			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					textPanel.saveText(fileChooser.getSelectedFile(),MainFrame.this);
				}

			}
		});

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					System.out.println("RADIS");
				}
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to exit?", "EXIT",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
					dispose();
					System.gc();
				}
				;
			}
		});

		dialog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myDialog.setVisible(true);
			}
		});

		deleteTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tableName = JOptionPane.showInputDialog(MainFrame.this,
						"Insert a name of tabe which you want to delete:", "Delete table", JOptionPane.WARNING_MESSAGE);
				commands.deleteTable(tableName);
			}
		});

		createTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tableName = JOptionPane.showInputDialog(MainFrame.this, "Insert a table name:", "Create table",
						JOptionPane.WARNING_MESSAGE);
				commands.createTable(tableName);
			}
		});

		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.clearPersonList();
				controller.setList(commands.selectAll(toolbar_panel.getTableNameSelect()));
				tablePanel.getPersonList(controller.getPersonList());
				tablePanel.refreshTable();
			}
		});

		defaultWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.add(tablePanel);
				tabbedPane.add(textPanel);
				tabbedPane.add(musicPanel);

				tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(tablePanel),
						new TabbedPaneCloseBtn(tabbedPane, tablePanel, "Table",
								My_icon_renderer.createIcon("/images/table1.png"),
								My_icon_renderer.createIcon("/images/exit1.png")));

				tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(textPanel),
						new TabbedPaneCloseBtn(tabbedPane, textPanel, "Notepad",
								My_icon_renderer.createIcon("/images/notepad.png"),
								My_icon_renderer.createIcon("/images/exit1.png")));
				
				tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(musicPanel),
						new TabbedPaneCloseBtn(tabbedPane, musicPanel, "Mp3 player",
								My_icon_renderer.createIcon("/images/notepad.png"),
								My_icon_renderer.createIcon("/images/exit1.png")));

			}
		});

		return menuBar;

	}

}
