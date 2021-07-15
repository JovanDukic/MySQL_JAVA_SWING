package mySQL;

import java.sql.Date;
import java.sql.Time;

import enums.Employed;
import enums.Gender;
import enums.Year_Group;

public class Person {
	
	private int ID;
	private String first_name;
	private String last_name;
	private String email;
	private int age;
	private Year_Group year_group;
	private Gender gender;
	private Employed employed;
	private boolean citizen;

	private Date date;
	private Time time;

	public Person(String fname, String lname, String email, int age, Year_Group group, Gender gender, Employed employed,
			boolean citizen) {

		this.first_name = fname;
		this.last_name = lname;
		this.email = email;
		this.age = age;
		this.year_group = group;
		this.gender = gender;
		this.employed = employed;
		this.citizen = citizen;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Year_Group getYear_group() {
		return year_group;
	}

	public void setYear_group(Year_Group year_group) {
		this.year_group = year_group;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Employed getEmployed() {
		return employed;
	}

	public void setEmployed(Employed employed) {
		this.employed = employed;
	}

	public boolean isCitizen() {
		return citizen;
	}

	public void setCitizen(boolean citizen) {
		this.citizen = citizen;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
