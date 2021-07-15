package comparator;

import java.util.LinkedList;
import java.util.List;

import interaface.Warning_empty_key;
import interaface.Warning_panel;
import mySQL.Person;

public class MyComparator {

	private List<Person> personList_return = new LinkedList<Person>();

	private Warning_panel warning_panel;

	private Warning_empty_key warning_empty_key;

	public List<Person> compare_by_key(String key, int column, List<Person> personList) {

		if (personList.size() != 0) {

			if (!(key.equals(""))) {

				personList_return.clear();

				switch (column) {
				case 0:
					warning_panel.warning(column);
					return personList;
				case 1:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getFirst_name().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 2:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getLast_name().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 3:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getEmail().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 4:
					for (int i = 0; i < personList.size(); i++) {
						if (Integer.parseInt(key) == personList.get(i).getAge()) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 5:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getYear_group().name().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 6:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getGender().name().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 7:
					for (int i = 0; i < personList.size(); i++) {
						if ((personList.get(i).getEmployed().name().toLowerCase()).indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 8:
					for (int i = 0; i < personList.size(); i++) {
						if ((String.valueOf(personList.get(i).isCitizen()).toLowerCase())
								.indexOf(key.toLowerCase()) != -1) {
							personList_return.add(personList.get(i));
						}
					}
					break;
				case 9:
					warning_panel.warning(column);
					return personList;
				case 10:
					warning_panel.warning(column);
					return personList;
				}

				return personList_return;

			} else {
				warning_empty_key.warning_no_key();
				return personList;
			}

		} else {
			warning_panel.warning(8); // MOZE BILO KOJI BROJ JER OVO NE ZAVISI OD KOLONE VEC OD PERSON LIST !!!
		}
		return personList_return;
	}

	public void setWarning_panel(Warning_panel warning_panel) {
		this.warning_panel = warning_panel;
	}

	public void setWarning_empty_key(Warning_empty_key warning_empty_key) {
		this.warning_empty_key = warning_empty_key;
	}

}
