package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Member extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Required
	@MaxLength(30)
	private String firstName;

	@Required
	@MaxLength(30)
	private String lastName;
	
	@Id
	@MaxLength(80)
	@PrimaryKeyJoinColumn
	private String email;

    @Required
    private String organization;
	
	private String employee_id;
	
	@DateTime(pattern="MM-dd-yyyy")
	private Date dob;
	
	@DateTime(pattern="MM-dd-yyyy HH:mm:ss")
	private Date lastLoggedIn;
	
	@DateTime(pattern="MM-dd-yyyy HH:mm:ss")
	private Date createdOn;
	
	private boolean active;
	
	public static Finder<String, Member> find = new Finder<String, Member>(String.class, Member.class);
	
	public String fullName(){return firstName + " " + lastName;}
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getOrganization(){
        return organization;
    }

    public void setOrganization(String organization){
        this.organization = organization;
    }
}
