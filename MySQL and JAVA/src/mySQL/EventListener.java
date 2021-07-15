package mySQL;

import java.util.EventObject;

public class EventListener extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String first_name;
	private String last_name;
	private String email;
	private int age;
	private int year_group;
	private String gender;
	private int employed;
	private boolean citizen;

	public EventListener(Object source, String fname, String lname, String email, int age, int year, String gender,
			int employed, boolean citizen) {
		super(source);

		this.first_name = fname;
		this.last_name = lname;
		this.email = email;
		this.age = age;
		this.year_group = year;
		this.gender = gender;
		this.employed = employed;
		this.citizen = citizen;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public int getYear_group() {
		return year_group;
	}

	public String getGender() {
		return gender;
	}

	public int getEmployed() {
		return employed;
	}

	public boolean isCitizen() {
		return citizen;
	}

}
