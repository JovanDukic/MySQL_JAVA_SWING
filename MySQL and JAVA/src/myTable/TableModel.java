package myTable;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import interaface.Command_interface;
import mySQL.Person;

public class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Person> personList = new LinkedList<Person>();

	private Command_interface command_interface;

	private String[] columnName = { "ID", "first_name", "last_name", "email", "Age", "Year_Group", "gender", "employed",
			"citizen", "date", "time" };

	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

	@Override
	public int getRowCount() {
		return personList.size();
	}

	@Override
	public int getColumnCount() {
		return 11;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			return true;
		case 5:
			return false;
		case 6:
			return false;
		case 7:
			return false;
		case 8:
			return false;
		case 9:
			return false;
		case 10:
			return false;

		}
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = personList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return person.getID();
		case 1:
			return person.getFirst_name();
		case 2:
			return person.getLast_name();
		case 3:
			return person.getEmail();
		case 4:
			return person.getAge();
		case 5:
			return person.getYear_group();
		case 6:
			return person.getGender();
		case 7:
			return person.getEmployed();
		case 8:
			return person.isCitizen();
		case 9:
			return person.getDate();
		case 10:
			return person.getTime();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Person person = personList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			break;
		case 1:
			String columnName = "first_name";
			person.setFirst_name(aValue.toString().trim());
			command_interface.updateSQL(columnName, aValue, rowIndex);
			break;
		case 2:
			String columnName1 = "last_name";
			person.setLast_name(aValue.toString().trim());
			command_interface.updateSQL(columnName1, aValue, rowIndex);
			break;
		case 3:
			String columnName2 = "email";
			person.setEmail(aValue.toString().trim());
			command_interface.updateSQL(columnName2, aValue, rowIndex);
			break;
		case 4:
			String columnName3 = "age";
			person.setAge(Integer.parseInt(aValue.toString().trim()));
			command_interface.updateSQL(columnName3, aValue, rowIndex);
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		}
	}
	
	public void getPersonList(List<Person> list) {
		this.personList = list;
	}

	public void setCommand_interface(Command_interface command_interface) {
		this.command_interface = command_interface;
	}

}
