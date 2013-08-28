package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Activity extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long internal_id;
	
	@MaxLength(240)
	@Required
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@Required
	public User instructor;
	
	@Required
	public String name;
	
	public static Finder<Long, Activity> find = new Finder<>(Long.class, Activity.class);
	
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
}
