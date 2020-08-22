/**
 *
 */
package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author tucke_000
 * modified by syh
 */

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "studentid")
	private String studentId;   // college id number

	@Size(min=2, max=2)
	private String month;
	@Size(min=2, max=2)
	private String day;
	@Size(min=4, max=4)
	private String year;

	@NotNull
	private String gender;

	@NotBlank
	@Size(min=1, max=30)
	@Column(name = "last_name")
	private String lastname;

	@NotBlank
	@Size(min=1, max=20)
	@Column(name = "first_name")
	private String firstname;

	@Size(max = 1)
	@Column(name="middle_initial")
	private String middleInitial;

	@NotBlank
	@Size(min=1, max=200)
	@Column(name="Street_Address")
	private String address;

	private String aptnum;
	@NotBlank
	private String city;
	@NotBlank
	@Size(min=2, max=2)
	private String state;

	@NotBlank
	private String zipcode;
	private String zipcode2;

	private String homephone;
	private String workphone;
	@NotBlank
	private String cellphone;

	@NotBlank
	@Email
	@Column(name = "email")
	private String email;

	//Has attended Montgomery College Before
	@NotNull
	private boolean attenMcb4;

	//How did they hear about us?
	@Column(name="Heard_about_us")
	private String hearMC;  // brochure, website, sns, advertisement, on campus, other

	private String digits; //for Military Service

	//Race or ethnicity choices, true or false
	private boolean notHispanic;
	private boolean hispanic;
	private boolean americanIndian;
	private boolean asian;
	private boolean blackAfricanAmerican;
	private boolean hawaiian;
	private boolean white;

	private boolean citizen;
	private boolean permanentResident;
	private boolean greenCard;
	private boolean workingCard;
	private boolean otherStatus;
	private String otherPermanent;

	@NotNull
	private boolean mdRes; //Is a MD resident

	@NotNull
	private boolean sixtyPlus; //Is sixty years of age or older

	@NotNull
	private boolean mdNatGuard;  //Is a MD national guard member

	private LocalDate registerDate;   // register date

	private long totalTuition;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<RegisterCourse> registerCourses;

	public Student() {
		// initialize non-required fields for preview
		this.studentId = " ";
		this.middleInitial= " ";
		this.aptnum= " ";
		this.zipcode2= " ";
		this.homephone= " ";
		this.workphone= " ";
		this.hearMC= " ";
		this.digits= " ";
		this.otherPermanent= " ";
		this.registerCourses = new Collection<RegisterCourse>() {
			@Override
			public int size() {
				return 0;
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public boolean contains(Object o) {
				return false;
			}

			@Override
			public Iterator<RegisterCourse> iterator() {
				return null;
			}

			@Override
			public Object[] toArray() {
				return new Object[0];
			}

			@Override
			public <T> T[] toArray(T[] a) {
				return null;
			}

			@Override
			public boolean add(RegisterCourse registerCourse) {
				return false;
			}

			@Override
			public boolean remove(Object o) {
				return false;
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				return false;
			}

			@Override
			public boolean addAll(Collection<? extends RegisterCourse> c) {
				return false;
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				return false;
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				return false;
			}

			@Override
			public void clear() {

			}
		};
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isAttenMcb4() {
		return attenMcb4;
	}

	public void setAttenMcb4(boolean attenMcb4) {
		this.attenMcb4 = attenMcb4;
	}

	public boolean isCitizen() {
		return citizen;
	}

	public void setCitizen(boolean citizen) {
		citizen = citizen;
	}

	public boolean isMdRes() {
		return mdRes;
	}

	public void setMdRes(boolean mdRes) {
		mdRes = mdRes;
	}

	public boolean isSixtyPlus() {
		return sixtyPlus;
	}

	public void setSixtyPlus(boolean sixtyPlus) {
		this.sixtyPlus = sixtyPlus;
	}


	public boolean isHispanic() {
		return hispanic;
	}

	public void setHispanic(boolean hispanic) {
		hispanic = hispanic;
	}

	public boolean isAmericanIndian() {
		return americanIndian;
	}

	public void setAmericanIndian(boolean americanIndian) {
		americanIndian = americanIndian;
	}

	public boolean isAsian() {
		return asian;
	}

	public void setAsian(boolean asian) {
		asian = asian;
	}

	public boolean isBlackAfricanAmerican() {
		return blackAfricanAmerican;
	}

	public void setBlackAfricanAmerican(boolean blackAfricanAmerican) {
		blackAfricanAmerican = blackAfricanAmerican;
	}

	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean white) {
		white = white;
	}

	public boolean isHawaiian() {
		return hawaiian;
	}

	public void setHawaiian(boolean hawaiian) {
		hawaiian = hawaiian;
	}

	public String getHearMC() {
		return hearMC;
	}

	public void setHearMC(String hearMC) {
		this.hearMC = hearMC;
	}

	public String getAptnum() {
		return aptnum;
	}

	public void setAptnum(String aptnum) {
		this.aptnum = aptnum;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getZipcode2() {
		return zipcode2;
	}

	public void setZipcode2(String zipcode2) {
		this.zipcode2 = zipcode2;
	}

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getDigits() {
		return digits;
	}

	public void setDigits(String digits) {
		this.digits = digits;
	}

	public boolean isNotHispanic() {
		return notHispanic;
	}

	public void setNotHispanic(boolean notHispanic) {
		this.notHispanic = notHispanic;
	}

	public boolean isPermanentResident() {
		return permanentResident;
	}

	public void setPermanentResident(boolean permanentResident) {
		this.permanentResident = permanentResident;
	}

	public boolean isGreenCard() {
		return greenCard;
	}

	public void setGreenCard(boolean greenCard) {
		this.greenCard = greenCard;
	}

	public boolean isWorkingCard() {
		return workingCard;
	}

	public void setWorkingCard(boolean workingCard) {
		this.workingCard = workingCard;
	}

	public boolean isOtherStatus() {
		return otherStatus;
	}

	public void setOtherStatus(boolean otherStatus) {
		this.otherStatus = otherStatus;
	}

	public String getOtherPermanent() {
		return otherPermanent;
	}

	public void setOtherPermanent(String otherPermanent) {
		this.otherPermanent = otherPermanent;
	}


	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public boolean isMdNatGuard() {
		return mdNatGuard;
	}

	public void setMdNatGuard(boolean mdNatGuard) {
		this.mdNatGuard = mdNatGuard;
	}


	public long getTotalTuition() {
		return totalTuition;
	}

	public void setTotalTuition(long totalTuition) {
		this.totalTuition = totalTuition;
	}

	public Collection<RegisterCourse> getRegisterCourses() {
		return registerCourses;
	}

	public void setRegisterCourses(Collection<RegisterCourse> registerCourses) {
		this.registerCourses = registerCourses;
	}

	//Determines whether or not statement is true and returns yes or no
	public String boolString(boolean flag){
		String statement;
		if(flag)
			statement="Yes";
		else
			statement="No";

		return statement;
	}
}
