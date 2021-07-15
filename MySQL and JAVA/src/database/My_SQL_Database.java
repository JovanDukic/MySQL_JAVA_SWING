package database;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import mySQL.Person;

public class My_SQL_Database {

	private List<Person> personList;

	public My_SQL_Database() {
		personList = new LinkedList<Person>();
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void addPerson(Person person) {
		personList.add(person);
	}

	public void removePerson(int ID) {
		personList.remove(ID);
	}

	public void clearPersonList() {
		personList.clear();
	}

	public void setList(List<Person> personList) {
		this.personList = personList.stream().collect(Collectors.toList());
		System.out.println("List je postavljena!" + personList.size());
	}

}
