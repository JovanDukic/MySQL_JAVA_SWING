package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import database.My_SQL_Database;
import enums.Employed;
import enums.Gender;
import enums.Year_Group;
import interaface.Refreshing;
import mySQL.EventListener;
import mySQL.Person;

public class Controller {

	private My_SQL_Database database = new My_SQL_Database();

	private Connection connection = null;

	private String inserInto = "insert into %s(first_name, last_name, email, age, year_group, gender, employed, citizen) values (?,?,?,?,?,?,?,?)";

	private String loadAll = "select * from %s where id = ?";

	private Refreshing refreshing;

	public void setPerons(EventListener eventListener, String tableName) {

		if (connection != null) {

			String insertValue = String.format(inserInto, tableName);

			String load = String.format(loadAll, tableName);

			String first_name = eventListener.getFirst_name();
			String last_name = eventListener.getLast_name();
			String email = eventListener.getEmail();
			int age = eventListener.getAge();
			int year_group = eventListener.getYear_group();
			String gender = eventListener.getGender();
			int employed = eventListener.getEmployed();
			boolean citizen = eventListener.isCitizen();

			Year_Group year_Group = null;

			switch (year_group) {
			case 0:
				year_Group = Year_Group.under_18;
				break;
			case 1:
				year_Group = Year_Group.adult;
				break;
			case 2:
				year_Group = Year_Group.over_65;
				break;
			}

			Gender gender1 = null;

			if (gender.equals("male")) {
				gender1 = Gender.male;
			} else {
				gender1 = Gender.female;
			}

			Employed employed2 = null;

			switch (employed) {
			case 0:
				employed2 = Employed.unemployed;
				break;
			case 1:
				employed2 = Employed.self_employed;
				break;
			case 2:
				employed2 = Employed.employed;
				break;
			}

			try {

				PreparedStatement insert = connection.prepareStatement(insertValue, Statement.RETURN_GENERATED_KEYS);

				PreparedStatement loadInfo = connection.prepareStatement(load);

				insert.setString(1, first_name);
				insert.setString(2, last_name);
				insert.setString(3, email);
				insert.setInt(4, age);
				insert.setString(5, year_Group.toString());
				insert.setString(6, gender1.toString());
				insert.setString(7, employed2.toString());
				insert.setBoolean(8, citizen);

				int rowsAffected = insert.executeUpdate();

				System.out.println("Dodatih redova u My_SQL: " + rowsAffected);

				Person person = new Person(first_name, last_name, email, age, year_Group, gender1, employed2, citizen);

				ResultSet resultSet = insert.getGeneratedKeys();

				while (resultSet.next()) {
					loadInfo.setInt(1, resultSet.getInt(1));

					ResultSet roli = loadInfo.executeQuery();

					while (roli.next()) {
						person.setID(roli.getInt(1));
						person.setDate(roli.getDate(10));
						person.setTime(roli.getTime(10));
					}

				}

				database.addPerson(person);
				
				refreshing.refresh();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Konekcija nije uspostavljena!");
		}

	}

	public List<Person> getPersonList() {
		return database.getPersonList();
	}

	public void removePerson(int ID) {
		database.removePerson(ID);
	}

	public void clearPersonList() {
		database.clearPersonList();
	}

	public void setList(List<Person> personList) {
		database.setList(personList);
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setRefreshing(Refreshing refreshing) {
		this.refreshing = refreshing;
	}

}
