package myTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import icons.My_icon_renderer;
import interaface.Command_interface;
import interaface.RemovePerson;
import mySQL.Person;
import my_listeners.My_Table_Listener;

public class TablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private TableModel tableModel;

	private static int prefwidth = 140;

	private MyCellRenderer myCellRenderer;
	private DefaultCellEditor cellEditor;

	private JPopupMenu popupMenu;

	private RemovePerson removePerson;

	private int removeID;

	private My_Table_Listener table_Listener;

	private Command_interface command_interface;

	private int columnToSort;

	public TablePanel() {
		Border border = BorderFactory.createEtchedBorder();
		Border border2 = BorderFactory.createTitledBorder(border, "SQL_Table");
		setBorder(border2);
		
		tableModel = new TableModel();
		table = new JTable(tableModel);

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);

		table_Listener = new My_Table_Listener();

		tableModel.addTableModelListener(table_Listener);

		myCellRenderer = new MyCellRenderer();

		table.setDefaultRenderer(Object.class, myCellRenderer);

		cellEditor = new DefaultCellEditor(new JTextField());

		cellEditor.setClickCountToStart(2);

		table.setDefaultEditor(Object.class, cellEditor);

		popupMenu = new JPopupMenu();

		JMenuItem remove = new JMenuItem("Remove person");
		JMenuItem add = new JMenuItem("Add person");
		JMenuItem insert = new JMenuItem("Insert new row");
		
		remove.setIcon(My_icon_renderer.createIcon("/images/removePerson.png"));
		add.setIcon(My_icon_renderer.createIcon("/images/addPerson.png"));
		insert.setIcon(My_icon_renderer.createIcon("/images/insertRow.png"));

		popupMenu.add(remove);
		popupMenu.addSeparator();

		popupMenu.add(add);
		popupMenu.addSeparator();

		popupMenu.add(insert);
		
		for (int i = 0; i < tableModel.getColumnCount(); i++) {

			if (i == 0) {

				table.getColumnModel().getColumn(i).setPreferredWidth(40);

			} else {

				table.getColumnModel().getColumn(i).setPreferredWidth(prefwidth);

			}
		}

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {

					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.setCellSelectionEnabled(true);

				} else if (e.getButton() == MouseEvent.BUTTON1) {

					int column = table.columnAtPoint(e.getPoint());

					table.setColumnSelectionAllowed(true);
					table.setRowSelectionAllowed(false);

					columnToSort = column;

					System.out.println("Odabrao si: " + column + " za sortiranje!");

				} else if (e.getButton() == MouseEvent.BUTTON3) {

					int row = table.rowAtPoint(e.getPoint());

					table.setRowSelectionAllowed(true);
					table.setColumnSelectionAllowed(false);

					table.getSelectionModel().setSelectionInterval(row, row);

					popupMenu.show(table, e.getX(), e.getY());

					System.out.println(row);

					removeID = row;
				}
			}

		});

		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removePerson.removePerson(removeID);
				tableModel.fireTableRowsDeleted(removeID, removeID);
			}
		});

		tableModel.setCommand_interface(new Command_interface() {

			@Override
			public void updateSQL(String columnName, Object aValue, int rowIndex) {
				command_interface.updateSQL(columnName, aValue, rowIndex);

			}
		});

	}

	public void getPersonList(List<Person> list) {
		tableModel.getPersonList(list);
	}

	public void refreshTable() {
		tableModel.fireTableDataChanged();
	}

	public void setRemovePerson(RemovePerson removePerson) {
		this.removePerson = removePerson;
	}

	public int getColumnToSort() {
		return columnToSort;
	}

	public void setCommand_interface(Command_interface command_interface) {
		this.command_interface = command_interface;
	}

}
