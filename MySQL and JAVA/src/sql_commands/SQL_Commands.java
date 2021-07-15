package sql_commands;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import enums.Employed;
import enums.Gender;
import enums.Year_Group;
import interaface.SetTableNames;
import interaface.Warnings;
import mySQL.Person;

public class SQL_Commands {

	private SetTableNames setTableNames;

	private LinkedHashSet<String> tableNames = new LinkedHashSet<String>();

	private Warnings warnings;

	private Connection connection = null;
	private String url = "";
	private String username = "";
	private String password = "";
	private int port;

	private String updatePerson = "update %s set %s = ? where id = ?";

	private String sortTable = "select * from %s order by %s %s";

	private String selectAll = "select * from %s";

	private String deletePerson = "delete from %s where id = ?";

	// private String checkSQL = "select count(*) as 'count' from person where id =
	// ?";

	// private String inserInto = "insert into person(first_name, last_name, email,
	// age, year_group, gender, employed, citizen) values (?,?,?,?,?,?,?,?)";

	// private String searchKey = "select * from person where %s like ?";

	private List<Person> personList = new LinkedList<Person>();

	// private Warning_panel warning_panel;

	public void updateMySQL(String columnName, Object value, int row, List<Person> personList, String tableName) {

		int ID = personList.get(row).getID();
		System.out.println(ID);

		if (connection != null) {

			String updateTable = String.format(updatePerson, tableName, columnName);

			try {

				// int ID = row + 1; //// JAVA BROJI REDOVE OD 0 A SQL OD 1 !!!!!! ILI OVO ILI
				// ++row (++row --> povecava za 1 i koristi ga, dok row++ prvo ga koristi, a
				// zatim povecava)

				if (value instanceof Integer) {
					int put = (int) (value);

					PreparedStatement update = connection.prepareStatement(updateTable);

					update.setInt(1, put);

					update.setInt(2, ID); /// ++row !!!

					System.out.println(update);

					int rowsAffected = update.executeUpdate();

					if (rowsAffected != 0) {
						System.out.println("Update je uspesno izvrsen!\n");
					} else {
						System.out.println("Update propo!\n");
					}

				} else {

					PreparedStatement update = connection.prepareStatement(updateTable);

					update.setString(1, value.toString());

					update.setInt(2, ID); /// ++row !!!

					System.out.println(update);

					int rowsAffected = update.executeUpdate();

					if (rowsAffected != 0) {
						System.out.println("Update je uspesno izvrsen!\n");
					} else {
						System.out.println("Update propo!\n");
					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			warnings.warningDB();
		}

	}

	public List<Person> sortTable(int column, List<Person> personList, String command, String tableName) {

		if (connection != null) {

			String typeOfSort = "";

			if (command.equals("asc")) {
				typeOfSort = "asc";
			} else {
				typeOfSort = "desc";
			}

			String columnName = "";

			switch (column) {
			case 0:
				columnName = "id";
				break;
			case 1:
				columnName = "first_name";
				break;
			case 2:
				columnName = "last_name";
				break;
			case 3:
				columnName = "email";
				break;
			case 4:
				columnName = "age";
				break;
			case 5:
				columnName = "year_group";
				break;
			case 6:
				columnName = "gender";
				break;
			case 7:
				columnName = "employed";
				break;
			case 8:
				columnName = "citizen";
				break;
			case 9:
				columnName = "created_at";
				break;
			case 10:
				columnName = "created_at";
				break;
			}

			try {

				String sort = String.format(sortTable, tableName, columnName, typeOfSort);

				System.out.println(sort);

				Statement sorting = connection.createStatement();

				ResultSet sorted = sorting.executeQuery(sort);

				while (sorted.next()) {
					int ID = sorted.getInt(1);
					String first_name = sorted.getString(2);
					String last_name = sorted.getString(3);
					String email = sorted.getString(4);
					int age = sorted.getInt(5);
					Year_Group year_group = Year_Group.valueOf(sorted.getString(6));
					Gender gender = Gender.valueOf(sorted.getString(7));
					Employed employed = Employed.valueOf(sorted.getString(8));
					boolean citizen = sorted.getBoolean(9);
					Date date = sorted.getDate(10);
					Time time = sorted.getTime(10);

					Person person = new Person(first_name, last_name, email, age, year_group, gender, employed,
							citizen);

					person.setID(ID);
					person.setDate(date);
					person.setTime(time);

					personList.add(person);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return personList;
		} else {
			warnings.warningDB();
		}

		this.personList.clear();
		return this.personList;

	}

	public List<Person> selectAll(String tableName) {

		if (connection != null) {

			personList.clear();

			String selec = String.format(selectAll, tableName);

			try {

				Statement select = connection.createStatement();

				ResultSet selected = select.executeQuery(selec);

				while (selected.next()) {
					int ID = selected.getInt(1);
					String first_name = selected.getString(2);
					String last_name = selected.getString(3);
					String email = selected.getString(4);
					int age = selected.getInt(5);
					Year_Group year_group = Year_Group.valueOf(selected.getString(6));
					Gender gender = Gender.valueOf(selected.getString(7));
					Employed employed = Employed.valueOf(selected.getString(8));
					boolean citizen = selected.getBoolean(9);
					Date date = selected.getDate(10);
					Time time = selected.getTime(10);

					Person person = new Person(first_name, last_name, email, age, year_group, gender, employed,
							citizen);

					person.setID(ID);
					person.setDate(date);
					person.setTime(time);

					personList.add(person);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return personList;
		} else {
			warnings.warningDB();
		}

		this.personList.clear();
		return this.personList;

	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void createConnection() {

		url = "jdbc:mysql://localhost:" + port + "/java?useSSL=false";

		try {

			this.connection = DriverManager.getConnection(url, username, password);

			if (connection != null) {

				System.out.println("Connected");

				setTableNames(); 

				setTableNames.setTableNames(tableNames); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// public List<Person> searchByKey(int column, Object key) {
	//
	// personList.clear();
	//
	// if (key instanceof String) {
	// System.out.println("STRING");
	// } else {
	// System.out.println("INT");
	// }
	//
	// String columnName = "";
	//
	// switch (column) {
	// case 0:
	// columnName = "id";
	// if (key instanceof String) {
	// warning_panel.warning();
	// return unsort();
	// }
	// break;
	// case 1:
	// columnName = "first_name";
	// if (key instanceof Integer) {
	// warning_panel.warning();
	// return unsort();
	// }
	// break;
	// case 2:
	// columnName = "last_name";
	// break;
	// case 3:
	// columnName = "email";
	// break;
	// case 4:
	// columnName = "age";
	// if (key instanceof String) {
	// warning_panel.warning();
	// return unsort();
	// }
	// break;
	// case 5:
	// columnName = "year_group";
	// break;
	// case 6:
	// columnName = "gender";
	// break;
	// case 7:
	// columnName = "employed";
	// break;
	// case 8:
	// columnName = "citizen";
	// break;
	// case 9:
	// columnName = "created_at";
	// break;
	// case 10:
	// columnName = "created_at";
	// break;
	// }
	//
	// try {
	//
	// connection = DriverManager.getConnection(url, username, password);
	//
	// String format = String.format(searchKey, columnName);
	//
	// PreparedStatement search = connection.prepareStatement(format);
	//
	// if (key instanceof String) {
	// search.setString(1, key.toString() + "%");
	// } else {
	// search.setInt(1, (int) key);
	// }
	//
	// System.out.println(search);
	//
	// ResultSet resultSet = search.executeQuery();
	//
	// while (resultSet.next()) {
	// int ID = resultSet.getInt(1);
	// String first_name = resultSet.getString(2);
	// String last_name = resultSet.getString(3);
	// String email = resultSet.getString(4);
	// int age = resultSet.getInt(5);
	// Year_Group year_group = Year_Group.valueOf(resultSet.getString(6));
	// Gender gender = Gender.valueOf(resultSet.getString(7));
	// Employed employed = Employed.valueOf(resultSet.getString(8));
	// boolean citizen = resultSet.getBoolean(9);
	// Date date = resultSet.getDate(10);
	// Time time = resultSet.getTime(10);
	//
	// Person person = new Person(first_name, last_name, email, age, year_group,
	// gender, employed, citizen);
	//
	// person.setID(ID);
	// person.setDate(date);
	// person.setTime(time);
	//
	// personList.add(person);
	//
	// }
	//
	// connection.close();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return personList;
	//
	// }
	//
	// public void setWarning_panel(Warning_panel warning_panel) {
	// this.warning_panel = warning_panel;
	// }

	public void deleteTable(String tableName) {

		if (connection != null) {

			if (tableName != null && !(tableName.equals(""))) {

				boolean tbName = false;

				for (String tb_name : this.tableNames) {

					if (tb_name.equals(tableName)) {

						tbName = false; // AKO IME POSTOJI ONDA JE DOBRO I PREKNI ODMAH !!!
						break;

					} else {

						tbName = true; // AKO IME NEEEE POSTOJI ONDA JE TRUE;

					}

				}

				if (!tbName) {

					String delete = "drop table %s";

					try {

						String deleteTable = String.format(delete, tableName);

						Statement delTab = connection.createStatement();

						int rowsAffected = delTab.executeUpdate(deleteTable);

						if (rowsAffected == 0) {

							System.out.println("Table has been successfully deleted !");

							this.tableNames.remove(tableName); ///// BITNO !!!

							setTableNames.removeTableNames(tableName); ///// BITNO !!!

						} else {
							System.out.println("PROPO!");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					warnings.noTableName(tableName);
				}
			}

		} else {
			warnings.warningDB();
		}

	}

	public void createTable(String tableName) {

		if (connection != null) {

			if (tableName != null && !(tableName.equals(""))) {

				boolean theSame = false;

				for (String tb_name : this.tableNames) {

					if (tableName.equals(tb_name)) {

						theSame = true; // UKOLIKO VEC POSTOJI TABELA SA TIM IMENOM PREKINI !!!
						break;

					}

				}

				if (!theSame) {

					String create = "CREATE TABLE %s (\r\n" + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\r\n"
							+ "    first_name VARCHAR(255) NOT NULL,\r\n" + "    last_name varchar(255) not null,\r\n"
							+ "    email varchar(255) not null,\r\n" + "    age int not null,\r\n"
							+ "    year_group enum('under_18','adult','over_65') not null,\r\n"
							+ "    gender enum('male','female') not null,\r\n"
							+ "    employed enum('unemployed','self_employed','employed'),\r\n"
							+ "    citizen boolean not null,\r\n" + "    created_at TIMESTAMP DEFAULT NOW());";

					try {

						String createTable = String.format(create, tableName);

						Statement creTbl = connection.createStatement();

						int rowsAffected = creTbl.executeUpdate(createTable);

						if (rowsAffected == 0) {

							System.out.println("Tabela je napravljena!");

							this.tableNames.add(tableName); ///// BITNO !!!

							setTableNames.addTableNames(tableName); ///// BITNO !!!

						} else {
							System.out.println("Propo");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					warnings.SameTableName(tableName);
				}

			}

		} else {
			warnings.warningDB();
		}

	}

	private void setTableNames() {

		if (connection != null) {

			try {

				Statement getTableNames = connection.createStatement();

				ResultSet tableNames = getTableNames.executeQuery("show tables");

				while (tableNames.next()) {

					this.tableNames.add(tableNames.getString(1));

					System.out.println(tableNames.getString(1));

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			warnings.warningDB();
		}

	}

	public void deletePerson(String tableName, int ID, List<Person> personList) {

		if (connection != null) {

			boolean tName = false;

			for (String name : tableNames) {

				if (tableName.equals(name)) {
					tName = false;
					break;
				} else {
					tName = true;
				}

			}

			if (!tName) {

				String delete = String.format(deletePerson, tableName);

				int id = personList.get(ID).getID();

				try {

					PreparedStatement deletePer = connection.prepareStatement(delete);

					deletePer.setInt(1, id);

					int rowsAffected = deletePer.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("radi");
					} else {
						System.out.println("ne radi!");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				warnings.SameTableName(tableName);
			}
		} else {
			warnings.warningDB();
		}

	}

	public Connection getConnection() {
		if (connection == null) {
			warnings.warningDB();
		}
		return connection;
	}

	public void setWarnings(Warnings warnings) {
		this.warnings = warnings;
	}

	public LinkedHashSet<String> getTableNames() {
		return tableNames;
	}

	public void setSetTableNames(SetTableNames setTableNames) {
		this.setTableNames = setTableNames;
	}

}
