package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Long internal_id;
	
	@MaxLength(240)
	@Required
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@Required
	private Member instructor;
	
	private boolean hasSchedule;


    private boolean defaultActivity;
	
	@Required
	private String name;
	
	public static Finder<Long, Activity> find = new Finder<Long, Activity>(Long.class, Activity.class);
	
	public static List<Activity> staticClasses(){
        Map<String, Object> allEq = new HashMap<String,Object>();
        allEq.put("hasSchedule", false);
        allEq.put("defaultActivity", false);
		return find.where().allEq(allEq).findList();
	}
    public static Activity getDefault(){
        return find.where().eq("defaultActivity",true).findUnique();
    }
	
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public Long getInternal_id() {
		return internal_id;
	}
	public void setInternal_id(Long internal_id) {
		this.internal_id = internal_id;
	}
	public Member getInstructor() {
		return instructor;
	}
	public void setInstructor(Member instructor) {
		this.instructor = instructor;
	}
	public boolean isHasSchedule() {
		return hasSchedule;
	}
	public void setHasSchedule(boolean hasSchedule) {
		this.hasSchedule = hasSchedule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDefaultActivity(){
         return defaultActivity;
    }

    public void setDefaultActivity(boolean defaultActivity){
        this.defaultActivity = defaultActivity;
    }
}
